package com.example.demo.src.content;

import com.example.demo.src.content.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class ContentDAOImpl implements ContentDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public ContentDTO findContent(Long contentId) {
		String findContentQuery = "select * from Contents where contentId = ? and status = 'used'";
		String findContentParam = contentId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findContentQuery, getContentDTORowMapper(), findContentParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ContentDTO> findContentsByLabel(Long labelId) {

		String findContentsQuery = "select * " +
			"from Contents C " +
			"left join ContentLabel CL " +
			"on C.contentId = CL.contentId " +
			"where CL.labelId = ? and C.status = 'used'";
		String findContentsParam = labelId.toString();

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), findContentsParam);
	}

	@Override
	public List<ContentDTO> findContentsByType(Long typeId) {

		String findContentsQuery = "select * from Contents where typeIdx = ? and status = 'used'";
		String findContentsParam = typeId.toString();

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), findContentsParam);

	}

	@Override
	public List<ContentDTO> findContentsByPay(String pay) {

		String findContentsQuery = "select * from Contents where isFree = ? and status = 'used'";

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), pay);
	}

	@Override
	public List<ContentDTO> findContentsByLabelAndType(Long labelId, Long typeId) {

		String findContentsQuery = "select * " +
			"from Contents C " +
			"left join ContentLabel CL " +
			"on C.contentId = CL.contentId " +
			"where CL.labelId = ? and C.typeIdx = ? and C.status = 'used'";
		Object[] findContentsParam = new Object[]{labelId.toString(), typeId.toString()};

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), findContentsParam);
	}

	@Override
	public List<ContentDTO> findContentsByLabelAndPay(Long labelId, String pay) {

		String findContentsQuery = "select * " +
			"from Contents C " +
			"left join ContentLabel CL " +
			"on C.contentId = CL.contentId " +
			"where CL.labelId = ? and C.isFree = ? and C.status = 'used'";
		Object[] findContentsParam = new Object[]{labelId.toString(), pay};

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), findContentsParam);
	}

	@Override
	public List<ContentDTO> findContentsByTypeAndPay(Long typeId, String pay) {

		String findContentsQuery = "select * from Contents where typeIdx = ? and isFree = ? and status = 'used'";
		Object[] findContentsParam = new Object[]{typeId.toString(), pay};

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), findContentsParam);
	}

	@Override
	public List<ContentDTO> findContentsByLabelAndTypeAndPay(Long labelId, Long typeId, String pay) {

		String findContentsQuery = "select * " +
			"from Contents C " +
			"left join ContentLabel CL " +
			"on C.contentId = CL.contentId " +
			"where CL.labelId = ? and C.typeIdx = ? and C.isFree = ? and C.status = 'used'";
		Object[] findContentsParam = new Object[]{labelId.toString(), typeId.toString(), pay};

		return this.jdbcTemplate.query(findContentsQuery, getContentDTORowMapper(), findContentsParam);
	}


	@Override
	public List<ContentDTO> findContentsAll() {
		String findContentQuery = "select * from Contents where status = 'used'";

		return this.jdbcTemplate.query(findContentQuery,
			getContentDTORowMapper());
	}


	@Override
	public TypeDTO findType(Long typeId) {

		String findTypeQuery = "select * from Type where typeIdx = ? and status = 'used'";
		String findTypeParam = typeId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findTypeQuery,
				(rs, rowNum) -> TypeDTO.builder()
					.typeId(rs.getLong("typeIdx"))
					.name(rs.getString("name"))
					.build()
				, findTypeParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<TypeDTO> findTypes() {
		String findTypeQuery = "select * from Type where status = 'used'";

		return this.jdbcTemplate.query(findTypeQuery,
			(rs, rowNum) -> TypeDTO.builder()
				.typeId(rs.getLong("typeIdx"))
				.name(rs.getString("name"))
				.build());
	}

	@Override
	public ContentLabelDTO findContentLabel(Long contentLabelId) {
		String findContentLabelQuery = "select * from ContentLabel where contentLabelId = ? and status = 'used'";
		String findContentLabelParam = contentLabelId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findContentLabelQuery,
				(rs, rowNum) -> ContentLabelDTO.builder()
					.contentLabelId(rs.getLong("contentLabelId"))
					.contentId(rs.getLong("contentId"))
					.labelId(rs.getLong("labelId"))
					.build()
				, findContentLabelParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ContentLabelDTO> findContentLabels() {

		String findContentLabelQuery = "select * from ContentLabel where status = 'used'";

		return this.jdbcTemplate.query(findContentLabelQuery,
			(rs, rowNum) -> ContentLabelDTO.builder()
				.contentLabelId(rs.getLong("contentLabelId"))
				.contentId(rs.getLong("contentId"))
				.labelId(rs.getLong("labelId"))
				.build());
	}

	@Override
	public List<ContentLabelDTO> findContentLabelsBy(Long contentId) {
		String findContentLabelQuery = "select * from ContentLabel where contentId = ? and status = 'used'";
		String findContentLabelParam = contentId.toString();

		return this.jdbcTemplate.query(findContentLabelQuery,
			(rs, rowNum) -> ContentLabelDTO.builder()
				.contentLabelId(rs.getLong("contentLabelId"))
				.contentId(rs.getLong("contentId"))
				.labelId(rs.getLong("labelId"))
				.build()
			, findContentLabelParam);
	}


	@Override
	public ContentCategoryDTO findContentCategory(Long contentCategoryId) {

		String findContentCategoryQuery = "select * from ContentCategory where contentCategoryId = ? and status = 'used'";
		String findContentCategoryParam = contentCategoryId.toString();


		try {
			return this.jdbcTemplate.queryForObject(findContentCategoryQuery,
				(rs, rowNum) -> ContentCategoryDTO.builder()
					.contentCategoryId(rs.getLong("contentCategoryId"))
					.name(rs.getString("name"))
					.build()
				, findContentCategoryParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ContentCategoryDTO> findContentCategories() {
		String findContentCategoryQuery = "select * from ContentCategory where status = 'used'";

		return this.jdbcTemplate.query(findContentCategoryQuery,
			(rs, rowNum) -> ContentCategoryDTO.builder()
				.contentCategoryId(rs.getLong("contentCategoryId"))
				.name(rs.getString("name"))
				.build());
	}

	@Override
	public LabelDTO findLabel(Long labelId) {

		String findLabelQuery = "select * from Label where labelId = ? and status = 'used'";
		String findLabelParam = labelId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findLabelQuery,
				(rs, rowNum) -> LabelDTO.builder()
					.labelId(rs.getLong("labelId"))
					.name(rs.getString("name"))
					.build()
				, findLabelParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long insertContents(PostContentReq postContentReq) {

		String insertContentsQuery = "insert into Contents(contentCategoryId, title, startDate, content, isFree, " +
			"titleImage, endDate, isAllTime, typeIdx) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] insertContentsParam = new Object[]{postContentReq.getContentCategoryId(), postContentReq.getTitle(),
			postContentReq.getStartDate(), postContentReq.getContent(), postContentReq.getIsFree(), postContentReq.getTitleImage(),
			postContentReq.getEndDate(), postContentReq.getIsAllTime(), postContentReq.getTypeId()};

		this.jdbcTemplate.update(insertContentsQuery, insertContentsParam);

		String lastInsertIdQuery = "select last_insert_id()";
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);

	}

	@Override
	public Long updateContentsStatus(Long contentId) {

		String updateContentsStatusQuery = "update Contents set status = 'deleted' where contentId = ?";
		String updateContentsStatusParam = contentId.toString();

		return (long) this.jdbcTemplate.update(updateContentsStatusQuery, updateContentsStatusParam);
	}


	// ############################
	// INTERNAL USE
	// ############################
	private RowMapper<ContentDTO> getContentDTORowMapper() {
		return (rs, rowNum) -> ContentDTO.builder()
			.contentId(rs.getLong("contentId"))
			.contentCategoryId(rs.getLong("contentCategoryId"))
			.title(rs.getString("title"))
			.startDate(rs.getDate("startDate"))
			.content(rs.getString("content"))
			.isFree(rs.getString("isFree"))
			.titleImage(rs.getString("titleImage"))
			.endDate(rs.getDate("endDate"))
			.isAllTime(rs.getBoolean("isAllTime"))
			.typeId(rs.getLong("typeIdx"))
			.build();
	}

}
