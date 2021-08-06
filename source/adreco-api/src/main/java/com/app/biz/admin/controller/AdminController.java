package com.app.biz.admin.controller;

import java.util.Map;
import java.util.Objects;

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
import com.app.sys.util.Result;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	/**
	 * @Desc : DashBoard에 필요한 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 1.
	 * @param session
	 * @return
	 */
	@GetMapping("/getDashBoardInfo")
	public ResponseEntity<Result> getDashBoardInfo(HttpSession session) {
		
		// 현재 광고판 상품
		Product currentProduct = (Product)session.getAttribute("currentProduct");
		
		Map<String, Object> resultMap = adminService.getDashBoardInfo(currentProduct);
		
		if(Objects.isNull(currentProduct)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failInstance());
		}
		
		if(ObjectUtils.isEmpty(currentProduct)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
		}
		
		return ResponseEntity.ok(Result.successInstance(resultMap));
	}

	/**
	 * @Desc : 피드백 페이지 로드 시 필요한 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 6.
	 * @return
	 */
	@GetMapping("/getFeedbackData")
	public ResponseEntity<Result> getFeedbackData() {
		
		Map<String, Object> resultMap = adminService.getFeedbackData();
		
		if(resultMap.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.failInstance());
		}
		
		return ResponseEntity.ok(Result.successInstance(resultMap));
	}
}
