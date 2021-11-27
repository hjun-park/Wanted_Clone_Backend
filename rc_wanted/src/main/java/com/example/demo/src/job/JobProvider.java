package com.example.demo.src.job;


import com.example.demo.config.BaseException;
import com.example.demo.src.cv.model.*;
import com.example.demo.src.job.model.Job;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class JobProvider {

    private final JobDao jobDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public JobProvider(JobDao jobDao, JwtService jwtService) {
        this.jobDao = jobDao;
        this.jwtService = jwtService;
    }

    public List<Job> getJobGroups() throws BaseException{
        try{
            List<Job> jobGroups = jobDao.getJobGroups();
            jobGroups.forEach(job -> {
                job.setSubJobs(jobDao.getJobs(job.getJobIdx()));
            });
            return jobGroups;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Job> getJobGroup(int jobGroupIdx) throws BaseException{
        try{
            List<Job> jobGroups = jobDao.getJobGroup(jobGroupIdx);
            jobGroups.forEach(job -> {
                job.setSubJobs(jobDao.getJobs(job.getJobIdx()));
            });
            return jobGroups;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Job> getJob(int jobIdx) throws BaseException{
        try{
            List<Job> jobs = jobDao.getJob(jobIdx);
            return jobs;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
