package com.app.biz.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biz.admin.mapper.AdminMapper;
import com.app.biz.feedback.model.CategoryTop3;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	public List<CategoryTop3> getCategoryTop3(String category) {
		
		// 현재는 feedback 테이블에 데이터가 없기 때문에
		// 임시로 product_info 테이블의 위에서 3개만 가져옴
		return adminMapper.getCategoryTop3(category);
	}
}
