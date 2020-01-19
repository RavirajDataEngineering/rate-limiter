package com.raviraj.hotel.query.ratelimiter;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

public class ResponseObject {
	
	
	private Map<String,Object> respMap = new HashMap<String,Object>();
	
	public void put(String key,Object value) {
		respMap.put(key, value);
	}
	
	public Object get(String key) {
		return respMap.get(key);
	}
	
	public Map getMap() {
		return this.respMap;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(respMap);
	}
}
