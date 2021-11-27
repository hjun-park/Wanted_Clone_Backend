package com.example.demo.src.company;

import com.example.demo.src.company.model.Company;
import com.example.demo.src.company.model.CompanyImage;
import com.example.demo.src.company.model.TagCompany;
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
public class CompanyDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Company findCompanyById(Long companyId) {

		String findCompanyQuery = "select * from Company where companyIdx = ? and status = 'used'";
		String findCompanyParam = companyId.toString();

		try {
			return this.jdbcTemplate.queryForObject(findCompanyQuery, companyRowMapper(), findCompanyParam);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<CompanyImage> findImagesById(Long companyId) {

		String findImagesQuery = "select image from CompanyImage where status = 'used' and companyIdx = ?";
		String findImageParam = companyId.toString();

		return this.jdbcTemplate.query(findImagesQuery,
			(rs, rowNum) -> CompanyImage.builder()
				.image(rs.getString("image"))
				.build(),
			findImageParam);
	}

	public List<Company> findCompanyByName(String keyword) {

		String findCompanyQuery = "select * from Company where REGEXP_LIKE(name, ?, 'i')  and status = 'used'";

		return this.jdbcTemplate.query(findCompanyQuery, companyRowMapper(), keyword);
	}

	public List<Company> findCompanies(Long countryId, Long regionId) {

		String findCompanyQuery = "select * from Company " +
			"WHERE status = 'used' and countryIdx = ? and regionIdx = ?";
		Object[] findCompanyParam = new Object[]{countryId.toString(), regionId.toString()};

		return this.jdbcTemplate.query(findCompanyQuery, companyRowMapper(), findCompanyParam);
	}

	public List<Company> findCompanies(Long countryId) {

		String findCompanyQuery = "select * from Company " +
			"WHERE status = 'used' and countryIdx = ?";
		String findCompanyParam = countryId.toString();

		return this.jdbcTemplate.query(findCompanyQuery, companyRowMapper(), findCompanyParam);
	}

	public List<Company> findCompanies() {

		String findCompanyQuery = "select * from Company " +
			"WHERE status = 'used'";

		return this.jdbcTemplate.query(findCompanyQuery, companyRowMapper());
	}


	public List<TagCompany> findTagCompany(Long tagId) {

		String findCompanyId = "select * " +
			"from TagCompany " +
			"where status = 'used' and tagIdx = ?";
		String findCompanyParam = tagId.toString();

		return this.jdbcTemplate.query(findCompanyId,
			(rs, rowNum) -> TagCompany.builder()
				.companyIdx(rs.getLong("companyIdx"))
				.tagIdx(rs.getLong("tagIdx"))
				.build()
			, findCompanyParam);
	}


	// =============================
	// INTERNAL USE
	// =============================
	private RowMapper<Company> companyRowMapper() {
		return (rs, rowNum) -> Company.builder()
			.companyIdx(rs.getLong("companyIdx"))
			.name(rs.getString("name"))
			.countryIdx(rs.getLong("countryIdx"))
			.regionIdx(rs.getLong("regionIdx"))
			.address(rs.getString("address"))
			.licenseNumber(rs.getString("licenseNumber"))
			.sales(rs.getInt("sales"))
			.employees(rs.getInt("employees"))
			.intro(rs.getString("intro"))
			.year(rs.getString("year"))
			.email(rs.getString("email"))
			.phone(rs.getString("phone"))
			.url(rs.getString("url"))
			.keyword(rs.getString("keyword"))
			.responseRate(rs.getInt("responseRate"))
			.logo(rs.getString("logo"))
			.latitude(rs.getDouble("latitude"))
			.longitude(rs.getDouble("longitude"))
			.build();
	}

}
