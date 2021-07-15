package com.app.adView.biz.ProductDataCrawling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.app.adView.biz.ProductDataCrawling.model.Product;
import com.app.adView.biz.ProductDataCrawling.service.ProductDataService;

@RestController
@RequestMapping("/api/productData")
public class ProductDataController {
	
	@Autowired
	private ProductDataService productDataService;
	
	@GetMapping("/getAdView")
	public EntityResponse<Product> getAdView() {
		return productDataService.getAdView();
	}
}
