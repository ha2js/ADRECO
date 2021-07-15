package com.app.adView.biz.ProductDataCrawling.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
	
	// 제조사
	private String productBrand;
	
	// 상품명
	private String productName;
	
	// 가격
	private String productPrice;
	
	// 이미지 URL
	private String productUrl;
	
	// keyword
	private String keyword;
}
