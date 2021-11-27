package com.example.demo.src.bookmark;

import com.example.demo.src.bookmark.model.BookmarkDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class BookmarkDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<BookmarkDTO> findBookmarks(int userId) {

		String findBookmarkQuery = "select * from Bookmark where userIdx = ? and status = 'used'";
		String findBookmarkParam = Integer.toString(userId);

		return this.jdbcTemplate.query(findBookmarkQuery,
			(rs, rowNum) -> BookmarkDTO.builder()
				.bookmarkIdx(rs.getLong("bookmarkIdx"))
				.userIdx(rs.getLong("userIdx"))
				.recruitIdx(rs.getLong("recruitIdx"))
				.build()
			, findBookmarkParam);
	}

	public BookmarkDTO findBookmark(int userId, Long recruitId) {

		String findBookmarkQuery = "select * from Bookmark " +
			"where userIdx = ? and recruitIdx = ? and status = 'used'";
		Object[] insertBookmarkParam = new Object[]{Integer.toString(userId), recruitId.toString()};

		try {
			return this.jdbcTemplate.queryForObject(findBookmarkQuery,
				(rs, rowNum) -> BookmarkDTO.builder()
					.bookmarkIdx(rs.getLong("bookmarkIdx"))
					.userIdx(rs.getLong("userIdx"))
					.recruitIdx(rs.getLong("recruitIdx"))
					.build()
				, insertBookmarkParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int insertBookmark(int userId, Long recruitId) {

		String insertBookmarkQuery = "insert into Bookmark(userIdx, recruitIdx) " +
			"VALUES(?, ?)";
		Object[] insertBookmarkParam = new Object[]{Integer.toString(userId), recruitId.toString()};

		return this.jdbcTemplate.update(insertBookmarkQuery, insertBookmarkParam);
	}

	public int updateBookmarkStatus(Long bookmarkId, int userId) {

		String updateBookmarkStatusQuery = "update Bookmark set status = 'deleted' " +
			"where bookmarkIdx = ? and userIdx = ?";
		Object[] updateBookmarkStatusParam = new Object[]{bookmarkId.toString(), Integer.toString(userId)};

		return this.jdbcTemplate.update(updateBookmarkStatusQuery, updateBookmarkStatusParam);
	}

}
