package com.raviraj.hotel.query.ratelimitedapi.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raviraj.hotel.query.ratelimitedapi.exceptions.ValidationException;
import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Hotel;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;
import com.raviraj.hotel.query.ratelimtedapi.service.Startup;

public class InMemoryDatabase {

	private static Map<City,List<Hotel>> mapbyCity = new HashMap<City,List<Hotel>>();
	
	private static Map<Room,List<Hotel>> mapbyRoom = new HashMap<Room,List<Hotel>>();
	
	private static List<Hotel> hotels = new ArrayList<Hotel>();
	
	private static Set<City> cities = new HashSet<City>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDatabase.class);
	
	public static void initdb(String fileName) {
		
		LOGGER.info("Initializing in memory database");
		if(fileName==null || "".equals(fileName))
			fileName = "src/main/resources/hoteldb.csv";
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			boolean ignoreFirstLine = true;
			while(line!=null) {
				if(ignoreFirstLine) {
					ignoreFirstLine = false;
					line = br.readLine();
					continue;
				}
				String[] arr = line.split(",");
				validateInput(arr);
				City city = new City(arr[0]);
				Hotel hotel = new Hotel(arr[1]);
				Room room = new Room(arr[2] , Double.parseDouble(arr[3]));
				hotel.addRoom(room);
				cities.add(city);
				hotels.add(hotel);
				room.setHotel(hotel.getId());
				
				if(mapbyCity.containsKey(city)) {
					List<Hotel> hotels = mapbyCity.get(city);
					hotels.add(hotel);
					mapbyCity.put(city, hotels);
				}else {
					List<Hotel> list = new ArrayList<Hotel>();
					list.add(hotel);
					mapbyCity.put(city, list);
				}
				
				if(mapbyRoom.containsKey(room)) {
					mapbyRoom.get(room).add(hotel);
				}else {
					List<Hotel> list = new ArrayList<Hotel>();
					list.add(hotel);
					mapbyRoom.put(room, list);
				}
				
				line = br.readLine();
			}
		}catch(Exception ex) {
			
			//TODO
			ex.printStackTrace();
		}
		

	}
	
	private static void validateInput(String[] arr) {
		if(arr.length<4)
			throw new ValidationException("Invalid number of values for input");
		if(!isNumeric(arr[3]))
			throw new ValidationException("Invalid value for room price");
		
		
	}
	private static boolean isNumeric(String str){
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	public static Map<City, List<Hotel>> getMapbyCity() {
		return mapbyCity;
	}

	public static Map<Room, List<Hotel>> getMapbyRoom() {
		return mapbyRoom;
	}

	public static List<Hotel> getHotels() {
		return hotels;
	}

	public static void setHotels(List<Hotel> hotels) {
		InMemoryDatabase.hotels = hotels;
	}

	public static Set<City> getCities() {
		return cities;
	}

	public static void setCities(Set<City> cities) {
		InMemoryDatabase.cities = cities;
	}

	
}
