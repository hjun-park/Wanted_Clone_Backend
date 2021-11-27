package com.example.demo.src.tag;

import com.example.demo.config.BaseException;
import com.example.demo.src.tag.model.Tag;

import java.util.List;

public interface TagService {

	//39 기업 태그 조회
	List<Tag> findTags(Long companyId) throws BaseException;

	//40 기업 태그 의견 등록
	int registerTag(Long companyId, String tagName) throws BaseException;

	Tag findTag(Long tagId) throws BaseException;

	Tag findTag(String tagName) throws BaseException;

}
