package com.raviraj.hotel.query.ratelimiter;

import com.raviraj.hotel.query.ratelimiter.dao.RateLimterDataAcess;

/**
 * The heart of rate limiter. This implementation decides how the rate limiting 
 * is implemented. 
 * {@link SlidingWindowRateLimiterWithLock} {@link SlidingWindowRateLimiterWithoutLock}
 * are the sample implementations given
 */
public interface RateLimiterFunction {

	public ResponseObject call(APITask apitask,RequestObject req);
	
	public void setDataAccesClient(RateLimterDataAcess dao);
	
}
