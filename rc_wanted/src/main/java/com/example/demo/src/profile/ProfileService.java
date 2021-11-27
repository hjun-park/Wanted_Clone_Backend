package com.example.demo.src.profile;



import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.cv.CvProvider;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.src.cv.CvDao;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isEmpty;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ProfileService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileDao profileDao;
    private final ProfileProvider profileProvider;
    private final CvProvider cvProvider;
    private final CvDao cvDao;
    private final JwtService jwtService;


    @Autowired
    public ProfileService(ProfileDao profileDao, ProfileProvider profileProvider, CvProvider cvProvider, CvDao cvDao, JwtService jwtService) {
        this.profileDao = profileDao;
        this.profileProvider = profileProvider;
        this.cvProvider = cvProvider;
        this.cvDao = cvDao;
        this.jwtService = jwtService;
    }

    //(2)
    public PostProfileRes createProfile(PostProfileReq postUserReq) throws BaseException {

        String pwd;
        try{
            //password 암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try{
            int userIdx = profileDao.createProfile(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostProfileRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //(3)
    public PostProfileSpecialtyRes createProfileSpecialty(PostProfileSpecialtyReq postProfileSpecialtyReq) throws BaseException {
        try{
            int userIdx, userGroupIdx, jobResultNo, skillResultNo = 0;

            userIdx = profileDao.updateUserCareerPeriod(postProfileSpecialtyReq);

            //기존에 설정된 직군 정보 삭제
            profileDao.deleteUserGroup(postProfileSpecialtyReq);
            userGroupIdx = profileDao.createUserGroup(postProfileSpecialtyReq);

            //기존에 설정된 직무 정보 삭제
            profileDao.deleteUserJob(postProfileSpecialtyReq);
            jobResultNo =  profileDao.createUserJob(postProfileSpecialtyReq);

            if(!isEmpty(postProfileSpecialtyReq.getSkillIdxes())){
                profileDao.deleteUserSkill(postProfileSpecialtyReq);
                skillResultNo =  profileDao.createUserSkill(postProfileSpecialtyReq);
            }

            if(!isEmpty(postProfileSpecialtyReq.getSalary())){
                profileDao.updateUserSalary(postProfileSpecialtyReq);
            }

            return new PostProfileSpecialtyRes(userIdx,userGroupIdx,jobResultNo,skillResultNo);


        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //(4)
    public int createProfileCareer(PostProfileCareerReq postProfileCareerReq) throws BaseException {
        try{
            int userIdx = postProfileCareerReq.getUserIdx();

            //새로 생성될 이력서를 메인으로 하기 위해 기존 이력서들 메인 여부 false로 변경
            cvDao.modifyOtherCvMain(userIdx);

            //cv 생성 위한 기본 정보 가져오기
            Cv cv = profileDao.getUserCvInfo(userIdx);
            cv.setTitle(cv.getName() + " 1");
            cv.setIntroduction(cvProvider.makeCvIntro(userIdx));

            //최초 생성 학력, 경력 정보를 담기 위한 기본 이력서 생성
            int cvIdx = profileDao.createCv(cv);

            //이력서-학력 관계 생성
            if(!isEmpty(postProfileCareerReq.getSchool())){
                profileDao.createCvEducation(postProfileCareerReq, cvIdx);
            }

            if(!isEmpty(postProfileCareerReq.getCompany())){
                profileDao.createCvCareer(postProfileCareerReq, cvIdx);
            }

            return cvIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // (5)
    public PostProfileInterestRes createProfileInterests(PostProfileInterestReq postProfileInterestReq) throws BaseException {
        try{
            //가입 목적(고민거리) update
            int userIdx = profileDao.updateUserPurpose(postProfileInterestReq);

            //keyword 관계 생성
            profileDao.deleteUserInterests(postProfileInterestReq);
            int createNo =  profileDao.createUserKeyword(postProfileInterestReq);
            return new PostProfileInterestRes(userIdx,createNo);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //(2), (3), (4)
    public void setUserProfileStatus(int level,  int userIdx) throws BaseException {
        try{
            profileDao.setUserProfileStatus(level, userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean modifyUser(PutUserReq putUserReq) throws BaseException {
        try{
            if(profileDao.modifyUser(putUserReq) == 1){
                return true;
            } else{
                throw new BaseException(MODIFY_FAIL_USER);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
