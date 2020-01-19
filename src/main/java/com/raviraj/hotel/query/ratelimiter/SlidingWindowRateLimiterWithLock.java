package com.raviraj.hotel.query.ratelimiter;

import com.raviraj.hotel.query.ratelimiter.dao.RateLimterDataAcess;

/**
 * 
 * @author Raviraj
 * Sliding window implementation that checks the rate limit for every request
 * using locks
 *
 */
public class SlidingWindowRateLimiterWithLock implements RateLimiterFunction{

	/**
	 * Can be implemented in differnt ways Distribued / Single Node
	 * depending on the dataaccess implementation.
	 */
	private RateLimterDataAcess rateLimitStore;
	
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
		rateLimitStore.lock(apiId);
		int count = rateLimitStore.getCurrentCount(apiId, time);
		if (count < api.getLimit().getRequestLimit()) {
			rateLimitStore.incrementCount(apiId, time, 1);
			rateLimitStore.unlock(apiId);
			return apitask.execute(req);
		} else {
			rateLimitStore.setLastBreach(time, apiId);
			rateLimitStore.unlock(apiId);
			ResponseObject obj = new ResponseObject();
			obj.put("Response", "RATE_LIMIT_EXCEEDED");
			return obj;
		}
		
	}
	
	public void setDataAccesClient(RateLimterDataAcess dao) {
		this.rateLimitStore = dao;
		
	}

}
