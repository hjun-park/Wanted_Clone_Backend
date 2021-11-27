package com.example.demo.src.profile;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.example.demo.src.cv.CvDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ProfileProvider {

    private final ProfileDao profileDao;
    private final CvDao cvDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProfileProvider(ProfileDao profileDao, CvDao cvDao, JwtService jwtService) {
        this.profileDao = profileDao;
        this.cvDao = cvDao;
        this.jwtService = jwtService;
    }

    //(1)
    public int checkEmail(String email) throws BaseException{
        try{
            return profileDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //(1)
    public boolean checkUser(String email) throws BaseException{
        try{
            return profileDao.checkUser(email).equals("used");
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException{
        User user = profileDao.getPwd(postLoginReq);
        System.out.println(1);
        //password 복호화
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        //password 확인 후 response 반환
        if(postLoginReq.getPassword().equals(password)){
            int userIdx = profileDao.getPwd(postLoginReq).getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(jwt,userIdx);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }


    // (21)
    public GetUserRes getUser(int userIdx) throws BaseException{
        if(!profileDao.checkUserStatus(userIdx)){
            throw new BaseException(CHECK_USER);
        }

        try{
            GetUserRes getUserRes = profileDao.getUserNecessary(userIdx);

            getUserRes.setInterest(profileDao.getInterest(userIdx).get());

            return getUserRes;

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetProfileSpecialtyRes getProfileSpecialty(int userIdx) throws BaseException{
        if(profileDao.checkUserProfileLevel(userIdx)<2){
            throw new BaseException(RESPONSE_ERROR_LEVEL2);
        }

        try{
            GetProfileSpecialtyRes getProfileSpecialtyRes = new GetProfileSpecialtyRes();

            getProfileSpecialtyRes.setJobGroup(profileDao.getJobGroups(userIdx));
            getProfileSpecialtyRes.setJobList(profileDao.getJobsList(userIdx));
            getProfileSpecialtyRes.setCareerPeriod(profileDao.getUserCareerPeriod(userIdx));
            getProfileSpecialtyRes.setSalary(profileDao.getUserSalary(userIdx));
            getProfileSpecialtyRes.setSkillList(profileDao.getUserSkillList(userIdx));

            return getProfileSpecialtyRes;

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }

    public GetProfileCareerRes getProfileCareer(int userIdx) throws BaseException{
        if(profileDao.checkUserProfileLevel(userIdx)<2){
            throw new BaseException(RESPONSE_ERROR_LEVEL3);
        }

        try{
            GetProfileCareerRes getProfileCareerRes = profileDao.getCvInfo(userIdx);
            return getProfileCareerRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }

	public UserDTO getUser(String email) throws BaseException{
		try{
			return profileDao.getUser(email);
		} catch (Exception exception){
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public int checkIsMain(int userIdx) throws BaseException{
        try{
            return cvDao.isMainCvExist(userIdx);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }




}
