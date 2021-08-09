package com.app.biz.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.app.biz.ProductDataCrawling.model.Product;
import com.app.biz.admin.model.CategoryTop3;

@Mapper
public interface AdminMapper {
	List<CategoryTop3> getCategoryTop3(String category);
	List<String> getAgeGroupOfCategory(String category);
	List<String> getCategoryList();
	List<Product> getAllProductList();
}
