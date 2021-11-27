package com.example.demo.src.skill;


import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;

@Repository
public class SkillDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Skill> getSkill (String searchKeyword) {
        String getSkillQuery = "SELECT skillIdx, title FROM Skill WHERE title LIKE ?";
        String getSkillParams = searchKeyword + "%";
        return this.jdbcTemplate.query(getSkillQuery,
                    (rs, rowNum) -> new Skill(
                            rs.getInt("skillIdx"),
                            rs.getString("title")),
                    getSkillParams);

    }

}
