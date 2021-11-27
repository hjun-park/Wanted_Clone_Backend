package com.example.demo.src.application;


import com.example.demo.config.BaseException;
import com.example.demo.src.application.model.Application;
import com.example.demo.src.application.model.GetApplicationRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ApplicationProvider {

    private final ApplicationDao applicationDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ApplicationProvider(ApplicationDao applicationDao, JwtService jwtService) {
        this.applicationDao = applicationDao;
        this.jwtService = jwtService;
    }

    // (30)
    public GetApplicationRes getApplications(int userIdx) throws BaseException {
        try {
            List<Application> applicationList = applicationDao.getApplications(userIdx);

            applicationList.forEach(application -> application.setCompanyName(applicationDao.getCompanyName(application)));
            applicationList.forEach(application -> application.setRecruitName(applicationDao.getRecruitName(application)));

            return new GetApplicationRes(userIdx, "전체", applicationList);

            } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (30)
    public GetApplicationRes getApplicationsByStatus(int userIdx, String applicationStatus) throws BaseException {
        try {
            List<Application> applicationList = applicationDao.getApplicationsByStatus(userIdx, applicationStatus);

            applicationList.forEach(application -> application.setCompanyName(applicationDao.getCompanyName(application)));
            applicationList.forEach(application -> application.setRecruitName(applicationDao.getRecruitName(application)));

            return new GetApplicationRes(userIdx, applicationStatus, applicationList);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }


    }

}
