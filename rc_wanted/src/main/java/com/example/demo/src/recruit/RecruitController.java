package com.example.demo.src.recruit;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.companyrecruit.CompanyRecruitService;
import com.example.demo.src.recruit.model.*;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_JWT;
import static com.example.demo.config.BaseResponseStatus.POST_EMPTY_CV;
import static com.example.demo.utils.ValidationRegex.isEmpty;

@Slf4j
@RestController
@RequestMapping("/recruits")
@Validated
public class RecruitController {

	private final CompanyRecruitService companyRecruitService;
	private final RecruitService recruitService;
	private final JwtService jwtService;

	public RecruitController(CompanyRecruitService companyRecruitService, RecruitService recruitService, JwtService jwtService) {
		this.companyRecruitService = companyRecruitService;
		this.recruitService = recruitService;
		this.jwtService = jwtService;
	}

	/**
	 * [38] 채용공고 전체 리스트 조회 API
	 *
	 * @param getRecruitsReq
	 * @return List<GetRecruitsRes>
	 */
	@GetMapping
	public BaseResponse<List<GetRecruitsRes>> getRecruits(@Valid GetRecruitsReq getRecruitsReq) {
		try {
			List<GetRecruitsRes> getRecruitsRes = companyRecruitService.findRecruits(getRecruitsReq);
			return new BaseResponse<>(getRecruitsRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [7] 직군 채용공고 조회 API
	 *
	 * @param groupId
	 * @param getRecruitsReq
	 * @return List<GetRecruitsRes>
	 */
	@GetMapping("/{groupId}")
	public BaseResponse<List<GetRecruitsRes>> getRecruitGroup(@PathVariable @NotNull @Positive Long groupId,
															  @Valid GetRecruitsReq getRecruitsReq) {
		try {
			List<GetRecruitsRes> getRecruitsRes = companyRecruitService.findRecruitGroup(groupId, getRecruitsReq);
			return new BaseResponse<>(getRecruitsRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [8] 직무 채용공고 조회 API
	 *
	 * @param groupId
	 * @param jobId
	 * @param getRecruitsReq
	 * @return List<GetRecruitsRes>
	 */
	@GetMapping("/{groupId}/{jobId}")
	public BaseResponse<List<GetRecruitsRes>> getRecruitJob(@PathVariable @NotNull @Positive Long groupId,
															@PathVariable @NotNull @Positive Long jobId,
															@Valid GetRecruitsReq getRecruitsReq) {
		try {
			List<GetRecruitsRes> getRecruitsRes = companyRecruitService.findRecruitJob(groupId, jobId, getRecruitsReq);
			return new BaseResponse<>(getRecruitsRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}


	/**
	 * [9] 채용공고 상세 조회 API
	 *
	 * @param recruitId
	 * @return GetREcruitDetailRes
	 */
	@GetMapping("/detail/{recruitId}")
	public BaseResponse<GetRecruitDetailRes> getRecruit(@PathVariable @NotNull @Positive Long recruitId) {
		try {
			GetRecruitDetailRes getRecruitDetailRes = companyRecruitService.findRecruitDetail(recruitId);
			return new BaseResponse<>(getRecruitDetailRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}


	/**
	 * [10] 포지션 검색 API
	 *
	 * @param keyword
	 * @return List<GetRecruitsRes>
	 */
	@GetMapping("/search")
	public BaseResponse<List<GetRecruitsRes>> getSearchRecruit(@RequestParam @NotBlank String keyword) {
		try {
			List<GetRecruitsRes> getRecruitsRes = companyRecruitService.searchRecruit(keyword);
			return new BaseResponse<>(getRecruitsRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [11] 기업 채용공고 조회 API
	 *
	 * @param companyId
	 * @return List<GetRecruitsCompany>
	 */
	@GetMapping("/company/{companyId}")
	public BaseResponse<List<GetRecruitsCompany>> getRecruitsCompany(@PathVariable @NotNull @Positive Long companyId) {
		try {
			List<GetRecruitsCompany> getRecruitsCompanyList = recruitService.findRecruitsCompany(companyId);
			return new BaseResponse<>(getRecruitsCompanyList);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [57] 채용공고 마감 삭제 API
	 *
	 * @param recruitId
	 * @return <updated row count>
	 */
	@PatchMapping("/{recruitId}/status")
	public BaseResponse<Long> patchRecruit(@PathVariable @NotNull @Positive Long recruitId) {
		try {
			return new BaseResponse<>(recruitService.updateRecruitStatus(recruitId));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}


	// (14) [POST] /recruits/:recruitId/applications - 입사지원 API
	@ResponseBody
	@PostMapping("/{recruitId}/applications")
	public BaseResponse<PostApplicationRes> createApplicant(@PathVariable("recruitId") int recruitId, @RequestBody PostApplicationReq postApplicationReq) {
		//jwt에서 idx 추출 후 userIdx로 저장 및 pathvariable requestDTO에 set
		try {
			int userIdxByJwt = jwtService.getUserIdx();
			postApplicationReq.setUserIdx(userIdxByJwt);
		} catch (BaseException exception) {
			return new BaseResponse<>(INVALID_JWT);
		}
		postApplicationReq.setRecruitIdx(recruitId);

		//null 체크
		if (isEmpty(postApplicationReq.getCvIdxes())) {
			return new BaseResponse<>(POST_EMPTY_CV);
		}

		try {
			PostApplicationRes postApplicationRes = recruitService.createApplicant(postApplicationReq);
			return new BaseResponse<>(postApplicationRes);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

}
