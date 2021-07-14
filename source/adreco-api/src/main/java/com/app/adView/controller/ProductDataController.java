package com.app.adView.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.adView.service.ProductDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
public class ProductDataController {
	
	@Autowired
	private ProductDataService productDataService;
	
	@GetMapping("/getProductInfo")
	public void getProductInfo() throws IOException {
		productDataService.getProductData();
	}
}
