package com.app.sys.util;

import com.app.sys.constant.Const;

import lombok.Getter;

@Getter
public class Result {
	
	String message;
	Object data;
	Object error;
	
	public Result successInstance() {
		Result result = new Result();
		
		result.message = Const.SUCCESS;
		
		return result;
	}
	
	public static Result successInstance(Object obj) {
		Result result = new Result();
		
		result.message = Const.SUCCESS;
		result.data = obj;
		
		return result;
	}
	
	public static Result failInstance() {
		Result result = new Result();
		
		result.message = Const.FAIL;
		
		return result;
	}
	
	public static Result internalServerErrorInstance() {
		Result result = new Result();
		
		result.message = Const.INTERNAL_SERVER_ERROR;
		
		return result;
	}
}
