package com.app.adView.biz.adView.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.app.adView.biz.ProductDataCrawling.model.Product;

@Mapper
public interface AdViewMapper {
	
	Product getAdView(String category);
}
