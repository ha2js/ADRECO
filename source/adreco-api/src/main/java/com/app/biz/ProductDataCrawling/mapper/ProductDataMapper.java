package com.app.biz.ProductDataCrawling.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.app.biz.ProductDataCrawling.model.Product;

@Mapper
public interface ProductDataMapper {

	public void initProductData();
	public void getProductData(List<Product> productInfo);
}
