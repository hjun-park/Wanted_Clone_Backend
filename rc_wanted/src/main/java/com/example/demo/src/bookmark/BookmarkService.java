package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.GetBookmarksRes;

import java.util.List;

public interface BookmarkService {

	// 31 북마크 목록 조회 (JWT 토큰 사용)
	List<GetBookmarksRes> findBookmarks() throws BaseException;

	// 32 북마크 등록
	int createBookmark(Long Id) throws BaseException;

	// 33 북마크 삭제
	int modifyBookmarkStatus(Long Id) throws BaseException;

}
