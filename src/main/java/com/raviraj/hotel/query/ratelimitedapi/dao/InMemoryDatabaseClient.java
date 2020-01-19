package com.raviraj.hotel.query.ratelimitedapi.dao;

import java.util.List;

import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Hotel;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;

public class InMemoryDatabaseClient implements DatabaseClient{

	public List<Hotel> getHotelsByCity(City city) {
		return InMemoryDatabase.getMapbyCity().get(city);
	}

	public List<Hotel> getHotelsByRoom(Room room) {
		return InMemoryDatabase.getMapbyRoom().get(room);
	}

}
