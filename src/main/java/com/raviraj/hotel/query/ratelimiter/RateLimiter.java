package com.raviraj.hotel.query.ratelimiter;

import java.util.HashSet;
import java.util.Set;

import com.raviraj.hotel.query.ratelimiter.dao.RateLimterDataAcess;

public class RateLimiter {

	/**
	 *  Depending on this RateLimterDataAcess implementation, we can different type of rate limiters, the
	 *  one in this project uses a Inmemory implementation, however to use this in a distributed environment
	 *  we can use a RateLimterDataAcess , that stores data in Cassandra or Redis etc.
	 *  
	 *  We can achieve a distributed rate limiter with this project , if we use the same Cassandra data access 
	 *  URL and same API names across different servers serving the API.
	 */
	private RateLimterDataAcess rateLimiterDao;
	
	/**
	 * Decides how to implement a rate limiter functionality, in this project there are two implementations
	 * 1. SlidingWindowWithLock - this implementation uses Locks and hence gives accurate rate limit but at the
	 *    cost of increased latency
	 * 2. SlidingWindowWithoutLock - this implementation doesnt use locks for every request, but periodically updates
	 *    local counts to central datastore , Hence we are relaxing the rate limit conditions at the benefit of
	 *    lower latency compared to 1.
	 */
	private RateLimiterFunction rateLimiterFunction;
	
	private Set<API> apis = new HashSet<API>();
	
	public RateLimterDataAcess getRateLimiterDao() {
		return rateLimiterDao;
	}

	public void setRateLimiterDao(RateLimterDataAcess rateLimiterDao) {
		this.rateLimiterDao = rateLimiterDao;
		rateLimiterFunction.setDataAccesClient(rateLimiterDao);
	}

	public RateLimiterFunction getRateLimiterFunction() {
		return rateLimiterFunction;
	}

	public void setRateLimiterFunction(RateLimiterFunction rateLimiterFunction) {
		this.rateLimiterFunction = rateLimiterFunction;
	}

	public void registerAPI(API api) {
		rateLimiterDao.registerAPI(api);
	}
	
	
	public ResponseObject callAPI(APITask task,RequestObject req) {
		
		return rateLimiterFunction.call(task,req);
	}
	
}
