package com.example.demo.src.tag;

import com.example.demo.src.tag.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class TagDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Tag> findTagsByCompanyId(Long companyId) {
		String findTagQuery = "select T.tagIdx, T.name, companyIdx, T.tagCategoryIdx " +
			"from TagCompany as TC " +
			"inner join" +
			"    (select * from Tag where status = 'used') as T" +
			"    on TC.tagIdx = T.tagIdx " +
			"where T.status = 'used' and TC.status = 'used' and companyIdx = ?";
		String findTagParam = companyId.toString();

		return this.jdbcTemplate.query(findTagQuery,
			(rs, rowNum) -> Tag.builder()
				.tagIdx(rs.getLong("tagIdx"))
				.tagCategoryIdx(rs.getLong("tagCategoryIdx"))
				.name(rs.getString("name"))
				.build()
			, findTagParam);
	}

	public List<Tag> findTagByTagId(Long tagId) {
		String findTagQuery = "select * " +
			"from Tag " +
			"where status = 'used' and tagIdx = ?";
		String findTagParam = tagId.toString();

		return this.jdbcTemplate.query(findTagQuery,
			(rs, rowNum) -> Tag.builder()
				.name(rs.getString("name"))
				.tagCategoryIdx(rs.getLong("tagCategoryIdx"))
				.build()
			, findTagParam);
	}


	public Tag findTag(String tagName) {
		String findTagQuery = "select * from Tag " +
			"where name = ? and status = 'used'";
		try {
			return this.jdbcTemplate.queryForObject(findTagQuery,
				(rs, rowNum) -> Tag.builder()
					.tagIdx(rs.getLong("tagIdx"))
					.name(rs.getString("name"))
					.tagCategoryIdx(rs.getLong("tagCategoryIdx"))
					.build()
				, tagName);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Tag findTag(Long tagId) {
		String findTagQuery = "select * from Tag " +
			"where tagIdx = ? and status = 'used'";

		String findTagParam = tagId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findTagQuery,
				(rs, rowNum) -> Tag.builder()
					.tagIdx(rs.getLong("tagIdx"))
					.name(rs.getString("name"))
					.tagCategoryIdx(rs.getLong("tagCategoryIdx"))
					.build()
				, findTagParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	public int insertTagAndTagCompany(Long companyId, String tagName) {

		String insertTagQuery = "insert into Tag(name, status) " +
			"VALUES (?, 'deleted')";
		String lastInsertIdQuery = "select last_insert_id()";
		String insertTagCompany = "insert into TagCompany(tagIdx, companyIdx, status) " +
			"VALUES (?, ?, 'deleted')";


		this.jdbcTemplate.update(insertTagQuery, tagName);
		Long lastInsertedId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);


		Object[] insertTagCompanyParam = new Object[]{lastInsertedId, companyId.toString()};
		return jdbcTemplate.update(insertTagCompany, insertTagCompanyParam);

	}


}
