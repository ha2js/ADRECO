package com.app.adView.sys.util;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

	HttpStatus statusCode;
	String message;
	Object data;
	Object error;
	
	public Result instance() {
		return new Result();
	}
	
}
