package com.raviraj.hotel.query.ratelimiter.factory;

import com.raviraj.hotel.query.ratelimiter.RateLimiterFunction;
import com.raviraj.hotel.query.ratelimiter.SlidingWindowRateLimiterWithLock;
import com.raviraj.hotel.query.ratelimiter.SlidingWindowRateLimiterWithoutLock;

public class RateLimiterFunctionFactory {

	public static RateLimiterFunction getRateLimiterFunction(String type) {
		if(type==null || "".equals(type))
			return new SlidingWindowRateLimiterWithLock();
		if(type.equals("SLIDINGWINDOW_WITHLOCK")) {
			return new SlidingWindowRateLimiterWithLock();
		}
		if(type.equals("SLIDINGWINDOW_WITHOUTLOCK")) {
			return new SlidingWindowRateLimiterWithoutLock();
		}
		return new SlidingWindowRateLimiterWithLock();
			
	}
}
