package com.raviraj.hotel.query.ratelimiter.dao;

import com.raviraj.hotel.query.ratelimiter.API;

public interface RateLimterDataAcess {

	public void registerAPI(API api);
	
	public void incrementCount(String apiId,long time,int count);
	
	public int getCurrentCount(String apiId,long time);
	
	public API getAPI(String api);
	
	public void setLastBreach(long timeInSeconds,String apiName);
	
	public Long getLastBreach(String apiName);
	
	public void lock(String apiId);
	
	public void unlock(String apiId);
	
}
