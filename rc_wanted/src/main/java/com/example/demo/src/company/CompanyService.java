package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.*;

import java.util.HashMap;
import java.util.List;

public interface CompanyService {

	// 01 회사 정보만 가져오기
	Company findCompany(Long Id) throws BaseException;

	// 02 회사 이미지 정보 가져오기
	List<String> findImages(Long Id) throws BaseException;

	String findOneImage(Long Id) throws BaseException;

	// 03 회사와 이미지 정보 함께 가져오기
	GetCompanyWithImageRes findCompanyWithImage(Long companyId) throws BaseException;

	// 04 검색결과 반환
	List<GetSearchCompanyRes> searchCompanies(String keyword) throws BaseException;

	// 05 기업 상세정보 조회
	GetCompanyDetailRes findCompanyDetail(Long Id) throws BaseException;

	// 06 국가ID에 따른 기업 리스트 반환
	List<GetCompanyWithImageRes> findCompaniesWithImage(Long countryId) throws BaseException;

	// 06 국가 및 지역 ID에 따른 기업 리스트 반환
	List<GetCompanyWithImageRes> findCompaniesWithImage(Long countryId, Long regionId) throws BaseException;

	List<GetTagCompanyRes> searchTagCompany(String tagName) throws BaseException;

	List<TagCompany> findTagCompany(Long tagId) throws BaseException;

	// 07 모든 기업 리스트 반환
	List<GetCompanyWithImageRes> findCompaniesWithImage() throws BaseException;

    List<GetCompanyNewsRes> crawlingNews(String companyName) throws BaseException;
}
