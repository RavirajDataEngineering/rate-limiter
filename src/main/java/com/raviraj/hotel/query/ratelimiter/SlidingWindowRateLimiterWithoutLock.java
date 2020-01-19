package com.raviraj.hotel.query.ratelimiter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.raviraj.hotel.query.ratelimiter.dao.InMemoryRateLimitDataStore;
import com.raviraj.hotel.query.ratelimiter.dao.InMemoryRateLimitDataStore.Partition;
import com.raviraj.hotel.query.ratelimiter.dao.RateLimterDataAcess;

/**
 * 
 * @author Raviraj
 * 
 * Sliding window implementation that periodically updates the rate limit store with 
 * local counts, there by relaxing the rate limit condition to achieve better latency
 *
 */
public class SlidingWindowRateLimiterWithoutLock implements RateLimiterFunction{

	private RateLimterDataAcess rateLimitStore;
	
	private ConcurrentHashMap<Partition, Integer> localCounter = new ConcurrentHashMap<Partition, Integer>();
	
	private class AsyncCountTimerTask extends TimerTask{
		
		@Override
		public void run() {
			Iterator<Partition> it = localCounter.keySet().iterator();
			while(it.hasNext()) {
				Partition p = it.next();
				Integer count = localCounter.get(p);
				int incCount = count==null ? 0 : count;
				API  api = rateLimitStore.getAPI(p.getApiId());
				long time = System.currentTimeMillis()/1000;
				rateLimitStore.lock(api.getApiId());
				rateLimitStore.incrementCount(api.getApiId(), p.getTimeInSeconds(),incCount );
				rateLimitStore.unlock(api.getApiId());
			}
			localCounter.clear();
		}
		
	}
	
	public SlidingWindowRateLimiterWithoutLock() {
		Timer timer = new Timer(true);
		timer.schedule(new AsyncCountTimerTask(), 0, 200);
	}
	
	public ResponseObject call(APITask apitask, RequestObject req) {
		String apiId = (String)req.get("apiid");
		API api = rateLimitStore.getAPI(apiId);
		long time = System.currentTimeMillis()/1000;
		Long lastBreach = rateLimitStore.getLastBreach(apiId);
		if(lastBreach!=null && time - lastBreach < 5){
			ResponseObject obj = new ResponseObject();
			obj.put("Response", "RATE_LIMIT_EXCEEDED");
			return obj;
		}
		
		int count = rateLimitStore.getCurrentCount(apiId,time);
		if(count < api.getLimit().getRequestLimit()) {
			Partition p = new InMemoryRateLimitDataStore.Partition(apiId, time);
			Integer localcount = localCounter.get(p);
			if(localcount == null) {
				localCounter.put(p, 1);
			}else {
				localCounter.put(p,localcount+1);
			}
			return apitask.execute(req);
		}else {
			rateLimitStore.setLastBreach(time, apiId);
			ResponseObject obj = new ResponseObject();
			obj.put("Response", "RATE_LIMIT_EXCEEDED");
			return obj;
		}
	
			
		
	}

	public void setDataAccesClient(RateLimterDataAcess dao) {
		this.rateLimitStore = dao;
		
	}

}
