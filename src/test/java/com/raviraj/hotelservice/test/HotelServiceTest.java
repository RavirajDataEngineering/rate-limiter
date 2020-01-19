package com.raviraj.hotelservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HotelServiceTest {
 
	static Server server = new Server(1000);
	@BeforeAll
	public static void init() {
		System.out.println("starting server");
        ServletContextHandler ctx = 
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
                
        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/hotelservice/*");
        serHol.setInitOrder(1);
        serHol.setInitParameter("jersey.config.server.provider.packages", 
                "com.raviraj.hotel.query.ratelimtedapi.service");
        try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@AfterAll
	public static void close() {
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Rate Limit exceeded City")
	public void testRateLimit() throws ClientProtocolException, IOException {
		 DefaultHttpClient httpClient = new DefaultHttpClient();
		 HttpGet getRequest = new HttpGet("http://localhost:1000/hotelservice/city/Bangkok");
		 HttpResponse response = httpClient.execute(getRequest);
		 httpClient = new DefaultHttpClient();
		 getRequest = new HttpGet("http://localhost:1000/hotelservice/city/Bangkok");
		  response = httpClient.execute(getRequest);
		  httpClient = new DefaultHttpClient();
		 getRequest = new HttpGet("http://localhost:1000/hotelservice/city/Bangkok");
		  response = httpClient.execute(getRequest);
		  httpClient = new DefaultHttpClient();
		 getRequest = new HttpGet("http://localhost:1000/hotelservice/city/Bangkok");
		  response = httpClient.execute(getRequest);
		  httpClient = new DefaultHttpClient();
		 getRequest = new HttpGet("http://localhost:1000/hotelservice/city/Bangkok");
		  response = httpClient.execute(getRequest);
		  httpClient = new DefaultHttpClient();
		 getRequest = new HttpGet("http://localhost:1000/hotelservice/city/Bangkok");
		  response = httpClient.execute(getRequest);
		 assertEquals(500, response.getStatusLine().getStatusCode());
	}
}
	
	
