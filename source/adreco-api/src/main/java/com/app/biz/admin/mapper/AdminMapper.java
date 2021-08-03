package com.app.biz.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.app.biz.feedback.model.CategoryTop3;

@Mapper
public interface AdminMapper {
	List<CategoryTop3> getCategoryTop3(String category);
}
