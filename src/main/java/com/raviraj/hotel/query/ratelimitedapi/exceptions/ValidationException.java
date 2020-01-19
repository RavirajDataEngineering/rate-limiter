package com.raviraj.hotel.query.ratelimitedapi.exceptions;

public class ValidationException extends RuntimeException{

	public ValidationException(String msg) {
		super(msg);
	}
}
