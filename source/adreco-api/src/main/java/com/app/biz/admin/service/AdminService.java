package com.app.biz.admin.service;

import java.util.List;
import java.util.Random;

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
	
	public List<String> getAgeGroupOfCategory(String category) {
		return adminMapper.getAgeGroupOfCategory(category);
	}
	
	public int[][] getRandomData(int row, int col) {
		Random random = new Random();
		
		int[][] tableData = new int[row][col];
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				tableData[i][j] = random.nextInt(100) + 1;
			}
		}
		
		return tableData;
	}
}
