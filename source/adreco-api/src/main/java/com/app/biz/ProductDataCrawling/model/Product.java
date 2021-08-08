package com.app.biz.ProductDataCrawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	// 상품명
	private String productName;
	
	// 이미지 URL
	private String url;
	
	// 가격
	private String productPrice;
	
	// 제조사
	private String productBrand;
	
	// keyword
	private String keyword;
}
