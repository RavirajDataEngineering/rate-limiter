package com.raviraj.hotel.query.ratelimiter;

import com.raviraj.hotel.query.ratelimitedapi.dao.DatabaseManager;
/**
 * 
 * @author raviraj
 * An instance of this implementation , has to be passed to the Ratelimiter by any service API that wants the
 * execution to be handled by the RateLimiter.
 *
 */
public interface APITask {
	
	public ResponseObject execute(RequestObject obj);
	
	public String getAPIid();
	
	public void setDatabaseManager(DatabaseManager dm);
	
}
