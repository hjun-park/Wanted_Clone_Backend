package com.example.demo.src.application;



import com.example.demo.config.BaseException;
import com.example.demo.src.application.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isEmpty;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ApplicationService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicationDao applicationDao;
    private final ApplicationProvider applicationProvider;
    private final JwtService jwtService;


    @Autowired
    public ApplicationService(ApplicationDao applicationDao, ApplicationProvider applicationProvider, JwtService jwtService) {
        this.applicationDao = applicationDao;
        this.applicationProvider = applicationProvider;
        this.jwtService = jwtService;

    }
/*
    //(14)
    public PostApplicationRes createApplicant(PostApplicationReq postApplicationReq) throws BaseException {
        try{
            //recruitIdx로 companyIdx 받아오기
            postApplicationReq.setCompanyIdx(applicationDao.getCompanyIdx(postApplicationReq.getRecruitIdx()));

            //추천자 존재할 시 userName 가져오기
            if(!isEmpty(postApplicationReq.getRecommenderIdx())){
                postApplicationReq.setRecommenderName(applicationDao.getUserName(postApplicationReq.getRecommenderIdx()));
            }

            //지원
            int applicationIdx = applicationDao.createApplicant(postApplicationReq);
            //이력서 등록
            int cvResultNo = applicationDao.createApplicantCv(applicationIdx, postApplicationReq.getCvIdxes());

            return new PostApplicationRes(postApplicationReq.getUserIdx(),applicationIdx, cvResultNo);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


 */
}
