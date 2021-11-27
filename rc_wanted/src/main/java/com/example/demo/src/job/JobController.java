package com.example.demo.src.job;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.job.model.Job;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.ValidationRegex.isEmpty;


@RestController
@RequestMapping("/jobs")
public class JobController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final JobProvider jobProvider;
    @Autowired
    private final JwtService jwtService;

    public JobController(JobProvider jobProvider, JwtService jwtService){
        this.jobProvider = jobProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<Job>> getJobGroup(@RequestParam(value = "group", required = false) Integer jobGroupIdx, @RequestParam(value = "job", required = false) Integer jobIdx){
        try{

            if(isEmpty(jobIdx)) {
                if(isEmpty(jobGroupIdx)){
                    return new BaseResponse<>(jobProvider.getJobGroups());
                } else{
                    return new BaseResponse<>(jobProvider.getJobGroup(jobGroupIdx));
                }
            } else{
                return new BaseResponse<>(jobProvider.getJob(jobIdx));
            }

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
