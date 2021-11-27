package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.content.model.GetContentDetailRes;
import com.example.demo.src.content.model.GetContentReq;
import com.example.demo.src.content.model.GetContentRes;
import com.example.demo.src.content.model.PostContentReq;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contents")
@Validated
public class ContentController {

	private final ContentService contentService;
	private final JwtService jwtService;

	public ContentController(ContentService contentService, JwtService jwtService) {
		this.contentService = contentService;
		this.jwtService = jwtService;
	}


	/**
	 * [36] 컨텐츠 카테고리 조회 API
	 * @param getContentReq
	 * @return List<GetContentRes>
	 */
	@GetMapping
	public BaseResponse<List<GetContentRes>> getContents(@Valid GetContentReq getContentReq) {
		try {
			List<GetContentRes> contents = contentService.findContents(getContentReq);
			return new BaseResponse<>(contents);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}


	/**
	 * [37] 커리어 성장 컨텐츠 상세조회 API
	 * @param contentId
	 * @return content
	 */
	@GetMapping("/{contentId}")
	public BaseResponse<GetContentDetailRes> getContentDetail(@PathVariable @NotNull @Positive Long contentId) {
		try {
			GetContentDetailRes content = contentService.findContentDetail(contentId);
			return new BaseResponse<>(content);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [55] 커리어 성장 컨텐츠 등록 API
	 * @param postContentReq
	 * @return id
	 */
	@PostMapping
	public BaseResponse<Long> postContents(@Valid @RequestBody PostContentReq postContentReq) {
		try {
			return new BaseResponse<>(contentService.registerContents(postContentReq));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [56] 커리어 성장 컨텐츠 삭제 API
	 * @param contentId
	 * @return <updated row count>
	 */
	@PatchMapping("/{contentId}/status")
	public BaseResponse<Long> patchContents(@PathVariable @NotNull @Positive Long contentId) {
		try {
			return new BaseResponse<>(contentService.updateContentsStatus(contentId));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

}
