package com.raviraj.hotel.query.ratelimitedapi.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

	public List<Room> getRooms() {
		return rooms;
	}

	private String id;
	
	private String city;
	
	private List<Room> rooms;
	
	public Hotel(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getId() {
		return this.id;
		
	}
	
	public void addRoom(Room room) {
		if(this.rooms == null) {
			rooms = new ArrayList<Room>();
			rooms.add(room);
		}else {
			rooms.add(room);
		}
	}
	
}
