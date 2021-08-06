package com.app.biz.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.app.biz.ProductDataCrawling.model.Product;
import com.app.biz.admin.mapper.AdminMapper;
import com.app.biz.feedback.model.CategoryTop3;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	/**
	 * @Desc : 입력 받은 카테고리에 해당하는 상품들 중에서 시청률이 가장 높은 3가지 상품 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 1.
	 * @param category
	 * @return
	 */
	public List<CategoryTop3> getCategoryTop3(String category) {
		
		// 현재는 feedback 테이블에 데이터가 없기 때문에
		// 임시로 product_info 테이블의 위에서 3개만 가져옴
		return adminMapper.getCategoryTop3(category);
	}
	
	/**
	 * @Desc : 입력 받은 카테고리의 타켓 연령대 시청률 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 2.
	 * @param category
	 * @return
	 */
	public List<String> getAgeGroupOfCategory(String category) {
		return adminMapper.getAgeGroupOfCategory(category);
	}
	
	/**
	 * @Desc : 임시 데이터 생성
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 4.
	 * @param row
	 * @param col
	 * @return
	 */
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
	
	/**
	 * @Desc : 피드백 페이지 로드 시 필요한 정보 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 6.
	 * @return
	 */
	public Map<String, Object> getFeedbackData() {
		
		// 모든 상품 카테고리 리스트
		List<String> categoryList = getCategoryList();
		
		// 모든 상품 리스트
		List<Product> productList = getAllProductList();
		
		if(ObjectUtils.isEmpty(categoryList) || ObjectUtils.isEmpty(productList)) {
			return new HashMap<>();
		}
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("categoryList", getCategoryList());
		result.put("productList", getAllProductList());
		
		return result;
	}
	
	/**
	 * @Desc : 모든 카테고리 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 6.
	 * @return
	 */
	public List<String> getCategoryList() {
		return adminMapper.getCategoryList();
	}
	
	/**
	 * @Desc : 모든 상품 조회
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 8. 6.
	 * @return
	 */
	public List<Product> getAllProductList() {
		return adminMapper.getAllProductList();
	}
}
