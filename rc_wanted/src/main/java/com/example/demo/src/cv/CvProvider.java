package com.example.demo.src.cv;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cv.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class CvProvider {

    static String title = "";
    private final CvDao cvDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CvProvider(CvDao cvDao, JwtService jwtService) {
        this.cvDao = cvDao;
        this.jwtService = jwtService;
    }

    // (15)
    public List<Cv> getCvs(int userIdx) throws BaseException {
        try{
            List<Cv> Cvs = cvDao.getCvs(userIdx);
            return Cvs;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (4), (16)
    public String makeCvTitle(PostInitialCvReq postCvReq) throws BaseException{
        int userIdx = postCvReq.getUserIdx();

        List<String> names = cvDao.getUserCvTitles(userIdx);
        int size = names.size() + 1;
        String userName = postCvReq.getName() + " ";

        boolean anyMatch;

        do {
            title = userName + size;
            size++;
            anyMatch = names.stream()
                    .anyMatch(name -> name.equals(title));
        } while (anyMatch);

        return title;
    }

    // (17)
    public GetCvRes getCv(int userIdx, int cvIdx) throws BaseException {

            //조회하고자 하는 cv가 해당 유저의 cv인지 확인
            if(userIdx != cvDao.checkUser(cvIdx)){
                throw new BaseException(INVALID_USER_JWT);
            }
            //cv가 제대로 된 상태인지 확인
            if(!cvDao.checkCvStatus(cvIdx)){
                throw new BaseException(CHECK_CV);
            }

        try{
            Cv cv = cvDao.getCv(cvIdx);
            cv.setCvIdx(cvIdx);
            List<Career> careerList = cvDao.getCvCareer(cvIdx);
            careerList.forEach(career -> career.setAccompList(cvDao.getCvCareerAccomp(career.getCvCareerIdx())));
            List<Education> educationList = cvDao.getCvEducation(cvIdx);
            List<Skill> skillList = cvDao.getCvSkill(cvIdx);
            List<Additional> additionalList = cvDao.getCvAdditional(cvIdx);
            List<Language> languageList = cvDao.getCvLanguage(cvIdx);
            languageList.forEach(language -> language.setLanguageTestList(cvDao.getCvLanguageTest(language.getCvLanguageIdx())));
            List<Link> linkList = cvDao.getCvLink(cvIdx);
            GetCvRes getCvRes = new GetCvRes(userIdx, cvIdx, cv,  careerList, educationList,
                                                        skillList, additionalList, languageList, linkList);

            return getCvRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (16)
    public String makeCvIntro(int userIdx) throws BaseException{
        int userCareerPeriod = cvDao.getUserCareerPeriod(userIdx);
        String userCareer;
        switch(userCareerPeriod) {
            case 0:
                userCareer = "신입 ";
                break;
            case 10:
                userCareer = "10년차 ";
                break;
            default :
                userCareer = userCareerPeriod+"년차 ";
                break;
        }
        String jobName = cvDao.getJobName(userIdx);

        return "안녕하세요. " + userCareer + jobName + "입니다.";
    }


}
