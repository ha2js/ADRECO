package com.app.biz.adView.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.app.biz.ProductDataCrawling.model.Product;

@Mapper
public interface AdViewMapper {
	
	Product getAdView(String category);
}
