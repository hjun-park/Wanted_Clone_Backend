package com.example.demo.src.cv;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.TableInfo;
import com.example.demo.src.cv.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/cvs")
public class CvController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CvProvider cvProvider;
    @Autowired
    private final CvService cvService;
    @Autowired
    private final JwtService jwtService;

    public CvController(CvProvider cvProvider, CvService cvService, JwtService jwtService){
        this.cvProvider = cvProvider;
        this.cvService = cvService;
        this.jwtService = jwtService;
    }

    // (15) [GET] /cvs - 이력서 목록 조회 API
    @ResponseBody
    @GetMapping("") //[GET] /cvs
    public BaseResponse<List<Cv>> getCvs(){
        try{
            int userIdx;
            //jwt에서 idx 추출 후 userIdx로 저장
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            //cv 목록 return
            return new BaseResponse<>(cvProvider.getCvs(userIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (16) [POST] /cvs - 기본 이력서 생성 API
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> postInitialCv(){
        try{
            int userIdx;
            //jwt에서 idx 추출 후 userIdx로 저장
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            //기본 이력서 생성 후 cvIdx return
            return new BaseResponse<>(cvService.createInitialCv(userIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (17) [GET] /cvs/:cvIdx - 이력서 상세 조회 API
    @ResponseBody
    @GetMapping("/{cvId}")
    public BaseResponse<GetCvRes> getCv(@PathVariable("cvId") int cvId){
        try{
            int userIdx;
            //jwt에서 idx 추출 후 userIdx로 저장
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            return new BaseResponse<>(cvProvider.getCv(userIdx, cvId));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //(18) [PATCH] /cvs/:cvIdx - 이력서 상세 정보 수정 API (업로드)
    @ResponseBody
    @PatchMapping("/{cvId}")
    public BaseResponse<Boolean> patchDetailedCv(@PathVariable("cvId") int cvIdx,
                                                @RequestBody PatchCvReq patchCvReq){
        try{
            //유효한 사용자의 접근인지 체크 후 userIdx로 저장
            try {
                int userIdx = jwtService.getUserIdx();
                patchCvReq.setUserIdx(userIdx);
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            //cvIdx 추출 후 저장
            patchCvReq.setCvIdx(cvIdx);

           return new BaseResponse<>(cvService.updateCv(patchCvReq));
        } catch (BaseException exception){
             return new BaseResponse<>(exception.getStatus());
        }
    }

    // (19) [PATCH] /cvs/:cvIdx/status
    @ResponseBody
    @PatchMapping("/{cvId}/status")
    public BaseResponse<PatchCvStatusRes> patchCvStatus(@PathVariable("cvId") int cvIdx,
                                               @RequestBody PatchCvStatusReq patchCvStatusReq){
        try{
            int userIdx;
            //jwt에서 idx 추출 후 userIdx로 저장
            try {
                userIdx = jwtService.getUserIdx();
            } catch (BaseException exception) {
                return new BaseResponse<>(INVALID_JWT);
            }
            //삭제
            if(patchCvStatusReq.getStatus() == true){
                PatchCvStatusRes patchCvStatusRes = new PatchCvStatusRes("이력서 삭제", cvService.updateCvStatus(userIdx, cvIdx));
                return new BaseResponse<>(patchCvStatusRes);
            }
            //메인 설정
            if(patchCvStatusReq.getIsMain() == true){
                PatchCvStatusRes patchCvStatusRes = new PatchCvStatusRes("이력서 메인 등록", cvService.updateCvMain(userIdx, cvIdx));
                return new BaseResponse<>(patchCvStatusRes);
            }
            PatchCvStatusRes patchCvStatusRes = new PatchCvStatusRes("이력서 상태 수정", false);
            return new BaseResponse<>(patchCvStatusRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (39) [POST] /cvs/:cvId/careers - empty initial CvCareer 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/careers")
    public BaseResponse<Integer> postCvCareer(@PathVariable("cvId") int cvIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvCareer,cvIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (40) [POST] /cvs/:cvId/careers/:careerId/career-accomps - empty initial CvCareerAccomp 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/careers/{careerId}/career-accomps")
    public BaseResponse<Integer> postCvCareerAccomp(@PathVariable("cvId") int cvIdx,
                                                    @PathVariable("careerId") int parentIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvCareerAccomp,parentIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (41) [POST] /cvs/:cvId/educations - empty initial CvCareerAccomp 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/educations")
    public BaseResponse<Integer> postCvEducation(@PathVariable("cvId") int cvIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvEducation,cvIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (42) [POST] /cvs/:cvId/additionals - empty initial CvEducation 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/additionals")
    public BaseResponse<Integer> postCvAdditional(@PathVariable("cvId") int cvIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvAdditional,cvIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (43) [POST] /cvs/:cvId/languages - empty initial CvLanguage 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/languages") //[POST] /cvs
    public BaseResponse<Integer> postCvLanguage(@PathVariable("cvId") int cvIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvLanguage,cvIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (44) [POST] /cvs/:cvId/languages/:languageId/language-tests - empty initial CvLanguageTest 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/languages/{languageId}/language-tests") //[POST] /cvs
    public BaseResponse<Integer> postCvCareer(@PathVariable("cvId") int cvIdx,
                                              @PathVariable("languageId") int parentIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvLanguageTest,parentIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (45) [POST] /cvs/:cvId/links - empty initial CvLink 생성 API
    @ResponseBody
    @PostMapping("/{cvId}/links") //[POST] /cvs
    public BaseResponse<Integer> postCvLink(@PathVariable("cvId") int cvIdx){
        try{
            return new BaseResponse<>(cvService.createInitialCvDetail(TableInfo.CvLink,cvIdx));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (46) [DELETE] /cvs/:cvId/careers/:careerId - 이력서 경력 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/careers/{careerId}")
    public BaseResponse<Boolean> deleteCvCareer(@PathVariable("careerId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvCareer,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (47) [DELETE] /cvs/:cvId/careers/:careerId/career-accomps/:careerAccompId - 이력서 경력 성과 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/careers/{careerId}/career-accomps/{careerAccompId}")
    public BaseResponse<Boolean> deleteCvCareer(@PathVariable("careerId") int parentIdx,
                                                @PathVariable("careerAccompId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvCareerAccomp,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (48) [DELETE] /cvs/:cvId/educations/:educationId - 이력서 학력 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/educations/{educationId}")
    public BaseResponse<Boolean> deleteCvEducation(@PathVariable("educationId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvCareer,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (49) [DELETE] /cvs/:cvId/additionals/:additionalId - 이력서 기타 수상 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/additionals/{additionalId}")
    public BaseResponse<Boolean> deleteCvAdditional(@PathVariable("additionalId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvCareer,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (50) [DELETE] /cvs/:cvId/languages/:languageId - 이력서 외국어 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/languages/{languageId}")
    public BaseResponse<Boolean> deleteCvLanguage(@PathVariable("languageId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvLanguage,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (50) [DELETE] /cvs/:cvId/languages/:languageId/language-tests/:languageTestId - 이력서 외국어 시험 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/languages/{languageId}/language-tests/{languageTestId}")
    public BaseResponse<Boolean> deleteCvLanguageTest(@PathVariable("languageTestId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvLanguageTest,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // (51) [DELETE] /cvs/:cvId/links/:linkId - 이력서 링크 항목 삭제 API
    @ResponseBody
    @DeleteMapping ("/{cvId}/links/{linkId}")
    public BaseResponse<Boolean> deleteCvLink(@PathVariable("cvId") int parentIdx,
                                                @PathVariable("linkId") int Idx){
        try{
            cvService.deleteCvDetail(TableInfo.CvLink,Idx);
            return new BaseResponse<>(true);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
