package com.raviraj.hotel.query.ratelimitedapi.model;

public class Room implements Comparable<Room>{

	private String  type;
	public String getType() {
		return type;
	}

	private double price;
	private String hotel;
	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public Room(String type,double price) {
		this.type = type;
		this.price = price;
	}
	
	public Room(String room) {
		this.type = room;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Room other = (Room) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int compareTo(Room o) {
		if(o==null) return 0;
		if(this.price < o.price)
			return -1;
		else if(this.price == o.price)
			return 1;
		else
			return 1;
	}
}

