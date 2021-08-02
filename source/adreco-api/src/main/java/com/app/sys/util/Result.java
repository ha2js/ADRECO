package com.app.adView.sys.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.app.adView.sys.constant.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {

	HttpStatus statusCode;
	String message;
	Object data;
	Object error;
	
	public Result instance() {
		return new Result();
	}
	
	public Result successInstance() {
		return Result.builder()
					.statusCode(HttpStatus.OK)
					.message(Const.SUCCESS)
					.build();
	}
	
	public Result successInstance(Object obj) {
		return Result.builder()
					.statusCode(HttpStatus.OK)
					.message(Const.SUCCESS)
					.data(obj)
					.build();
	}
	
	public Result failInstance() {
		return Result.builder()
					.statusCode(HttpStatus.FORBIDDEN)
					.message(Const.FAIL)
					.build();
	}
	
	public Result internalServerErrorInstance() {
		return Result.builder()
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.message(Const.INTERNAL_SERVER_ERROR)
				.build();
	}
}
