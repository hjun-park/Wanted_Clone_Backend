package com.example.demo.src.application;


import com.example.demo.config.BaseException;
import com.example.demo.src.application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ApplicationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // (30)
    public List<Application> getApplications(int userIdx){
        String getApplicationsQuery = "select applicationIdx, companyIdx, recruitIdx, date_format(createdAt,'%Y-%m-%d') as createdAt, applicationStatus, recommenderName, isCompensation from Application Where status = \"used\" && userIdx = ? Order by updatedAt DESC";
        return this.jdbcTemplate.query(getApplicationsQuery,
                (rs,rowNum) -> new Application(
                        rs.getInt("applicationIdx"),
                        rs.getInt("companyIdx"),
                        rs.getInt("recruitIdx"),
                        rs.getString("createdAt"),
                        rs.getString("applicationStatus"),
                        rs.getString("recommenderName"),
                        rs.getBoolean("isCompensation")),
                userIdx
                );
    }

    // (30)
    public List<Application> getApplicationsByStatus(int userIdx, String applicationStatus){
        String getApplicationsByStatusQuery = "select applicationIdx, companyIdx, recruitIdx, createdAt, applicationStatus, recommenderName, isCompensation from Application Where (status = \"used\" && userIdx = ? && applicationStatus = ? )Order by updatedAt DESC";
        Object[] getApplicationsByStatusParams = {userIdx, applicationStatus};
        return this.jdbcTemplate.query(getApplicationsByStatusQuery,
                (rs,rowNum) -> new Application(
                        rs.getInt("applicationIdx"),
                        rs.getInt("companyIdx"),
                        rs.getInt("recruitIdx"),
                        rs.getString("createdAt"),
                        rs.getString("applicationStatus"),
                        rs.getString("recommenderName"),
                        rs.getBoolean("isCompensation")),
                getApplicationsByStatusParams

        );
    }

    // (30)
    public String getCompanyName(Application application){
        String getCompanyNameQuery = "select name as companyName from Company where companyIdx = ?";
        int getCompanyNameParams = application.getCompanyIdx();
        return this.jdbcTemplate.queryForObject(getCompanyNameQuery, String.class, getCompanyNameParams );
    }

    // (30)
    public String getRecruitName(Application application){
        String getRecruitNameQuery = "select content as recruitName from Recruit where recruitIdx = ?";
        int getRecruitNameParams = application.getRecruitIdx();
        return this.jdbcTemplate.queryForObject(getRecruitNameQuery, String.class, getRecruitNameParams );
    }




}
