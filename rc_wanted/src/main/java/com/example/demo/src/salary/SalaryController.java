package com.example.demo.src.salary;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.salary.model.GetSalaryRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.demo.utils.ValidationRegex.isEmpty;



@RestController
@RequestMapping("/salaries")
public class SalaryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SalaryProvider salaryProvider;
    @Autowired
    private final JwtService jwtService;

    public SalaryController(SalaryProvider salaryProvider, JwtService jwtService){
        this.salaryProvider = salaryProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("") // /salary?group={group}&&job={job}%%period={period}
    public BaseResponse<GetSalaryRes> getSalary(@RequestParam(value = "group", required = false) Integer jobGroup,
                                                  @RequestParam(value = "job", required = false) Integer job,
                                                  @RequestParam(value = "period", required = false) Integer period){

        try{
            if(isEmpty(jobGroup)){
                if(isEmpty(period)){
                    GetSalaryRes result = salaryProvider.getJobSalaryAllPeriod(Optional.ofNullable(job).orElse(1));
                    return new BaseResponse<> (result);
                } else {
                    GetSalaryRes result = salaryProvider.getJobSalary(Optional.ofNullable(job).orElse(1), period);
                    return new BaseResponse<>(result);
                }
            } else {
                if(isEmpty(job)){
                    if(isEmpty(period)){
                        GetSalaryRes result = salaryProvider.getGroupSalaryAllPeriod(jobGroup);
                        return new BaseResponse<>(result);
                    } else {
                        GetSalaryRes result = salaryProvider.getGroupSalary(jobGroup, period);
                        return new BaseResponse<> (result);
                    }
                } else{
                    if(isEmpty(period)){
                        GetSalaryRes result = salaryProvider.getGroupSalaryAllPeriod(jobGroup);
                        return new BaseResponse<>(result);
                    } else {
                        GetSalaryRes result = salaryProvider.getGroupSalary(jobGroup, period);
                        return new BaseResponse<> (result);
                    }
                }
            }
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
