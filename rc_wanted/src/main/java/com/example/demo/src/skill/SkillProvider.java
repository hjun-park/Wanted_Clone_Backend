package com.example.demo.src.skill;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class SkillProvider {

    private final SkillDao skillDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SkillProvider(SkillDao skillDao, JwtService jwtService) {
        this.skillDao = skillDao;
        this.jwtService = jwtService;
    }

    public List<Skill> searchSkill(String searchKeyword) throws BaseException{
        try{
            List<Skill> skillList = skillDao.getSkill(searchKeyword);
            return skillList;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
