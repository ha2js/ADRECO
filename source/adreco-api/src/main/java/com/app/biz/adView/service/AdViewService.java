package com.app.biz.adView.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.biz.ProductDataCrawling.model.Product;
import com.app.biz.adView.mapper.AdViewMapper;
import com.app.sys.constant.Const;


@Service
public class AdViewService {
	
	@Autowired
	private AdViewMapper adViewMapper;
	
	/**
	 * @Desc : 광고판에 게시할 상품 선택
	 * @Author : "SangHoon Lee"
	 * @Date : 2021. 7. 25.
	 * @return
	 */
	public Product getProduct() {
		
		Random random = new Random();
		
		// 연령대 성별에 맞는 키워드 개수를 랜덤 클래스에 넣고 랜덤 추출
		// 임시로 모든 카테고리 개수를 넣고 추후에 변경할 예정
		int selectedCategory = random.nextInt(Const.categoryName.length);
		
		// 선택한 카테고리의 상품을 랜덤으로 추출
		Product product = adViewMapper.getAdView(Const.categoryName[selectedCategory]);
		
		return product;
	}
}
