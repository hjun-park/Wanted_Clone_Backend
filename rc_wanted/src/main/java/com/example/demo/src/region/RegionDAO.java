package com.example.demo.src.region;

import com.example.demo.src.region.model.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Slf4j
public class RegionDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Region findRegion(Long regionId) {

		String findRegionQuery = "select * from Region where regionIdx = ? and status = 'used'";
		String findRegionParam = regionId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findRegionQuery,
				(rs, rowNum) -> Region.builder()
					.regionIdx(rs.getLong("regionIdx"))
					.name_eng(rs.getString("name_eng"))
					.name_kor(rs.getString("name_kor"))
					.build()
				, findRegionParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Region findRegion(String regionName) {

		String findRegionQuery = "select * from Region where name_eng = ? and status = 'used'";
		String findRegionParam = regionName;

		try {
			return this.jdbcTemplate.queryForObject(findRegionQuery,
				(rs, rowNum) -> Region.builder()
					.regionIdx(rs.getLong("regionIdx"))
					.name_eng(rs.getString("name_eng"))
					.name_kor(rs.getString("name_kor"))
					.build()
				, findRegionParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}

