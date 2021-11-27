package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.company.model.GetCompanyDetailRes;
import com.example.demo.src.company.model.GetCompanyNewsRes;
import com.example.demo.src.company.model.GetSearchCompanyRes;
import com.example.demo.src.company.model.GetTagCompanyRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/company")
@Validated
public class CompanyController {

	private final CompanyService companyService;
	private final JwtService jwtService;

	public CompanyController(CompanyService companyService, JwtService jwtService) {
		this.companyService = companyService;
		this.jwtService = jwtService;
	}

	/**
	 * [33] 태그명 기업명 검색 API
	 * @param tag
	 * @return List<GetTagCompanyRes>
	 */
	@GetMapping
	public BaseResponse<List<GetTagCompanyRes>> searchTagCompany(@RequestParam @NotBlank String tag) {
		try {
			List<GetTagCompanyRes> tagCompanyResList = companyService.searchTagCompany(tag);
			return new BaseResponse<>(tagCompanyResList);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [12] 기업명 검색 API
	 * @param keyword
	 * @return List<GetSearchCompanyRes>
	 */
	@GetMapping("/search")
	public BaseResponse<List<GetSearchCompanyRes>> getSearchCompany(@RequestParam @NotBlank String keyword) {
		try {
			List<GetSearchCompanyRes> getSearchCompanyResList = companyService.searchCompanies(keyword);
			return new BaseResponse<>(getSearchCompanyResList);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [13] 기업 세부정보 및 연관태그 조회
	 * @param companyId
	 * @return GetCompanyDetailRes
	 */
	@GetMapping("/{companyId}")
	public BaseResponse<GetCompanyDetailRes> getCompanyDetail(@PathVariable @NotNull @Positive Long companyId) {
		try {
			GetCompanyDetailRes getCompanyDetailResList = companyService.findCompanyDetail(companyId);
			return new BaseResponse<>(getCompanyDetailResList);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [54] 기업 관련 뉴스 크롤링
	 *
	 * @param keyword
	 * @return List<GetCompanyNewsRes>
	 */
	@GetMapping("/news")
	public BaseResponse<List<GetCompanyNewsRes>> getCompanyNews(@RequestParam @NotBlank String keyword) {

		try {
			List<GetCompanyNewsRes> news = companyService.crawlingNews(keyword);
			return new BaseResponse<>(news);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}

	}

}
