package com.example.demo.src.tag;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.tag.model.PostTagReq;
import com.example.demo.src.tag.model.Tag;
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
@RequestMapping("/tags")
@Validated
public class TagController {

	private final TagService tagService;
	private final JwtService jwtService;

	public TagController(TagService tagService, JwtService jwtService) {
		this.tagService = tagService;
		this.jwtService = jwtService;
	}


	/**
	 * [34] 기업 태그 조회 API
	 * @param companyId
	 * @return List<Tag>
	 */
	@GetMapping("/{companyId}")
	public BaseResponse<List<Tag>> getTags(@PathVariable @NotNull @Positive Long companyId) {
		try {
			List<Tag> tags = tagService.findTags(companyId);
			return new BaseResponse<>(tags);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [35] 기업 태그 등록 의견 API
	 * @param companyId
	 * @param postTagReq
	 * @return <inserted row count>
	 */
	@PostMapping("/{companyId}")
	public BaseResponse<Integer> postTag(@PathVariable @NotNull @Positive Long companyId,
										 @Valid @RequestBody PostTagReq postTagReq) {
		try {
			Integer insertedRowNum = tagService.registerTag(companyId, postTagReq.getTagName());
			return new BaseResponse<>(insertedRowNum);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}
}
