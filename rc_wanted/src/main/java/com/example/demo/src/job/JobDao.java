package com.example.demo.src.job;


import com.example.demo.config.BaseException;
import com.example.demo.config.TableInfo;
import com.example.demo.src.cv.model.*;
import com.example.demo.src.job.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Repository
public class JobDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Job> getJobGroups(){
        String getJobGroupsQUERY = "SELECT jobGroupIdx as jobIdx, title, image FROM JobGroups";
        return this.jdbcTemplate.query(getJobGroupsQUERY,
                (rs, rowNum) -> new Job(
                        rs.getInt("jobIdx"),
                        rs.getString("title"),
                        rs.getString("image"))
                );
    }

    public List<Job> getJobGroup(int jobGroupIdx){
        String getJobGroupQuery = "SELECT jobGroupIdx as jobIdx, title, image FROM JobGroups where jobGroupIdx = ?";
        return this.jdbcTemplate.query(getJobGroupQuery,
                (rs, rowNum) -> new Job(
                        rs.getInt("jobIdx"),
                        rs.getString("title"),
                        rs.getString("image")),
                jobGroupIdx
        );
    }

    public List<Job> getJob(int jobIdx){
        String getJobQuery = "SELECT jobIdx, title, image FROM Job where jobIdx = ?";
        return this.jdbcTemplate.query(getJobQuery,
                (rs, rowNum) -> new Job(
                        rs.getInt("jobIdx"),
                        rs.getString("title"),
                        rs.getString("image")),
                jobIdx
        );
    }

    public List<Job> getJobs(int jobGroupIdx){
        String getJobQuery = "SELECT jobIdx, title, image FROM Job where jobGroupIdx = ?";
        return this.jdbcTemplate.query(getJobQuery,
                (rs, rowNum) -> new Job(
                        rs.getInt("jobIdx"),
                        rs.getString("title"),
                        rs.getString("image")),
                jobGroupIdx
        );
    }

}
