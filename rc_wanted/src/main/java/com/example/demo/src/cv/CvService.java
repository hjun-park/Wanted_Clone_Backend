package com.example.demo.src.cv;

import com.example.demo.config.BaseException;
import com.example.demo.config.TableInfo;
import com.example.demo.src.cv.model.*;
import com.example.demo.src.profile.ProfileDao;
import com.example.demo.src.profile.ProfileService;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isEmpty;

@Service
public class CvService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CvDao cvDao;
    private final CvProvider cvProvider;
    private final ProfileDao profileDao;
    private final JwtService jwtService;
    private final ProfileService profileService;

    @Autowired
    public CvService(CvDao cvDao, CvProvider cvProvider, ProfileDao profileDao, JwtService jwtService, ProfileService profileService) {
        this.cvDao = cvDao;
        this.cvProvider = cvProvider;
        this.profileDao = profileDao;
        this.jwtService = jwtService;
        this.profileService = profileService;
    }

    // (16)
    public int createInitialCv(int userIdx) throws BaseException {
        try{
            //userIdx로 기본 정보 가져오기
            PostInitialCvReq postInitialCvReq = cvDao.getUserCvInfo(userIdx);
            //이력서 기본 소개 및 제목 생성
            if(profileDao.checkUserProfileLevel(userIdx) >= 2){
                postInitialCvReq.setIntroduction(cvProvider.makeCvIntro(userIdx));
            } else{
                postInitialCvReq.setIntroduction("기본 간단소개입니다.");
            }
            postInitialCvReq.setTitle(cvProvider.makeCvTitle(postInitialCvReq));
            //기본 이력서 생성
            return cvDao.createInitialCv(postInitialCvReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (18)
    public void updateCvCareer(List<Career> careerList, int cvIdx) throws BaseException {
        try{
            for(Career career : careerList) {
                int cvCareerIdx = cvDao.updateCvCareer(career, cvIdx);
                if(!isEmpty(career.getAccompList())) {
                    for (Accomp accomp : career.getAccompList()) {
                        accomp.setCvCareerIdx(cvCareerIdx);
                        cvDao.updateCvCareerAccomp(accomp);
                    }
                }
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (18)
    public void updateCvLanguage(List<Language> languageList, int cvIdx) throws BaseException {
        try{
            for(Language language : languageList) {
                int cvLanguageIdx = cvDao.updateCvLanguage(language, cvIdx);
                if(!isEmpty(language.getLanguageTestList())) {
                    for (LanguageTest languageTest : language.getLanguageTestList()) {
                        languageTest.setCvLanguageIdx(cvLanguageIdx);
                        cvDao.updateCvLanguageTest(languageTest);
                    }
                }
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //(18)
    public boolean updateCv(PatchCvReq patchCvReq) throws BaseException{
        int cvIdx = patchCvReq.getCvIdx();
        //변경하고자 하는 cv가 해당 유저의 cv인지 확인
        if(patchCvReq.getUserIdx() != cvDao.checkUser(cvIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }
        try{
            if(!isEmpty(patchCvReq.getCv())){
                cvDao.updateCvInfo(patchCvReq.getCv(), cvIdx);
            }

            if(!isEmpty(patchCvReq.getCareerList())){
                this.updateCvCareer(patchCvReq.getCareerList(), cvIdx);
            }

            if(!isEmpty(patchCvReq.getEducationList())){
                cvDao.updateCvEducation(patchCvReq.getEducationList(),cvIdx);
            }

            if(!isEmpty(patchCvReq.getSkillList())){
                cvDao.createCvSkill(patchCvReq.getSkillList(), cvIdx);
            }

            if(!isEmpty(patchCvReq.getAdditionalList())){
                cvDao.updateCvAdditional(patchCvReq.getAdditionalList(),cvIdx);
            }

            if(!isEmpty(patchCvReq.getLanguageList())){
                this.updateCvLanguage(patchCvReq.getLanguageList(), cvIdx);
            }

            if(!isEmpty(patchCvReq.getLinkList())){
                cvDao.updateCvLink(patchCvReq.getLinkList(), cvIdx);
            }
            return true;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (19)
    public boolean updateCvStatus(int userIdx, int cvIdx) throws BaseException{
        //변경하고자 하는 cv가 해당 유저의 cv인지 확인
        if(userIdx != cvDao.checkUser(cvIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }
        try{
            if(cvDao.modifyCvStatus(cvIdx) == 1){
                //관련 세부항목 모두 삭제
                cvDao.deleteAllCvDetail(cvIdx);
                //현재 유저에게 기본 이력서가 존재하는지 확인 후, 없으면 profileLevel 2로 수정
                if(cvDao.isMainCvExist(userIdx) == 0){
                    profileService.setUserProfileStatus(2,userIdx);
                }
                return true;
            } else{
                throw new BaseException(DELETE_FAIL_CV);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // (19)
    public boolean updateCvMain(int userIdx, int cvIdx) throws BaseException{

        //변경하고자 하는 cv가 해당 유저의 cv인지 확인
        if(userIdx != cvDao.checkUser(cvIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }
        try{
            //현재 있는 메인 이력서의 메인 여부를 우선 수정
            cvDao.modifyOtherCvMain(userIdx);
            //이후 메인으로 등록
            if(cvDao.modifyCvMain(cvIdx) == 1){
                return true;
            } else{
                throw new BaseException(MODIFY_FAIL_CVMAIN);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // (39) ~ (45)
    public int createInitialCvDetail(TableInfo tableName, int cvIdx) throws BaseException {
        try{
            return(cvDao.createInitialCvDetail(tableName, cvIdx));
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (46) ~ (52)
    public void deleteCvDetail(TableInfo tableName, int Idx) throws BaseException {
        try{
            cvDao.deleteCvDetail(tableName, Idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
