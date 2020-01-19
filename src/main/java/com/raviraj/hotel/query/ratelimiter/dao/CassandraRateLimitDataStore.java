package com.raviraj.hotel.query.ratelimiter.dao;

import com.raviraj.hotel.query.ratelimiter.API;

/**
 * 
 * @author Raviraj
 *  
 *  Cassandra Data store for implementation of distributed 
 *  rate limiting. This class is just a skeleton class , if need to be used with a 
 *  distributed rate limiting , the class has to implented.
 *
 */
public class CassandraRateLimitDataStore implements RateLimterDataAcess {


	public int getCurrentCount(String apiId, long time) {
		// TODO Auto-generated method stub
		return 0;
	}

	public API getAPI(String api) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerAPI(API api) {
		// TODO Auto-generated method stub
		
	}

	public void setLastBreach(long timeInSeconds, String apiName) {
		// TODO Auto-generated method stub
		
	}

	public Long getLastBreach(String apiName) {
		// TODO Auto-generated method stub
		return 0L;
	}

	public void incrementCount(String apiId, long time, int count) {
		// TODO Auto-generated method stub
		
	}

	public void lock(String apiId) {
		// TODO Auto-generated method stub
		
	}

	public void unlock(String apiId) {
		// TODO Auto-generated method stub
		
	}

}
