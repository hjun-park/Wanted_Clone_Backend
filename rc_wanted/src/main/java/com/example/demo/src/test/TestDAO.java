package com.example.demo.src.test;

import com.example.demo.src.profile.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class TestDAO{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public String getUsers() throws Exception{
		try {
			List<Integer> integerList;
			String getUsersQuery = "select * from TEST";

			integerList = this.jdbcTemplate.query(getUsersQuery,
				(rs, rowNum) -> rs.getInt("att1"));

			log.info(String.valueOf(integerList));
			System.out.println("getUsersQuery = " + getUsersQuery);
			System.out.println("getUsersQuery = " + integerList);

			return String.valueOf(integerList);

		} catch (Exception exception) {
			exception.printStackTrace();
			return exception.getMessage();
		}

	}

}
