package com.example.demo.src.skill;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/skills")
public class SkillController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SkillProvider skillProvider;
    @Autowired
    private final SkillService skillService;
    @Autowired
    private final JwtService jwtService;

    public SkillController(SkillProvider skillProvider, SkillService skillService, JwtService jwtService){
        this.skillProvider = skillProvider;
        this.skillService = skillService;
        this.jwtService = jwtService;
    }

    //(20) [GET] /skills?skill=skill - 스킬 검색
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<Skill>> searchSkill(@RequestParam String skill){
        try{
            String searchKeyword = skill.toUpperCase(Locale.ROOT);
            return new BaseResponse<> (skillProvider.searchSkill(searchKeyword));
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }





}
