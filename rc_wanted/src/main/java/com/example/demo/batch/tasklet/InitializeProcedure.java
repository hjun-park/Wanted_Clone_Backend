package com.example.demo.batch.tasklet;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
@NoArgsConstructor
public class InitializeProcedure implements Tasklet {

    private DataSource dataSource;
    private String sql = "UPDATE JobSalary SET count=0, totalSalary=0";

    public InitializeProcedure(DataSource datasource){
        this.dataSource = datasource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public String getSql() {
        return sql;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        //log.info(">>>> step1 requestDateTime = {}", this.value);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        int result = jdbcTemplate.update(sql);
        return RepeatStatus.FINISHED;
    }
}
