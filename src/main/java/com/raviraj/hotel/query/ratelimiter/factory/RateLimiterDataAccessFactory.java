package com.raviraj.hotel.query.ratelimiter.factory;

import com.raviraj.hotel.query.ratelimiter.dao.CassandraRateLimitDataStore;
import com.raviraj.hotel.query.ratelimiter.dao.InMemoryRateLimitDataStore;
import com.raviraj.hotel.query.ratelimiter.dao.RateLimterDataAcess;

public class RateLimiterDataAccessFactory {

	public static RateLimterDataAcess getRateLimitDataAccess(String type) {
		if(type==null || "".equals(type)) {
			return new InMemoryRateLimitDataStore();
		}
		if(type.equals("INMEMORY")) {
			return new InMemoryRateLimitDataStore();
		}
		if(type.equals("2")) {
			return new CassandraRateLimitDataStore();
		}
		return new InMemoryRateLimitDataStore();
	}
}
