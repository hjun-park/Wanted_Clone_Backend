package com.example.demo.batch.job;


import com.example.demo.batch.tasklet.InitializeProcedure;
import com.example.demo.batch.JobInfo;
import com.example.demo.batch.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class jobSalaryBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource; // DataSource DI

    private static final int chunkSize = 10;

    @Bean
    public Job jobCalcJobSalaryBatchJob() {
        return jobBuilderFactory.get("jobCalcJobSalaryBatchJob")
                .start(jobInitializeBatchStep())
                .next(jobTotalSalaryCalcStep())
                .next(jobAvgSalaryCalcStep())
                //.start(jobAvgSalaryCalcStep1())
                .build();
    }

    //Step1: jobSalary 계산 전 JobSalary의 count, totalSalary 초기화
    @Bean
    public Step jobInitializeBatchStep() {
        return stepBuilderFactory.get("jobInitializeBatchStep")
                .tasklet((contribution, chunkContext)->
                        new InitializeProcedure(dataSource)
                                .execute(contribution, chunkContext))
                .build();
    }

    //Step2: 유저 salary 가져와서 job의 totalSalary에 sum
    @Bean
    @JobScope
    public Step jobTotalSalaryCalcStep() {
        return stepBuilderFactory.get("jobTotalSalaryCalcStep")
                .<UserInfo, UserInfo>chunk(chunkSize)
                .reader(jobTotalSalaryCalcReader())
                .processor(jobTotalSalaryCalcProcessor())
                .writer(jobTotalSalaryCalcWritter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<UserInfo> jobTotalSalaryCalcReader() {
        return new JdbcCursorItemReaderBuilder<UserInfo>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(UserInfo.class))
                .sql("Select User.userIdx, salary, salaryPeriod, jobIdx, careerPeriod From User " +
                        "INNER JOIN UserJob on User.userIdx = UserJob.userIdx " +
                        "WHERE salaryCurrency = \"KRW\"")
                .name("jobSalaryCalcStepReader")
                .build();
    }

    public ItemProcessor<UserInfo, UserInfo> jobTotalSalaryCalcProcessor(){
        return userInfo -> {
            if((userInfo.getSalaryPeriod()).equals("연간")){
                userInfo.setTotalSalary(userInfo.getSalary());
            } else{
                userInfo.setTotalSalary(userInfo.getSalary()*12);
            }
            return userInfo;
        };
    }

    @Bean
    public JdbcBatchItemWriter<UserInfo> jobTotalSalaryCalcWritter() {
        return new JdbcBatchItemWriterBuilder<UserInfo>()
                .dataSource(dataSource)
                .sql("UPDATE JobSalary SET totalSalary=totalSalary+:totalSalary, count=count+1 WHERE (jobIdx=:jobIdx) AND (careerPeriod=:careerPeriod)")
                .beanMapped()
                .build();
    }

    //Step3: 해당 job avgSalary 계산
    @Bean
    public Step jobAvgSalaryCalcStep() {
        return stepBuilderFactory.get("jobCountBatchStep")
                .<JobInfo, JobInfo>chunk(chunkSize)
                .reader(jobAvgSalaryCalcReader())
                .processor(jobAvgSalaryCalcProcessor())
                .writer(jobAvgSalaryCalcWritter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<JobInfo> jobAvgSalaryCalcReader() {
        return new JdbcCursorItemReaderBuilder<JobInfo>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(JobInfo.class))
                .sql("Select jobSalaryIdx, jobIdx, count, totalSalary From JobSalary WHERE count>=1")
                .name("jobAvgSalaryCalcReader")
                .build();
    }

    public ItemProcessor<JobInfo, JobInfo> jobAvgSalaryCalcProcessor(){
        return jobInfo -> {
            jobInfo.setAvgSalary(jobInfo.getTotalSalary()/jobInfo.getCount());
            return jobInfo ;
        };
    }

    @Bean
    public JdbcBatchItemWriter<JobInfo> jobAvgSalaryCalcWritter() {
        return new JdbcBatchItemWriterBuilder<JobInfo>()
                .dataSource(dataSource)
                .sql("UPDATE JobSalary SET avgSalary=:avgSalary WHERE jobSalaryIdx = :jobSalaryIdx")
                .beanMapped()
                .build();
    }




/*

    @Bean
    public Step jobCountBatchStep() {
        return stepBuilderFactory.get("jobCountBatchStep")
                .<JobInfo, JobInfo>chunk(chunkSize)
                .reader(jobCountBatchStepReader())
                .writer(jobCountBatchStepWritter())
                .build();
    }

 */


/*



    @Bean
    public JdbcCursorItemReader<JobInfo> jobCountBatchStepReader() {
        return new JdbcCursorItemReaderBuilder<JobInfo>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(JobInfo.class))
                .sql("SELECT jobIdx, count, totalSalary FROM JobSalary")
                .name("jobCountBatchStepReader")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<JobInfo> jobCountBatchStepWritter() {
        return new JdbcBatchItemWriterBuilder<JobInfo>()
                .dataSource(dataSource)
                .sql("UPDATE JobSalary SET count = 0, totalSalary = 0")
                .beanMapped()
                .build();
    }

    //Step2: 유저 salary 가져와서 job의 totalSalary에 sum
    @Bean
    public Step jobTotalSalaryCalcStep() {
        return stepBuilderFactory.get("jobCountBatchStep")
                .<UserInfo, UserInfo>chunk(chunkSize)
                .reader(jobTotalSalaryCalcReader())
                .processor(jobTotalSalaryCalcProcessor())
                .writer(jobTotalSalaryCalcWritter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<UserInfo> jobTotalSalaryCalcReader() {
        return new JdbcCursorItemReaderBuilder<UserInfo>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(UserInfo.class))
                .sql("Select User.userIdx, salary, salaryPeriod, jobIdx, careerPeriod From User " +
                        "INNER JOIN UserJob on User.userIdx = UserJob.userIdx " +
                        "WHERE salaryCurrency = \"KRW\"")
                .name("jobSalaryCalcStepReader")
                .build();
    }

    public ItemProcessor<UserInfo, UserInfo> jobTotalSalaryCalcProcessor(){
        return userInfo -> {
            if((userInfo.getSalaryPeriod()).equals("월간")){
                userInfo.setTotalSalary(userInfo.getSalary()*12);
            } else{
                userInfo.setTotalSalary(userInfo.getSalary());
            }
            return userInfo;
        };
    }

    @Bean
    public JdbcBatchItemWriter<UserInfo> jobTotalSalaryCalcWritter() {
        return new JdbcBatchItemWriterBuilder<UserInfo>()
                .dataSource(dataSource)
                .sql("UPDATE JobSalary SET totalSalary=totalSalary+:totalSalary, count=count+1 WHERE (jobIdx=:jobIdx) AND (careerPeriod=:careerPeriod)")
                .beanMapped()
                .build();
    }

    //Step3: 해당 job avgSalary 계산
    @Bean
    public Step jobAvgSalaryCalcStep() {
        return stepBuilderFactory.get("jobCountBatchStep")
                .<UserInfo, UserInfo>chunk(chunkSize)
                .reader(jobAvgSalaryCalcReader())
                .processor(jobAvgSalaryCalcProcessor())
                .writer(jobAvgSalaryCalcWritter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<JobInfo> jobAvgSalaryCalcReader() {
        return new JdbcCursorItemReaderBuilder<JobInfo>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(JobInfo.class))
                .sql("Select jobIdx, count, totalSalary From JobSalary")
                .name("jobAvgSalaryCalcReader")
                .build();
    }

    public ItemProcessor<JobInfo, JobInfo> jobAvgSalaryCalcProcessor(){
        return jobInfo -> {
            jobInfo.setAvgSalary(jobInfo.getAvgSalary()/jobInfo.getCount());
            return jobInfo ;
        };
    }

    @Bean
    public JdbcBatchItemWriter<UserInfo> jobAvgSalaryCalcWritter() {
        return new JdbcBatchItemWriterBuilder<UserInfo>()
                .dataSource(dataSource)
                .sql("UPDATE Job SET totalSalary = totalSalary + :totalSalary WHERE jobIdx = :jobIdx")
                .beanMapped()
                .build();
    }




 */

}

