package com.raviraj.hotel.query.ratelimtedapi.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raviraj.hotel.query.ratelimitedapi.dao.InMemoryDatabase;
import com.raviraj.hotel.query.ratelimitedapi.exceptions.ValidationException;



public class Startup {

	private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);
	
	public static void main(String[] args) {
		if(args.length<2) {
			throw new ValidationException("Please give the arguments for config file and hoteldb.csv");
		}
		Server server = new Server(4444);

		ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		ctx.setContextPath("/");
		server.setHandler(ctx);

		ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/hotelservice/*");
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages",
				"com.raviraj.hotel.query.ratelimtedapi.service");
		init(args);
		try {
			server.start();
			LOGGER.info("server started");
			server.join();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Error starting server ", ex);
		} finally {
			server.destroy();
		}
	}

	private static void init(String[] args) {
		if(args==null || args.length==0) {
			InMemoryDatabase.initdb("src/main/resources/hoteldb.csv");
			RateLimiterBuilder.build("src/main/resources/ratelimitconfig");
		}
		else {
			InMemoryDatabase.initdb(args[1]);
			RateLimiterBuilder.build(args[0]);
		}
		
		
	}
}
