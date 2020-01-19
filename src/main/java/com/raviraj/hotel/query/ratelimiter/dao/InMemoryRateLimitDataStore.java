package com.raviraj.hotel.query.ratelimiter.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.raviraj.hotel.query.ratelimiter.API;

/**
 * 
 * @author Raviraj
 * Data store that stores the rate limit state implemented in-memory
 *
 */
public class InMemoryRateLimitDataStore  implements RateLimterDataAcess{

	
	private static HashMap<String,API> apis = new HashMap<String,API>();
	
	private static ConcurrentHashMap<Partition,Integer> rollingWindowCount = new ConcurrentHashMap<Partition,Integer>();
	
	private static ConcurrentHashMap<String,Long> lastBreach = new ConcurrentHashMap<String,Long>();
	
	private static ConcurrentHashMap<String,Lock> lockMap = new ConcurrentHashMap<String,Lock>();
	
	public static class Partition {
		public long getTimeInSeconds() {
			return timeInSeconds;
		}
		public void setTimeInSeconds(long timeInSeconds) {
			this.timeInSeconds = timeInSeconds;
		}

		String apiId;
		long timeInSeconds;
		public String getApiId() {
			return apiId;
		}
		public void setApiId(String apiId) {
			this.apiId = apiId;
		}
		public Partition(String apiId, long timeInSeconds) {
			super();
			this.apiId = apiId;
			this.timeInSeconds = timeInSeconds;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((apiId == null) ? 0 : apiId.hashCode());
			result = prime * result + (int) (timeInSeconds ^ (timeInSeconds >>> 32));
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Partition other = (Partition) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (apiId == null) {
				if (other.apiId != null)
					return false;
			} else if (!apiId.equals(other.apiId))
				return false;
			if (timeInSeconds != other.timeInSeconds)
				return false;
			return true;
		}
		
		private Class<Partition> getEnclosingInstance() {
			return InMemoryRateLimitDataStore.Partition.class;
		}
	}
	
	public void registerAPI(API api) {
		apis.put(api.getApiId(), api);
		Lock lock = new ReentrantLock();
		lockMap.put(api.getApiId(), lock);
	}

	public void incrementCount(String apiId,long time,int cnt) {
		
		Integer count = rollingWindowCount.get(new Partition(apiId, time));
		if(count != null)
			rollingWindowCount.put(new Partition(apiId, time), count+cnt);
		else
			rollingWindowCount.put(new Partition(apiId, time), cnt);
	}

	public int getCurrentCount(String apiId,long time) {
		
		API api = apis.get(apiId);
		int limit = api.getLimit().getRequestLimit();
		int slidingWindowTime = api.getLimit().getTimeInSeconds();
		int totalcount = 0;
		for(int i=0;i<slidingWindowTime;i++) {
			Integer count =  rollingWindowCount.get(new Partition(apiId,time-i));
			if(count != null) {
				totalcount+=count;
			}
		}
		return totalcount;
	}
	
	public API getAPI(String api) {
		return apis.get(api);
	}

	public void setLastBreach(long timeInSeconds,String apiName) {
		
		lastBreach.put(apiName, timeInSeconds);
	}

	public Long getLastBreach(String apiName) {
		return lastBreach.get(apiName);
	}

	public void lock(String apiId) {
		Lock lock = lockMap.get(apiId);
		lock.lock();
		
	}

	public void unlock(String apiId) {
		Lock lock = lockMap.get(apiId);
		lock.unlock();
	}
	
}
