package com.app.adView.model;

import java.util.List;

import lombok.Data;

@Data
public class Product {
	
	// 제조사
	private String manufacturer;
	
	// 상품명
	private List<String> name;
	
	// 가격
	private String price;
	
	// 이미지 URL
	private String imgUrl;
}
