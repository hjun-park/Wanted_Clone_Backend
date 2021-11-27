package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.BookmarkDTO;
import com.example.demo.src.bookmark.model.GetBookmarksRes;
import com.example.demo.src.companyrecruit.CompanyRecruitService;
import com.example.demo.src.recruit.model.GetRecruitDetailRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@Service
@Slf4j
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

	private final BookmarkDAO bookmarkDAO;
	private final CompanyRecruitService companyRecruitService;
	private final JwtService jwtService;

	public BookmarkServiceImpl(BookmarkDAO bookmarkDAO, CompanyRecruitService companyRecruitService, JwtService jwtService) {
		this.bookmarkDAO = bookmarkDAO;
		this.companyRecruitService = companyRecruitService;
		this.jwtService = jwtService;
	}

	@Override
	public List<GetBookmarksRes> findBookmarks() throws BaseException {
		try {
			int userId = jwtService.getUserIdx();
			List<BookmarkDTO> bookmarks = bookmarkDAO.findBookmarks(userId);

			return bookmarks.stream()
				.map(bookmark -> {
					try {
						GetRecruitDetailRes recruit = companyRecruitService.findRecruitDetail(bookmark.getRecruitIdx());
						return new GetBookmarksRes(recruit.getImageList().isEmpty() ? null : recruit.getImageList().get(0),
							recruit.getRecruitTitle(), recruit.getCompanyName(), recruit.getCountry(),
							recruit.getRegion(), recruit.getReward() );
					} catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				})
				.collect(Collectors.toList());


		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	@Transactional
	public int createBookmark(Long recruitId) throws BaseException {

		// JWT 토근
		int userId = jwtService.getUserIdx();

		// 북마크 생성
		if (bookmarkDAO.findBookmark(userId, recruitId) == null) {
			return bookmarkDAO.insertBookmark(userId, recruitId);
		} else { // 기존 등록된 북마크가 있는 경우 에러처리하지 않고 변경 row 0
			return 0;
		}

	}

	@Override
	@Transactional
	public int modifyBookmarkStatus(Long bookmarkId) throws BaseException {

		// JWT 토큰 검증
		int userId = jwtService.getUserIdx();

		// 북마크 삭제 처리
		int updatedRow = bookmarkDAO.updateBookmarkStatus(bookmarkId, userId);

		if (updatedRow == 0) {
			throw new BaseException(INVALID_USER_JWT);
		} else {
			return updatedRow;
		}
	}
}
