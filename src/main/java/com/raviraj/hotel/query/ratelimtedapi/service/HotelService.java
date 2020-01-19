package com.raviraj.hotel.query.ratelimtedapi.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.raviraj.hotel.query.ratelimitedapi.dao.DatabaseManager;
import com.raviraj.hotel.query.ratelimitedapi.dao.InMemoryDatabaseClient;
import com.raviraj.hotel.query.ratelimitedapi.model.City;
import com.raviraj.hotel.query.ratelimitedapi.model.Room;
import com.raviraj.hotel.query.ratelimitedapi.tasks.GetHotelsByCity;
import com.raviraj.hotel.query.ratelimitedapi.tasks.GetHotelsByRoom;
import com.raviraj.hotel.query.ratelimiter.APITask;
import com.raviraj.hotel.query.ratelimiter.RateLimiter;
import com.raviraj.hotel.query.ratelimiter.RequestObject;
import com.raviraj.hotel.query.ratelimiter.ResponseObject;

@Path("")
public class HotelService {
	
	private  RateLimiter rateLimiter = RateLimiterBuilder.getRateLimiter();
	
	@GET
	@Path("/city/{city}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHotelsByCity(@PathParam("city") String city,@QueryParam("sort") String sort) {
		APITask task = new GetHotelsByCity("city");
		RequestObject req = new RequestObject();
		DatabaseManager dm = new DatabaseManager();
		dm.setDatabaseClient(new InMemoryDatabaseClient());
		task.setDatabaseManager(dm);
		req.put("apiid", "city");
		req.put("city", new City(city));
		if(sort!=null && !"".equals(sort))
			req.put("sort", sort);
		else
			req.put("sort", "DEFAULT");
		ResponseObject response = rateLimiter.callAPI(task,req);
		String result = (String)response.get("Response");
		if(result.equals("SUCCESS"))
			return Response.ok().entity(response.toString()).build();
		else
			return Response.status(429).entity(response.toString()).build();
	}
	
	@GET
	@Path("/room/{room}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHotelsByRoom(@PathParam("room") String room,@QueryParam("sort") String sort) {
		APITask task = new GetHotelsByRoom("room");
		RequestObject req = new RequestObject();
		DatabaseManager dm = new DatabaseManager();
		dm.setDatabaseClient(new InMemoryDatabaseClient());
		task.setDatabaseManager(dm);
		req.put("apiid", "room");
		req.put("room", new Room(room));
		if(sort!=null && !"".equals(sort))
			req.put("sort", sort);
		else
			req.put("sort", "DEFAULT");
		ResponseObject response = rateLimiter.callAPI(task,req);
		String result = (String)response.get("Response");
		if(result.equals("SUCCESS"))
			return Response.ok().entity(response.toString()).build();
		else
			return Response.status(429).entity(response.toString()).build();
	}
}
