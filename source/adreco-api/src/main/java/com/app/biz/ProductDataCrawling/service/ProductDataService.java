package com.app.biz.ProductDataCrawling.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biz.ProductDataCrawling.mapper.ProductDataMapper;
import com.app.biz.ProductDataCrawling.model.Product;
import com.app.sys.constant.Const;

@Service
public class ProductDataService {
	
	@Autowired
	private ProductDataMapper productDataMapper;
	
	@PostConstruct
	public void getProductData() throws IOException {
		
		// 기존 데이터 삭제
		productDataMapper.initProductData();
		
		// 카테고리별 웹 크롤링
		for(int cateIdx = 0; cateIdx < Const.categories.length; cateIdx++) {
			
			String category = Const.categories[cateIdx];
			
			// 상품 정보 List
			List<Product> productInfo = new ArrayList<>();
			
			Document doc = Jsoup.connect(Const.LF_SQUARE_PRODUCT_URL + "?cate=" + category).get();
			
			// 제품 설명 (제조사, 제품명, 제품 가격)
			Elements description = doc.select(".product-list-wrap ul li .description");
			// 제품 이미지 URL
			Elements imgUrl = doc.select(".product-list-wrap ul li img");
			
			// 컬럼별 상품정보 추출
			for(Element content : description) {
				
				Product product = Product.builder()
									.keyword(Const.categoryName[cateIdx])
									.productName(content.select(".prd-name").text())
									.productPrice(content.select(".prd-price").text().split(" ")[0])
									.productBrand(content.select(".prd-brand").text())
									.build();
				
				productInfo.add(product);
			}
			
			for(int idx = 0; idx < imgUrl.size(); idx++) {
				Element content = imgUrl.get(idx);
				productInfo.get(idx).setUrl(content.attr("src"));
			}
			
			// DB 저장
			productDataMapper.getProductData(productInfo);
		}
	}
}