package com.raviraj.hotel.query.ratelimitedapi.dao;

import java.util.List;

import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Hotel;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;

public interface DatabaseClient {
	
	public List<Hotel> getHotelsByCity(City city);
	
	public List<Hotel> getHotelsByRoom(Room room);
}
