package com.example.demo.src.country;

import com.example.demo.src.country.model.CountryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Slf4j
public class CountryDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public CountryDTO findCountry(Long countryId) {

		String findCountryQuery = "select * from Country where countryIdx = ? and status = 'used'";
		String findCountryParam = countryId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findCountryQuery,
				(rs, rowNum) -> CountryDTO.builder()
					.countryIdx(rs.getLong("countryIdx"))
					.name_eng(rs.getString("name_eng"))
					.name_kor(rs.getString("name_kor"))
					.build()
				, findCountryParam);
		} catch (Exception exception) {
			return null;
		}
	}

	public CountryDTO findCountry(String countryName) {

		String findCountryQuery = "select * from Country where name_eng = ? and status = 'used'";
		String findCountryParam = countryName;

		try {
			return this.jdbcTemplate.queryForObject(findCountryQuery,
				(rs, rowNum) -> CountryDTO.builder()
					.countryIdx(rs.getLong("countryIdx"))
					.name_eng(rs.getString("name_eng"))
					.name_kor(rs.getString("name_kor"))
					.build()
				, findCountryParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
