package com.example.demo.src.skill;



import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.cv.CvProvider;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isEmpty;

// Service Create, Update, Delete 의 로직 처리
@Service
public class SkillService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SkillDao skillDao;
    private final SkillProvider skillProvider;
    private final CvProvider cvProvider;
    private final JwtService jwtService;


    @Autowired
    public SkillService(SkillDao skillDao, SkillProvider skillProvider, CvProvider cvProvider, JwtService jwtService) {
        this.skillDao = skillDao;
        this.skillProvider = skillProvider;
        this.cvProvider = cvProvider;
        this.jwtService = jwtService;

    }


}
