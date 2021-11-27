package com.example.demo.src.recruit;


import com.example.demo.config.BaseException;
import com.example.demo.src.recruit.model.*;
import lombok.extern.slf4j.Slf4j;
import org.intellij.lang.annotations.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
@Slf4j
public class RecruitDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Recruit findRecruitById(Long recruitId) {
		String findRecruitQuery = "select * from Recruit where recruitIdx = ? and status = 'used'" +
			" order by updatedAt desc";
		String findRecruitParam = recruitId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Recruit> findRecruits() {
		String findRecruitQuery = "select * from Recruit where status = 'used' order by updatedAt desc";
		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper());
	}

	public List<Recruit> findRecruits(Long companyId) {
		String findRecruitQuery = "select * from Recruit where companyIdx = ? and status = 'used' order by updatedAt desc";
		String findRecruitParam = companyId.toString();

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruits(int year, Long companyId) {
		String findRecruitQuery = "select * from Recruit where minExperience <= ? and companyIdx = ? " +
			"and status = 'used' order by updatedAt desc";
		Object[] findRecruitParam = new Object[]{Integer.toString(year), companyId.toString()};

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruitGroupAll(Long groupId) {
		String findRecruitQuery = "select * from Recruit where jobGroupIdx = ? and status = 'used'" +
			" order by updatedAt desc";
		String findRecruitParam = groupId.toString();

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruitGroupAll(Long groupId, Long companyId) {
		String findRecruitQuery = "select * from Recruit where jobGroupIdx = ?" +
			" and companyIdx = ? and status = 'used'" +
			" order by updatedAt desc";
		Object[] findRecruitParam = new Object[]{groupId.toString(), companyId.toString()};

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruitGroupAll(Long groupId, int year, Long companyId) {
		String findRecruitQuery = "select * from Recruit where jobGroupIdx = ?" +
			" and minExperience <= ? and companyIdx = ? and status = 'used'";
		Object[] findRecruitParam = new Object[]{groupId.toString(), Integer.toString(year), companyId.toString()};

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruitJobAll(Long groupId, Long jobId) {

		String findRecruitQuery = "select * from Recruit where jobGroupIdx = ? and jobIdx = ? and status = 'used'";
		Object[] findRecruitParam = new Object[]{groupId.toString(), jobId.toString()};

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruitJobAll(Long groupId, Long jobId, Long companyId) {

		String findRecruitQuery = "select * from Recruit where jobGroupIdx = ? and jobIdx = ? " +
			"and companyIdx = ? and status = 'used'";
		Object[] findRecruitParam = new Object[]{groupId.toString(), jobId.toString(), companyId.toString()};

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public List<Recruit> findRecruitJobAll(Long groupId, Long jobId, int year, Long companyId) {

		String findRecruitQuery = "select * from Recruit where jobGroupIdx = ? and jobIdx = ?" +
			" and minExperience <= ? and companyIdx = ? and status = 'used'";
		Object[] findRecruitParam = new Object[]{groupId.toString(), jobId.toString(), Integer.toString(year),
			companyId.toString()};

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}


	public List<Recruit> findRecruitByTitle(String keyword) {

		@Language("MySQL")
		String findRecruitQuery = "select * from Recruit where REGEXP_LIKE(title, ?, 'i') and status = 'used'" +
			" order by updatedAt desc";

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), keyword);

	}

	public List<Recruit> findRecruitAllByCompanyId(Long companyId) {

		@Language("MySQL")
		String findRecruitQuery = "select * from Recruit where companyIdx = ? and status = 'used'" +
			" order by updatedAt desc";
		String findRecruitParam = companyId.toString();

		return this.jdbcTemplate.query(findRecruitQuery, getRecruitRowMapper(), findRecruitParam);
	}

	public Long updateRecruitStatus(Long recruitId) {

		String updateRecruitStatusQuery = "update Recruit set status = 'deleted' where recruitIdx = ?";
		String updateRecruitStatusParam = recruitId.toString();

		return (long) this.jdbcTemplate.update(updateRecruitStatusQuery, updateRecruitStatusParam);
	}



	// =============================
	// INTERNAL USE
	// =============================
	private RowMapper<Recruit> getRecruitRowMapper() {
		return (rs, rowNum) -> Recruit.builder()
			.recruitIdx(rs.getLong("recruitIdx"))
			.companyIdx(rs.getLong("companyIdx"))
			.title(rs.getString("title"))
			.likeCount(rs.getInt("likeCount"))
			.content(rs.getString("content"))
			.deadline(rs.getDate("deadline"))
			.reward(rs.getInt("reward"))
			.jobIdx(rs.getLong("jobIdx"))
			.minExperience(rs.getInt("minExperience"))
			.isAlways(rs.getBoolean("isAlways"))
			.build();
	}



	//(14)
	public int createApplicant(PostApplicationReq postApplicationReq){
		String createApplicantQuery = "insert into Application (companyIdx, recruitIdx, recommenderIdx, recommenderName, userIdx) VALUES (?,?,?,?,?)";
		Object[] createApplicantParams = new Object[]{postApplicationReq.getCompanyIdx(), postApplicationReq.getRecruitIdx(), postApplicationReq.getRecommenderIdx(), postApplicationReq.getRecommenderName(), postApplicationReq.getUserIdx()};

		this.jdbcTemplate.update(createApplicantQuery, createApplicantParams);

		String lastInserIdQuery = "select last_insert_id()";
		return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
	}

	//(14)
	public int createApplicantCv(int applicationIdx, int[] cvIdxes){
		String createApplicantCvQuery = "insert into ApplicationCv (applicationIdx, cvIdx) Values (?,?)";

		int result = 0;
		for(int cvIdx : cvIdxes){
			Object[] createApplicantCvParams = new Object[]{applicationIdx, cvIdx};
			this.jdbcTemplate.update(createApplicantCvQuery, createApplicantCvParams);
			result += 1;
		}

		return result;


	}

	//(14)
	public int getCompanyIdx(int recruitIdx) {
		String getCompanyIdxQuery = "select companyIdx from Recruit where recruitIdx = ?";
		int getCompanyIdxParams = recruitIdx;

		return this.jdbcTemplate.queryForObject(getCompanyIdxQuery,
			int.class,
			getCompanyIdxParams);
	}

	//(14)
	public String getUserName(int userIdx) throws BaseException {
		String getUserNameQuery = "select name from User where userIdx = ?";
		int getUserNameParams = userIdx;

		return this.jdbcTemplate.queryForObject(getUserNameQuery,
			String.class,
			getUserNameParams);
	}
}
