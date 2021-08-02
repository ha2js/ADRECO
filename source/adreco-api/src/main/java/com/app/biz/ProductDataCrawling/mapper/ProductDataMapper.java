package com.app.adView.biz.ProductDataCrawling.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.app.adView.biz.ProductDataCrawling.model.Product;

@Mapper
public interface ProductDataMapper {

	public void initProductData();
	public void getProductData(List<Product> productInfo);
}
