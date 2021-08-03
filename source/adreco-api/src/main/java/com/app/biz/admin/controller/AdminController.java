package com.app.biz.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.biz.ProductDataCrawling.model.Product;
import com.app.biz.admin.service.AdminService;
import com.app.biz.feedback.model.CategoryTop3;
import com.app.sys.util.Result;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/getDashBoardInfo")
	public ResponseEntity<Result> getDashBoardInfo(HttpSession session) {
		
		// 현재 광고판에 나오는 상품이 없는 경우
		if(ObjectUtils.isEmpty(session.getAttribute("currentProduct"))) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
		}
		
		Product currentProduct = (Product)session.getAttribute("currentProduct");
		
		List<CategoryTop3> categoryTop3 = adminService.getCategoryTop3(currentProduct.getKeyword());
		
		// 카테고리에 해당하는 광고상품이 없는 경우
		if(ObjectUtils.isEmpty(categoryTop3)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
		}
		
		// 최종적으로 Map에 데이터를 담는 작업
		Map<String, Object> result = new HashMap<>();
		
		result.put("currentProduct", currentProduct);
		result.put("categoryTop3", categoryTop3);
		
		return ResponseEntity.ok(Result.successInstance(result));
	}
}
