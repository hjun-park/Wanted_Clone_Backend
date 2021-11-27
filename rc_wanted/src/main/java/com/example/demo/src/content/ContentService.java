package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.src.content.model.GetContentDetailRes;
import com.example.demo.src.content.model.GetContentReq;
import com.example.demo.src.content.model.GetContentRes;
import com.example.demo.src.content.model.PostContentReq;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ContentService {

	// 41 커리어 성장 목록 조회
	List<GetContentRes> findContents(@RequestParam GetContentReq getContentReq) throws BaseException;

	// 42 커리어 성장 상세 페이지
	GetContentDetailRes findContentDetail(@PathVariable Long contentId) throws BaseException;

	// 55 커리어 성장 페이지 등록
	Long registerContents(PostContentReq postContentReq) throws BaseException;

	// 56 커리어 성장 페이지 삭제
	Long updateContentsStatus(@PathVariable Long contentId) throws BaseException;


}
