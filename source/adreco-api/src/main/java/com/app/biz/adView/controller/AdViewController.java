package com.app.biz.adView.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	/**
	 * @Desc : 광고판에서 필요한 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 7. 25.
	 * @param session
	 * @return
	 */
	@GetMapping("/getAdView")
	public ResponseEntity<Result> getAdView(HttpSession session) {
			
		Product product = adViewService.getProduct();
		
		if(!ObjectUtils.isEmpty(product)) {
			session.setAttribute("currentProduct", product);
			return ResponseEntity.ok().body(Result.successInstance(product));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
	}
}
