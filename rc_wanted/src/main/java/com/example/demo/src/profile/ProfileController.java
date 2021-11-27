package com.example.demo.src.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProfileProvider profileProvider;
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final JwtService jwtService;

    public ProfileController(ProfileProvider profileProvider, ProfileService profileService, JwtService jwtService){
        this.profileProvider = profileProvider;
        this.profileService = profileService;
        this.jwtService = jwtService;
    }


    //(1) [POST] /profiles/emails - 유저 이메일 체크 API (2,4에 선행)
    @ResponseBody
    @PostMapping("/emails")
    public BaseResponse<Boolean> checkEmail(@RequestBody PostEmailReq postEmailReq){
        try{
            //형식 체크 후 관련 에러코드 반환
            if(!isRegexEmail(postEmailReq.getEmail())){return new BaseResponse<>(INVALID_EMAIL);}

            //필요없는 값 처리
            postEmailReq.setNonNecessaryValue(1);

            if(profileProvider.checkEmail(postEmailReq.getEmail()) == 1){
                return new BaseResponse<>(true);
            } else{
                return new BaseResponse<>(false);
            }

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    // (2) [POST] /profiles - 유저 기본 정보 등록 API (기본 회원가입)
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProfileRes> createProfile(@RequestBody PostProfileReq postProfileReq) {

        //각 요소들에 대해 null 관련 에러코드 반환
        if(isEmpty(postProfileReq.getEmail())){return new BaseResponse<>(POST_EMPTY_EMAIL);}
        if(isEmpty(postProfileReq.getName())){return new BaseResponse<>(POST_EMPTY_NAME);}
        if(isEmpty(postProfileReq.getPhone())){return new BaseResponse<>(POST_EMPTY_PASSWORD);}
        if(isEmpty(postProfileReq.getPhoneCountryCode())){return new BaseResponse<>(POST_EMPTY);}
        if(isEmpty(postProfileReq.getPassword())){return new BaseResponse<>(POST_EMPTY_PASSWORD);}
        if(isEmpty(postProfileReq.getPasswordConfig())){return new BaseResponse<>(POST_EMPTY_PASSWORD);}
        if(isEmpty(postProfileReq.getIsAcceptEmail())){postProfileReq.setIsAcceptEmail(false);}

        //각 요소들에 대해 형식 체크 관련 에러코드 반환
        if(!isRegexEmail(postProfileReq.getEmail())){return new BaseResponse<>(INVALID_EMAIL);}
        if(!isRegexPhone(postProfileReq.getPhone())){return new BaseResponse<>(INVALID_PHONE);}
        if(!isRegexPassword(postProfileReq.getPassword())){return new BaseResponse<>(INVALID_PASSWORD);}
        if(!isRegexPassword(postProfileReq.getPasswordConfig())){return new BaseResponse<>(INVALID_PASSWORD);}

        //패스워드 체크
        if(!postProfileReq.getPassword().equals(postProfileReq.getPasswordConfig())){return new BaseResponse<>(INVALID_PASSWORD);}

        try{
            PostProfileRes postProfileRes = profileService.createProfile(postProfileReq);
            profileService.setUserProfileStatus(1,  postProfileRes.getUserIdx());
            postProfileRes.setProfileLevel(1);
            return new BaseResponse<>(postProfileRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // (3) [POST] /profile/specialty - 유저 직무직군 정보 등록 API
    @ResponseBody
    @PostMapping("/specialties")
    public BaseResponse<PostProfileSpecialtyRes> createProfileSpecialty(@RequestBody PostProfileSpecialtyReq postProfileSpecialtyReq) {
        //유효한 사용자의 접근인지 체크 후 userIdx로 저장
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            postProfileSpecialtyReq.setUserIdx(userIdxByJwt);
        } catch (BaseException exception) {
            return new BaseResponse<>(INVALID_JWT);
        }

        //각 요소 null 체크 후 에러 코드 반환
        if(isEmpty(postProfileSpecialtyReq.getJobGroupIdx())){return new BaseResponse<>(POST_EMPTY_GROUPIDX);}
        if(isEmpty(postProfileSpecialtyReq.getJobIdxes())){return new BaseResponse<>(POST_EMPTY_JOBIDX);}

        try{
            PostProfileSpecialtyRes postProfileSpecialtyRes = profileService.createProfileSpecialty(postProfileSpecialtyReq);
            //메인 이력서 있는지 (있으면 profile level 3)
            if(profileProvider.checkIsMain(postProfileSpecialtyReq.getUserIdx()) != 1){
                profileService.setUserProfileStatus(2, postProfileSpecialtyRes.getUserIdx());
                postProfileSpecialtyRes.setProfileLevel(2);
            } else{
                postProfileSpecialtyRes.setProfileLevel(3);
            }
            return new BaseResponse<>(postProfileSpecialtyRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // (4) [POST] /profile/career - 유저 학력/경력 정보 등록 API
    @ResponseBody
    @PostMapping("/careers")
    public BaseResponse<PostProfileCareerRes> createProfileCareer(@RequestBody PostProfileCareerReq postProfileCareerReq) {
        //유효한 사용자의 접근인지 체크 후 userIdx로 저장
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            postProfileCareerReq.setUserIdx(userIdxByJwt);
        } catch (BaseException exception) {
            return new BaseResponse<>(INVALID_JWT);
        }

        //각 요소 null 체크 후 에러코드 반환
        if( (isEmpty(postProfileCareerReq.getCompany())) && (isEmpty(postProfileCareerReq.getSchool())) ){return new BaseResponse<>(POST_EMPTY_ALL);}

        try{
            int cvIdx = profileService.createProfileCareer(postProfileCareerReq);
            profileService.setUserProfileStatus(3, postProfileCareerReq.getUserIdx());
            return new BaseResponse<>(new PostProfileCareerRes(cvIdx, 3));
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // (5) [POST] /profile/interests - 유저 관심 키워드 설정 API
    @ResponseBody
    @PostMapping("/interests")
    public BaseResponse<PostProfileInterestRes> createProfileInterests(@RequestBody PostProfileInterestReq postProfileInterestReq) {
        //유효한 사용자의 접근인지 체크 후 userIdx 추출 저장
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            postProfileInterestReq.setUserIdx(userIdxByJwt);
        } catch (BaseException exception) {
            return new BaseResponse<>(INVALID_JWT);
        }

        //null 체크 후 관련 에러 코드 반환
        if(isEmpty(postProfileInterestReq.getPurpose())){return new BaseResponse<>(POST_EMPTY_PURPOSE);}
        if(isEmpty(postProfileInterestReq.getInterestIdxes())){return new BaseResponse<>(POST_EMPTY_KEYWORD);}

        try{
            PostProfileInterestRes postProfileInterestRes = profileService.createProfileInterests(postProfileInterestReq);
            return new BaseResponse<>(postProfileInterestRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // (6) [POST] /profiles/login - 로그인 API
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq){
        try{
            //null 체크 후 관련 에러코드 반환
            if(isEmpty(postLoginReq.getEmail())){return new BaseResponse<>(POST_EMPTY_EMAIL);}
            if(isEmpty(postLoginReq.getPassword())){return new BaseResponse<>(POST_EMPTY_PASSWORD);}

            //형식 체크 후 관련 에러코드 반환
            if(!isRegexEmail(postLoginReq.getEmail())){return new BaseResponse<>(INVALID_EMAIL);}
            if(!isRegexPassword(postLoginReq.getPassword())){return new BaseResponse<>(INVALID_PASSWORD);}

            //status 확인 후 관련 에러코드 반환
            if(!profileProvider.checkUser(postLoginReq.getEmail())){return new BaseResponse<>(CHECK_USER);}

            PostLoginRes postLoginRes = profileProvider.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (21) [GET] /profile - 특정 유저 조회 API
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetUserRes> getUser(){
        try{
            int userIdx;
            //유효한 사용자의 접근인지 체크 후 userIdx 추출
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            GetUserRes getUserRes = profileProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    // (22) [PUT] /profile - 특정정유저 기본 정보 수정 API
    @ResponseBody
    @PutMapping("")
    public BaseResponse<Boolean> putUser(@RequestBody PutUserReq putUserReq){
        try{
            int userIdx;
            //유효한 사용자의 접근인지 체크 후 userIdx 추출
            try {
                userIdx = jwtService.getUserIdx();
                putUserReq.setUserIdx(userIdx);
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            return new BaseResponse<>(profileService.modifyUser(putUserReq));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // () [GET] /profile/specialty - 특정 유저 조회 API
    @ResponseBody
    @GetMapping("/specialties")
    public BaseResponse<GetProfileSpecialtyRes> getUserSpecialty(){
        try{
            int userIdx;
            //유효한 사용자의 접근인지 체크 후 userIdx 추출
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            GetProfileSpecialtyRes getProfileSpecialtyRes = profileProvider.getProfileSpecialty(userIdx);
            return new BaseResponse<>(getProfileSpecialtyRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    // () [GET] /profile/career - 특정 유저 조회 API
    @ResponseBody
    @GetMapping("/careers")
    public BaseResponse<GetProfileCareerRes> getUserCareer(){
        try{
            int userIdx;
            //유효한 사용자의 접근인지 체크 후 userIdx 추출
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            GetProfileCareerRes getProfileCareerRes = profileProvider.getProfileCareer(userIdx);
            return new BaseResponse<>(getProfileCareerRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }





}
