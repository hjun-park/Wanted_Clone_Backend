package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.bookmark.model.GetBookmarksRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/bookmarks")
@Validated
public class BookmarkController {

	private final BookmarkService bookmarkService;
	private final JwtService jwtService;

	public BookmarkController(BookmarkService bookmarkService, JwtService jwtService) {
		this.bookmarkService = bookmarkService;
		this.jwtService = jwtService;
	}

	/**
	 * [26] 북마크 목록 조회 API
	 *
	 * @return List<GetBookmarkRes>
	 */
	@GetMapping
	public BaseResponse<List<GetBookmarksRes>> getBookmarks() {
		try {
			List<GetBookmarksRes> bookmarks = bookmarkService.findBookmarks();
			return new BaseResponse<>(bookmarks);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [27] 북마크 등록 API
	 *
	 * @param recruitId
	 * @return <inserted Row count>
	 */
	@PostMapping("/{recruitId}")
	public BaseResponse<Integer> postBookmark(@PathVariable @NotNull @Positive Long recruitId) {
		try {
			Integer addRow = bookmarkService.createBookmark(recruitId);
			return new BaseResponse<>(addRow);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	/**
	 * [28] 북마크 삭제 API
	 *
	 * @param bookmarkId
	 * @return <deleted Row count>
	 */
	@PatchMapping("/{bookmarkId}/status")
	public BaseResponse<Integer> updateBookmarkStatus(@PathVariable @NotNull @Positive Long bookmarkId) {
		try {
			Integer updatedRow = bookmarkService.modifyBookmarkStatus(bookmarkId);
			return new BaseResponse<>(updatedRow);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

}
