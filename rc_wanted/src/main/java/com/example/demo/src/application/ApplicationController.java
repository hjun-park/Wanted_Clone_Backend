package com.example.demo.src.application;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.application.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ApplicationProvider applicationProvider;
    @Autowired
    private final ApplicationService applicationService;
    @Autowired
    private final JwtService jwtService;

    public ApplicationController(ApplicationProvider applicationProvider, ApplicationService applicationService, JwtService jwtService){
        this.applicationProvider = applicationProvider;
        this.applicationService = applicationService;
        this.jwtService = jwtService;
    }

    // (30) [GET] /applicantions?applicationStatus = {applicationStatus} - 유저 입사지원 목록 조회 API
    @ResponseBody
    @GetMapping("") //[POST] /applicantions/:recruitId
    public BaseResponse<GetApplicationRes> createApplicant(@RequestParam(required = false) String applicationStatus) {
        //jwt에서 idx 추출 후 userIdx로 저장
        int userIdx;
        try {
            userIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(INVALID_JWT);
        }

        try{
            if(!isEmpty(applicationStatus)){ //query string 있을 경우
                return new BaseResponse<>(applicationProvider.getApplicationsByStatus(userIdx, applicationStatus));
            } else{ //없을 경우
                return new BaseResponse<>(applicationProvider.getApplications(userIdx));
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
