package com.example.demo.src.salary;


import com.example.demo.config.BaseException;
import com.example.demo.src.salary.model.GetSalaryRes;
import com.example.demo.src.salary.model.Salary;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SalaryProvider {

    private final SalaryDao salaryDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SalaryProvider(SalaryDao salaryDao, JwtService jwtService) {
        this.salaryDao = salaryDao;
        this.jwtService = jwtService;
    }

    public GetSalaryRes getGroupSalary(int groupIdx, int careerPeriod) throws BaseException{
        try{
            GetSalaryRes getSalaryRes = new GetSalaryRes("직군", groupIdx);
            getSalaryRes.setTitle(salaryDao.getGroupName(groupIdx));

            switch(careerPeriod){
                case 0 : getSalaryRes.setPeriod("신입"); break;
                case 10 : getSalaryRes.setPeriod("10년 이상"); break;
                default : getSalaryRes.setPeriod(careerPeriod + "년");
            }

            List<Integer> salaryList = new ArrayList();
            salaryDao.getJobsByGroup(groupIdx).forEach(integer ->
                    salaryList.add(salaryDao.getOnlySalariesByJob(integer, careerPeriod)));
            double avg = salaryList.stream().mapToInt(Integer::intValue).average().orElse(0);
            getSalaryRes.setAvgSalary((int)avg);

            return getSalaryRes;

        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetSalaryRes getJobSalary(int jobIdx, int careerPeriod) throws BaseException{
        try{
            GetSalaryRes getSalaryRes = new GetSalaryRes("직무", jobIdx);
            getSalaryRes.setTitle(salaryDao.getJobName(jobIdx));

            Salary salary = salaryDao.getJobSalary(jobIdx, careerPeriod);

            switch(salary.getCareerPeriod()){
                case 0 : getSalaryRes.setPeriod("신입"); break;
                case 10 : getSalaryRes.setPeriod("10년 이상"); break;
                default : getSalaryRes.setPeriod(salary.getCareerPeriod() + "년");
            }
            getSalaryRes.setAvgSalary(salary.getAvgSalary());

            return getSalaryRes;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetSalaryRes getGroupSalaryAllPeriod(int groupIdx) throws BaseException{
        try{
            GetSalaryRes getSalaryRes = new GetSalaryRes("직군", groupIdx);

            getSalaryRes.setTitle(salaryDao.getGroupName(groupIdx));
            getSalaryRes.setPeriod("모든 경력기간");

            List<Integer> salaryList = new ArrayList();
            salaryDao.getJobsByGroup(groupIdx).forEach(integer ->
                    salaryList.add(salaryDao.getOnlySalaryByJobAllPeriod(integer)));
            double avg = salaryList.stream().mapToInt(Integer::intValue).average().orElse(0);
            getSalaryRes.setAvgSalary((int)avg);

            return getSalaryRes;

        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetSalaryRes getJobSalaryAllPeriod(int jobIdx) throws BaseException{
        try{
            GetSalaryRes getSalaryRes = new GetSalaryRes("직무", jobIdx);
            getSalaryRes.setTitle(salaryDao.getJobName(jobIdx));

            getSalaryRes.setPeriod("모든 경력기간");
            getSalaryRes.setAvgSalary(salaryDao.getOnlySalaryByJobAllPeriod(jobIdx));

            return getSalaryRes;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
