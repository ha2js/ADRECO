package com.app.biz.adView.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.biz.ProductDataCrawling.model.Product;
import com.app.biz.adView.service.AdViewService;
import com.app.sys.util.Result;

@RestController
@RequestMapping("/api/adView")
public class AdViewController {
	
	@Autowired
	private AdViewService adViewService;
	
	@GetMapping("/getAdView")
	public ResponseEntity<Result> getAdView(HttpSession session) {
		
		ResponseEntity<Result> result = adViewService.getAdView();
		
		if(!ObjectUtils.isEmpty(result.getBody().getData())) {
			session.setAttribute("currentProduct", ((Product)(result.getBody().getData())));
		}
		
		return result;
	}
}
