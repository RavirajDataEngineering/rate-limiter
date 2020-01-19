package com.raviraj.hotel.query.ratelimiter;

import java.util.HashMap;
import java.util.Map;

public class RequestObject {

	private Map<String,Object> reqMap = new HashMap<String,Object>();
	
	public void put(String key,Object value) {
		reqMap.put(key, value);
	}
	
	public Object get(String key) {
		return reqMap.get(key);
	}
}
