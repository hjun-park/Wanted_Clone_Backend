package com.example.demo.src.like;

import com.example.demo.src.like.model.LikeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class LikeDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<LikeDTO> findLikes(int userId) {

		String findLikeQuery = "select * from Likes where userIdx = ? and status = 'used'";
		String findLikeParam = Integer.toString(userId);

		return this.jdbcTemplate.query(findLikeQuery,
			(rs, rowNum) -> LikeDTO.builder()
				.likeIdx(rs.getLong("likeIdx"))
				.userIdx(rs.getInt("userIdx"))
				.recruitIdx(rs.getLong("recruitIdx"))
				.build()
			, findLikeParam);
	}

	public List<LikeDTO> findLikes(Long recruitId) {

		String findLikeQuery = "select * from Likes where recruitIdx = ? and status = 'used'";
		String findLikeParam = recruitId.toString();

		return this.jdbcTemplate.query(findLikeQuery,
			(rs, rowNum) -> LikeDTO.builder()
				.likeIdx(rs.getLong("likeIdx"))
				.userIdx(rs.getInt("userIdx"))
				.recruitIdx(rs.getLong("recruitIdx"))
				.build()
			, findLikeParam);
	}

	public LikeDTO findLike(int userId, Long recruitId) {

		String findLikeQuery = "select * from Likes " +
			"where userIdx = ? and recruitIdx = ? and status = 'used'";
		Object[] insertLikeParam = new Object[]{Integer.toString(userId), recruitId.toString()};

		try {
			return this.jdbcTemplate.queryForObject(findLikeQuery,
				(rs, rowNum) -> LikeDTO.builder()
					.likeIdx(rs.getLong("likeIdx"))
					.userIdx(rs.getInt("userIdx"))
					.recruitIdx(rs.getLong("recruitIdx"))
					.build()
				, insertLikeParam
			);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int insertLike(int userId, Long recruitId) {

		String insertLikeQuery = "insert into Likes(userIdx, recruitIdx) " +
			"VALUES(?, ?)";
		Object[] insertLikeParam = new Object[]{Integer.toString(userId), recruitId.toString()};

		return this.jdbcTemplate.update(insertLikeQuery, insertLikeParam);
	}

	public int updateLikeStatus(Long likeId, int userId) {

		String updateLikeStatusQuery = "update Likes set status = 'deleted' " +
			"where likeIdx = ? and userIdx = ?";
		Object[] updateLikeStatusParam = new Object[]{likeId.toString(), Integer.toString(userId)};

		return this.jdbcTemplate.update(updateLikeStatusQuery, updateLikeStatusParam);
	}

	public int findLikeCount(Long recruitId) {

		String findLikeCountQuery = "select COUNT(*) AS likeCount from Likes where recruitIdx = ? and status = 'used'";
		String findLikeCountParam = recruitId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findLikeCountQuery, Integer.class, findLikeCountParam);
		} catch (NullPointerException e) {
			return -1;
		}
	}

}
