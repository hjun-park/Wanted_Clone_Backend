package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.like.model.GetLikeUserRes;
import com.example.demo.src.like.model.GetLikesRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/likes")
@Validated
public class LikeController {

	private final LikeService likeService;
	private final JwtService jwtService;

	public LikeController(LikeService likeService, JwtService jwtService) {
		this.likeService = likeService;
		this.jwtService = jwtService;
	}


	/**
	 * [29] 좋아요 목록 조회 API
	 * @return List<GetLikesRes>
	 */
	@GetMapping
	public BaseResponse<List<GetLikesRes>> getLikes() {
		try {
			List<GetLikesRes> getLikesRes = likeService.findLikes();
			return new BaseResponse<>(getLikesRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [30] 좋아요 등록 API
	 * @param recruitId
	 * @return <inserted row count>
	 */
	@PostMapping("/{recruitId}")
	public BaseResponse<Integer> postLike(@PathVariable @NotNull @Positive Long recruitId) {
		try {
			Integer addRow = likeService.createLike(recruitId);
			return new BaseResponse<>(addRow);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [31] 좋아요 삭제 API
	 * @param likeId
	 * @return <deleted row count>
	 */
	@PatchMapping("/{likeId}/status")
	public BaseResponse<Integer> updateLikeStatus(@PathVariable @NotNull @Positive Long likeId) {
		try {
			Integer updatedRow = likeService.modifyLikeStatus(likeId);
			return new BaseResponse<>(updatedRow);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [32] 채용공고 좋아요 유저 목록 조회 API
	 * @param companyId
	 * @return List<GetLikeUserRes>
	 */
	@GetMapping("/{companyId}")
	public BaseResponse<List<GetLikeUserRes>> getLikeUser(@PathVariable @NotNull @Positive Long companyId) {
		try {
			List<GetLikeUserRes> likeUsers = likeService.findLikeUsers(companyId);
			return new BaseResponse<>(likeUsers);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}


}
