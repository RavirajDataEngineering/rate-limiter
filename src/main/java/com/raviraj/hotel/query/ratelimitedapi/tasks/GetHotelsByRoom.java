package com.raviraj.hotel.query.ratelimitedapi.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.raviraj.hotel.query.ratelimitedapi.dao.DatabaseManager;
import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Hotel;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;
import com.raviraj.hotel.query.ratelimiter.APITask;
import com.raviraj.hotel.query.ratelimiter.RequestObject;
import com.raviraj.hotel.query.ratelimiter.ResponseObject;

public class GetHotelsByRoom implements APITask{

	
	private DatabaseManager dbManager;
	
	private String apiId;
	
	public GetHotelsByRoom(String apiId) {
		this.apiId = apiId;
	}


	public void setDatabaseManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public ResponseObject execute(RequestObject req) {
		List<Hotel> hotels = dbManager.getHotelsByRoomType((Room)req.get("room"));
		List<Room> rooms = new ArrayList<Room>();
		for(Hotel h:hotels) {
			List<Room> roomlist = h.getRooms();
			for(Room r : roomlist) {
				if(r.equals(req.get("room")))
					rooms.add(r);
			}
		}
		String sort = (String)req.get("sort");
		if (!sort.equals("DEFAULT")) {
			if (sort.equals("ASC")) {
				Collections.sort(rooms);
			} else {
				Collections.sort(rooms, Collections.reverseOrder());
			}
		}
		ResponseObject response =new  ResponseObject();
		response.put("hotels", rooms);
		response.put("Response","SUCCESS");
		return response;
	}


	public String getAPIid() {
		return this.apiId;
	}

}
