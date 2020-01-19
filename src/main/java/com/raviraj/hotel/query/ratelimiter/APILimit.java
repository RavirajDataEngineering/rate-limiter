package com.raviraj.hotel.query.ratelimiter;

public class APILimit {

	private int timeInSeconds;
	
	private int requestLimit;

	public APILimit(int timeInSeconds, int requestLimit) {
		super();
		this.timeInSeconds = timeInSeconds;
		this.requestLimit = requestLimit;
	}

	public int getTimeInSeconds() {
		return timeInSeconds;
	}

	public void setTimeInSeconds(int timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
	}

	public int getRequestLimit() {
		return requestLimit;
	}

	public void setRequestLimit(int requestLimit) {
		this.requestLimit = requestLimit;
	}
	
	
	
}
