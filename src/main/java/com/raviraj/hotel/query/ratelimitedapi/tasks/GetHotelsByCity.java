package com.raviraj.hotel.query.ratelimitedapi.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.raviraj.hotel.query.ratelimitedapi.dao.DatabaseManager;
import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Hotel;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;
import com.raviraj.hotel.query.ratelimiter.APITask;
import com.raviraj.hotel.query.ratelimiter.RequestObject;
import com.raviraj.hotel.query.ratelimiter.ResponseObject;

public class GetHotelsByCity implements APITask{

	private DatabaseManager dbManager;
	
	private String apiId;
	
	public void setDatabaseManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public GetHotelsByCity(String apiId) {
		this.apiId = apiId;
	}

	public ResponseObject execute(RequestObject req) {
		
		List<Hotel> hotels = dbManager.getHotelsByCity((City)req.get("city"));
		if(hotels==null)
			hotels = new ArrayList<Hotel>();
		List<Room> rooms = new ArrayList<Room>();
		for(Hotel h:hotels) {
			rooms.addAll(h.getRooms());
		}
		String sort = (String)req.get("sort");
		if (!sort.equals("DEFAULT")) {
			if (sort.equals("ASC"))
				Collections.sort(rooms);
			else {
				Collections.sort(rooms, Collections.reverseOrder());
			}
		}
		ResponseObject response =new  ResponseObject();
		response.put("hotels", rooms);
		response.put("Response","SUCCESS");
		return response;
	}

	public String getAPIid() {
		return apiId;
	}

}
