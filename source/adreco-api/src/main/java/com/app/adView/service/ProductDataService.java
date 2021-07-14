package com.app.adView.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductDataService {
	
	// LF 스퀘어 URL
	private static String LF_SQUARE_PRODUCT_URL = "https://www.lfsquare.com/shop/listbest.php";
	
	// 카테고리(한글) 배열
	private static String[] categoryKor = {"남성의류", "여성의류", "패션잡화", "해외명품", "유아동", "스포츠", "리빙", "뷰티", "슈즈", "식품", "가전"};
	
	// 카테고리 배열 (URL 뒤 GET방식)
	private static String[] categories = {"01", "02", "03", "04", "05", "06", "08", "09", "0303", "0B", "0D"};
	
	@PostConstruct
	public void getProductData() throws IOException {
		
		// 상품 정보 Map
		// 1Depth : (카테고리명, Map)
		// 2Depth : (컬럼(ex.상품명), 상품정보)
		Map<String, Map<String, List<String>>> productInfo = new HashMap<>();
		
		// 카테고리별 웹 크롤링
		for(String category : categories) {
			
			Map<String, List<String>> productList = new HashMap<>();
			List<String> productBrand = new ArrayList<>();
			List<String> productName = new ArrayList<>();
			List<String> productPrice = new ArrayList<>();
			List<String> productUrl = new ArrayList<>();
			
			Document doc = Jsoup.connect(LF_SQUARE_PRODUCT_URL + "?cate=" + category).get();
			
			// 제품 설명 (제조사, 제품명, 제품 가격)
			Elements description = doc.select(".product-list-wrap ul li .description");
			// 제품 이미지 URL
			Elements imgUrl = doc.select(".product-list-wrap ul li img");
			
			// 컬럼별 상품정보 추출
			for(Element content : description) {
				productBrand.add(content.select(".prd-brand").text());
				productName.add(content.select(".prd-name").text());
				productPrice.add(content.select(".prd-price").text());
			}
			productUrl.add(imgUrl.attr("src"));
			
			// 상품정보 Mapping
			productList.put("제조사", productBrand);
			productList.put("상품명", productName);
			productList.put("상품가격", productPrice);
			productList.put("상품이미지", productUrl);
			
			System.out.println(productList);
			log.info("====================================================");
		}
	}
}
