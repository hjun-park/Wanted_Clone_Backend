package com.example.demo.src.salary;


import com.example.demo.config.BaseException;
import com.example.demo.src.salary.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.RESPONSE_EMPTY;
import static com.example.demo.config.BaseResponseStatus.RESPONSE_ERROR_JOBS;

@Repository
public class SalaryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Salary getJobSalary(int jobIdx, int careerPeriod) throws BaseException {
        String getJobSalaryQuery = "SELECT jobIdx, careerPeriod, avgSalary FROM JobSalary WHERE jobIdx = ? AND careerPeriod = ?";
        Object[] getJobSalaryParams = new Object[]{jobIdx, careerPeriod};
        try{
            return this.jdbcTemplate.queryForObject(getJobSalaryQuery,
                    (rs, rowNum) -> new Salary(
                            rs.getInt("jobIdx"),
                            rs.getInt("careerPeriod"),
                            rs.getInt("avgSalary")), getJobSalaryParams
                    );
        } catch (Exception e){
            throw new BaseException(RESPONSE_EMPTY);
        }
    }


    public int getOnlySalariesByJob(int jobIdx, int careerPeriod){
        String getOnlySalariesByJobQuery = "SELECT avgSalary FROM JobSalary WHERE jobIdx = ? && careerPeriod = ?";
        Object[] getOnlySalariesByJobParams = new Object[]{jobIdx, careerPeriod};

        return this.jdbcTemplate.queryForObject(getOnlySalariesByJobQuery,int.class,getOnlySalariesByJobParams);
    }

    public int getOnlySalaryByJobAllPeriod(int jobIdx){
        String getOnlySalaryByJobAllPeriodQuery = "SELECT avg(avgSalary) as avgSalary FROM JobSalary WHERE jobIdx = ?";

        return this.jdbcTemplate.queryForObject(getOnlySalaryByJobAllPeriodQuery,int.class,jobIdx);
    }


    public List<Integer> getJobsByGroup(int groupIdx) {
        String getJobsByGroupQuery = "SELECT jobIdx FROM Job WHERE jobGroupIdx = ?";
        return this.jdbcTemplate.query(getJobsByGroupQuery,
                (rs, rowNum) -> (rs.getInt("jobIdx")),
                groupIdx);
    }

    public String getJobName(int jobIdx) throws BaseException {
        String getJobNameQuery = "SELECT title FROM Job WHERE jobIdx = ?";
        try{
            return this.jdbcTemplate.queryForObject(getJobNameQuery, String.class, jobIdx);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_JOBS);
        }
    }

    public String getGroupName(int jobGroupIdx) throws BaseException {
        String getGroupNameQuery = "SELECT title FROM JobGroups WHERE jobGroupIdx = ?";
        try{
            return this.jdbcTemplate.queryForObject(getGroupNameQuery, String.class, jobGroupIdx);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_JOBS);
        }
    }




}
