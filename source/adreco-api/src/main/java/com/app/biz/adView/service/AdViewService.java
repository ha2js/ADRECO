package com.app.biz.adView.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.biz.ProductDataCrawling.model.Product;
import com.app.biz.adView.mapper.AdViewMapper;
import com.app.sys.constant.Const;
import com.app.sys.util.Result;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AdViewService {
	
	@Autowired
	private AdViewMapper adViewMapper;
	
	public ResponseEntity<Result> getAdView() {
		
		Random random = new Random();
		
		try {
			// 연령대 성별에 맞는 키워드 개수를 랜덤 클래스에 넣고 랜덤 추출
			// 임시로 모든 카테고리 개수를 넣고 추후에 변경할 예정
			int selectedCategory = random.nextInt(Const.categoryName.length);
			
			Product product = adViewMapper.getAdView(Const.categoryName[selectedCategory]);
			Result result = Result.successInstance(product);
			
			log.info("상품정보 : {}", product);
			
			return ResponseEntity.status(HttpStatus.OK).body(result);
			
		} catch (NullPointerException e) {
			log.info("해당 연령대와 성별에 맞는 카테고리가 없습니다.");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.failInstance());
		} catch(Exception e) {
			log.info("알 수 없는 오류 : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.internalServerErrorInstance());
		}
		
	}
}
