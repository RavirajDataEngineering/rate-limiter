package com.raviraj.hotel.query.ratelimtedapi.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import com.raviraj.hotel.query.ratelimiter.API;
import com.raviraj.hotel.query.ratelimiter.APILimit;
import com.raviraj.hotel.query.ratelimiter.RateLimiter;
import com.raviraj.hotel.query.ratelimiter.RateLimiterFunction;
import com.raviraj.hotel.query.ratelimiter.dao.RateLimterDataAcess;
import com.raviraj.hotel.query.ratelimiter.factory.RateLimiterDataAccessFactory;
import com.raviraj.hotel.query.ratelimiter.factory.RateLimiterFunctionFactory;

public class RateLimiterBuilder {

	private static RateLimiter rateLimiter;
	
	public static RateLimiter build(String configFile) {
		if(rateLimiter!=null)
			return rateLimiter;
		RateLimiterFunction rateLimitFunc = null;
		RateLimterDataAcess rateLimitDao = null;
		boolean rateLimitFuncPresent = false;
		boolean rateLimitDataAccessPresent = false;
		List<API> apis = new ArrayList<API>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(configFile));
			String line = br.readLine();
			while(line!=null) {
				String configs[] = line.split("=");
				if(configs[0].equals("RATELIMITER.FUNCTION.TYPE")) {
					rateLimitFuncPresent = true;
					rateLimitFunc = RateLimiterFunctionFactory.getRateLimiterFunction(configs[1]);
				}
				else if(configs[0].equals("RATELIMITER.DATAACCESS")) {
					rateLimitDataAccessPresent = true;
					rateLimitDao = RateLimiterDataAccessFactory.getRateLimitDataAccess(configs[1]);
				}
				else if(configs[0].equals("API")){
					String[] apiParams = configs[1].split(",");
					validateAPIParams(apiParams);
					API api = new API(apiParams[0], new APILimit(Integer.parseInt(apiParams[1]),Integer.parseInt(apiParams[2])));
					apis.add(api);
				}else {
					throw new ValidationException("Invalid Config parameter in Ratelimter Config");
				}
				line = br.readLine();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Error reading config while building rate limitter");
		}
		
		validateConfigs(rateLimitFuncPresent, rateLimitDataAccessPresent);
		rateLimiter = new RateLimiter();
		rateLimiter.setRateLimiterFunction(rateLimitFunc);
		rateLimiter.setRateLimiterDao(rateLimitDao);
		for(API api: apis)
			rateLimiter.registerAPI(api);
		return rateLimiter;
	}

	private static void validateConfigs(boolean rateLimitFuncPresent, boolean rateLimitDataAccessPresent) {
		if(!rateLimitFuncPresent) {
			throw new ValidationException("Rate limitier Function not present in config");
		}
		if(!rateLimitDataAccessPresent) {
			throw new ValidationException("rate limiter data access type not present in config");
		}
	}
	
	private static void validateAPIParams(String[] apiParams) {
		if(apiParams.length<3) {
			throw new ValidationException("Inavlid input for config params ");
		}
		if(!isNumeric(apiParams[1])) {
			throw new ValidationException("Invaid value for window time in config");
		}
		if(!isNumeric(apiParams[2])) {
			throw new ValidationException("Invaid value for API limit time in config");
		}
	}
	
	private static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

	public static RateLimiter getRateLimiter() {
		return rateLimiter;
	}
}
