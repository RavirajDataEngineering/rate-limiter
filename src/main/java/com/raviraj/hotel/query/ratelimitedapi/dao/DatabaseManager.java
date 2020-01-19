package com.raviraj.hotel.query.ratelimitedapi.dao;

import java.util.List;

import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Hotel;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;

public class DatabaseManager {

	/**
	 * Depending on the databse client used we can have different implementaions 
	 *  The one in this project is a Inmemory Implementation.
	 */
	private DatabaseClient dbClient;
	
	public void setDatabaseClient(DatabaseClient client) {
		this.dbClient = client;
	}
	
	public List<Hotel> getHotelsByCity(City city){
		return dbClient.getHotelsByCity(city);
	}
	
	public List<Hotel> getHotelsByRoomType(Room room){
		return dbClient.getHotelsByRoom(room);
	}
}
