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
	
	/**
	 * @Desc : DashBoard에서 필요한 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 1.
	 * @param session
	 * @return
	 */
	@GetMapping("/getDashBoardInfo")
	public ResponseEntity<Result> getDashBoardInfo(HttpSession session) {
		
		try {
			
			// 현재 광고판 상품
			Product currentProduct = (Product)session.getAttribute("currentProduct");
			
			// 현재 카테고리의 Top3 상품
			List<CategoryTop3> categoryTop3 = adminService.getCategoryTop3(currentProduct.getKeyword());
			
			// 현재 카테고리의 타겟 연령대
			List<String> ageGroup = adminService.getAgeGroupOfCategory(currentProduct.getKeyword());
			
			// DashBoard 임시 데이터 생성
			int[][] bigLineChartData = adminService.getRandomData(2, 12);
			int[][] blueBarChartData = adminService.getRandomData(1, ageGroup.size());
			
			// 최종적으로 Map에 데이터를 담는 작업
			Map<String, Object> result = new HashMap<>();
			
			result.put("currentProduct", currentProduct);
			result.put("categoryTop3", categoryTop3);
			result.put("bigLineChartData", bigLineChartData);
			result.put("blueBarChartData", blueBarChartData);
			result.put("barChartColumns", ageGroup);
			
			return ResponseEntity.ok(Result.successInstance(result));
			
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failInstance());
		}
	}

	/**
	 * @Desc : 피드백 페이지 로드 시 필요한 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 6.
	 * @return
	 */
	@GetMapping("/getFeedbackData")
	public ResponseEntity<Result> getFeedbackData() {
		List<Product> productList = adminService.getAllProductList();
		
		if(ObjectUtils.isEmpty(productList)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
		}
		
		return ResponseEntity.ok(Result.successInstance(productList));
	}
}
