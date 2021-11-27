package com.example.demo.src.cv;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.TableInfo;
import com.example.demo.src.cv.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isEmpty;

@Repository
public class CvDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // (15)
    public List<Cv> getCvs(int userIdx) {
        String getCvsQuery = "select cvIdx, title, date_format(updatedAt,'%Y.%m.%d') as updateDate, if(isMain,\"원티드 이력서\",null) as isMain, if(isUploaded,\"첨부 완료\",null) as isUploaded, if(isCompleted,\"작성 완료\",\"작성중\") as isCompleted from Cv where userIdx = ? && status = \"used\" Order By isMain DESC, updatedAt DESC";
        int getCvsParams = userIdx;
        return this.jdbcTemplate.query(getCvsQuery,
                (rs, rowNum) -> new Cv(
                        rs.getInt("cvIdx"),
                        rs.getString("title"),
                        rs.getString("updateDate"),
                        rs.getString("isMain"),
                        rs.getString("isUploaded"),
                        rs.getString("isCompleted")),
                getCvsParams);
    }

    // (16)
    public PostInitialCvReq getUserCvInfo(int userIdx) throws BaseException{
        String getUserCvInfoQuery = "select userIdx, name, email, phone from User where userIdx = ?";
        int getUserCvInfoParams = userIdx;
        try {
            return this.jdbcTemplate.queryForObject(getUserCvInfoQuery,
                    (rs, rowNum) -> new PostInitialCvReq(
                            rs.getInt("userIdx"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone")),
                    getUserCvInfoParams);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_USER);
        }
    }

    // (4), (16)
    public List<String> getUserCvTitles(int userIdx) {
        String getUserCvTitlesQuery = "select title from Cv where userIdx = ?";
        int getUserCvTitlesParams = userIdx;

        return this.jdbcTemplate.query(getUserCvTitlesQuery,
                (rs, rowNum) -> new String(
                        rs.getString("title")),
                getUserCvTitlesParams);
    }

    // (16)
    public int getUserCareerPeriod(int userIdx) throws BaseException {
        String getUserCareerPeriodQuery = "select careerPeriod from User where userIdx = ?";
        int getUserCareerPeriodParams = userIdx;

        try {
            return this.jdbcTemplate.queryForObject(getUserCareerPeriodQuery,
                    int.class,
                    getUserCareerPeriodParams);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_USER);
        }
    }

    // (16)
    public String getJobName(int userIdx) throws BaseException {
        String getJobNameQuery = "SELECT title FROM Job WHERE jobIdx = (SELECT min(jobIdx) FROM UserJob WHERE (userIdx = ?) & (status = \"used\"))";
        int getJobNameParams = userIdx;

        try {
            return this.jdbcTemplate.queryForObject(getJobNameQuery,
                    String.class,
                    getJobNameParams);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_JOBS);
        }
    }

    // (16)
    public int createInitialCv(PostInitialCvReq postCvReq) throws BaseException{
        String createInitialCvQuery = "insert into Cv (title, introduction, name, email, phone, userIdx) VALUES (?,?,?,?,?,?)";
        Object[] createInitialCvParams = {postCvReq.getTitle(), postCvReq.getIntroduction(), postCvReq.getName(), postCvReq.getEmail(), postCvReq.getPhone(), postCvReq.getUserIdx()};
        try{
            this.jdbcTemplate.update(createInitialCvQuery, createInitialCvParams);
            String lastInserIdQuery = "select last_insert_id()";
            return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_MAKE_CV);
        }
    }

    // (17)
    public Cv getCv(int cvIdx) throws BaseException{
        String getCvQuery = "SELECT title, name, email, phone, introduction FROM Cv WHERE cvIdx = ? && status = \"used\"";
        try {
            return this.jdbcTemplate.queryForObject(getCvQuery,
                    (rs, rowNum) -> new Cv(
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("introduction")),
                    cvIdx);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_CV);
        }
    }

    // (39) ~ (45)
    public int createInitialCvDetail(TableInfo table, int cvIdx) throws BaseException {
        String tableName = table.getName();
        String keyName = table.getParentKey();
        String createCvDetailQuery = "INSERT into " + tableName + " (" + keyName + ") VALUES (?)";
        if(this.jdbcTemplate.update(createCvDetailQuery, cvIdx)==0){
            throw new BaseException(CREATE_ERROR_CVDETAIL);
        };

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // (46) ~ (52)
    public void deleteCvDetail(TableInfo table, int idx) throws BaseException {
        String tableName = table.getName();
        String primaryKey = table.getPrimarKey();
        String deleteCvDetailQuery = "Delete From " + tableName + " WHERE " + primaryKey + " = ?";

        if(this.jdbcTemplate.update(deleteCvDetailQuery, idx) == 0){
            throw new BaseException(DELETE_FAIL_CVDETAIL);
        }
    }

    // (18)
    public int updateCvCareer(Career career, int cvIdx) throws BaseException{
        String updateCvCareerQuery = "UPDATE CvCareer SET startDate = ?, endDate = ?, company = ?, position = ?, cvIdx = ?, displayOrder = ? WHERE cvCareerIdx = ?";
        Object[] updateCvCareerParams = {career.getStartDate(), career.getEndDate(), career.getCompany(), career.getPosition(), cvIdx, career.getDisplayOrder(), career.getCvCareerIdx()};

        int result = this.jdbcTemplate.update(updateCvCareerQuery, updateCvCareerParams);
        if(result == 0){
            throw new BaseException(UPDATE_FAIL_CVCAREER);
        }
        return career.getCvCareerIdx();
    }

    // (18)
    public void updateCvCareerAccomp(Accomp accomp) throws BaseException{
        String updateCvCareerAccompQuery = "UPDATE CvCareerAccomp SET startDate = ?, endDate = ?, title = ?, intro = ?, cvCareerIdx = ?, displayOrder = ? WHERE CvCareerAccompIdx = ?";
        Object[] updateCvCareerAccompParams = {accomp.getStartDate(), accomp.getEndDate(), accomp.getTitle(), accomp.getIntro(), accomp.getCvCareerIdx(), accomp.getDisplayOrder(), accomp.getCvCareerAccompIdx()};

        if(this.jdbcTemplate.update(updateCvCareerAccompQuery, updateCvCareerAccompParams) == 0){
            throw new BaseException(UPDATE_FAIL_CVCAREERACCOMP);
        }
    }

    // (18)
    public void updateCvInfo(Cv cv, int cvIdx) throws BaseException{
        String updateCvInfoQuery = "UPDATE Cv SET title = ?, name = ?, email = ?, phone = ?, introduction = ? WHERE cvIdx = ?";
        Object[] updateCvInfoParams = {cv.getTitle(), cv.getName(), cv.getEmail(), cv.getPhone(), cv.getIntroduction(), cvIdx};

        if(this.jdbcTemplate.update(updateCvInfoQuery, updateCvInfoParams) == 0){
            throw new BaseException(UPDATE_FAIL_CVINFO);
        }
    }

    // (18)
    public void updateCvEducation(List<Education> educationList, int cvIdx) throws BaseException {
        String updateCvEducationQuery = "UPDATE CvEducation SET startDate = ?, endDate = ?, school = ?, major = ?, content = ?, cvIdx = ?, displayOrder = ? WHERE cvEducationIdx = ?";

        for (Education education : educationList) {
            Object[] updateCvEducationParams = {education.getStartDate(), education.getEndDate(), education.getSchool(), education.getMajor(), education.getContent(), cvIdx, education.getDisplayOrder(), education.getCvEducationIdx()};
            if(this.jdbcTemplate.update(updateCvEducationQuery, updateCvEducationParams) == 0){
                throw new BaseException(UPDATE_FAIL_CVEDUCATION);
            }
        }
    }

    // (18)
    public void updateCvAdditional(List<Additional> additionalList, int cvIdx) throws BaseException {
        String updateCvAdditionalQuery = "UPDATE CvAdditional SET date = ?, cvIdx = ?, title = ?, content = ?, displayOrder = ? WHERE cvAdditionalIdx = ?";

        for (Additional additional : additionalList) {
            Object[] updateCvAdditionalParams = {additional.getDate(), cvIdx, additional.getTitle(), additional.getContent(), additional.getDisplayOrder(),additional.getCvAdditionalIdx()};

            if(this.jdbcTemplate.update(updateCvAdditionalQuery, updateCvAdditionalParams) == 0){
                throw new BaseException(UPDATE_FAIL_CVADDITIONAL);
            }
        }



    }

    // (18)
    public int updateCvLanguage(Language language, int cvIdx) throws BaseException{
        String updateCvLanguageQuery = "UPDATE CvLanguage SET cvIdx = ?, category = ?, level = ?, displayOrder = ? WHERE cvLanguageIdx = ?";
        Object[] updateCvLanguageParams = {cvIdx, language.getCategory(), language.getLevel(), language.getDisplayOrder(), language.getCvLanguageIdx()};

        if(this.jdbcTemplate.update(updateCvLanguageQuery, updateCvLanguageParams) == 0){
            throw new BaseException(UPDATE_FAIL_CVLANGUAGE);
        }
        return language.getCvLanguageIdx();
    }

    // (18)
    public void updateCvLanguageTest(LanguageTest languageTest) throws BaseException{
        String updateCvLanguageTestQuery = "UPDATE CvCareerLanguageTest SET cvLanguageIdx = ?, title = ?, score = ?, date = ?, displayOrder = ? WHERE CvLanguageTestIdx = ?";
        Object[] updateCvLanguageTestParams = {languageTest.getCvLanguageIdx(), languageTest.getTitle(), languageTest.getScore(), languageTest.getDate(), languageTest.getDisplayOrder(), languageTest.getCvLanguageIdx()};

        if(this.jdbcTemplate.update(updateCvLanguageTestQuery, updateCvLanguageTestParams) == 0){
            throw new BaseException(UPDATE_FAIL_CVLANGUAGETEST);
        }
    }

    // (18)
    public void createCvSkill(List<Skill> skillList, int cvIdx) throws BaseException {
        String createCvSkillQuery = "insert into CvSkill (cvIdx, skillIdx) VALUES (?,?)";
        this.resetCvSkill(cvIdx);

        for (Skill skill : skillList) {
            Object[] createCvSkillParams = {cvIdx, skill.getSkillIdx()};
            if(this.jdbcTemplate.update(createCvSkillQuery, createCvSkillParams)==0){
                throw new BaseException(UPDATE_FAIL_CVSKILL);
            };
        }
    }

    public void resetCvSkill(int cvIdx) throws BaseException {
        String resetCvSkillQuery = "DELETE FROM CvSkill WHERE cvIdx = ?";
        this.jdbcTemplate.update(resetCvSkillQuery, cvIdx);
    }

    //(18)
    public void updateCvLink(List<Link> linkList, int cvIdx) throws BaseException{
        String updateCvLinkQuery = "UPDATE CvLink SET cvIdx = ?, link = ?, displayOrder = ? WHERE cvLinkIdx = ?";

        for (Link link : linkList) {
            Object[] updateCvLinkParams = {cvIdx, link.getLink(), link.getDisplayOrder(), link.getCvLinkIdx()};
            if(this.jdbcTemplate.update(updateCvLinkQuery, updateCvLinkParams) == 0){
                throw new BaseException(UPDATE_FAIL_CVLINK);
            }
        }
    }

    // (17)
    public List<Career> getCvCareer(int cvIdx) {
        String getCvCareerQuery = "select cvCareerIdx, startDate, endDate, company, position,displayOrder from CvCareer where cvIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getCvCareerQuery,
                (rs, rowNum) -> new Career(
                        rs.getInt("cvCareerIdx"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("company"),
                        rs.getString("position"),
                        rs.getInt("displayOrder")),
                cvIdx);
    }

    // (17)
    public List<Accomp> getCvCareerAccomp(int cvCareerIdx) {
        String getCvCareerAccompQuery = "select cvCareerIdx, cvCareerAccompIdx, startDate, endDate, title, intro, displayOrder from CvCareerAccomp where cvCareerIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getCvCareerAccompQuery,
                (rs, rowNum) -> new Accomp(
                        rs.getInt("cvCareerIdx"),
                        rs.getInt("cvCareerAccompIdx"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("title"),
                        rs.getString("intro"),
                        rs.getInt("displayOrder")),
                cvCareerIdx);
    }

    // (17)
    public List<Education> getCvEducation(int cvIdx) {
        String getEducationQuery = "select cvEducationIdx, startDate, endDate, school, major, content, displayOrder from CvEducation where cvIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getEducationQuery,
                (rs, rowNum) -> new Education(
                        rs.getInt("cvEducationIdx"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("school"),
                        rs.getString("major"),
                        rs.getString("content"),
                        rs.getInt("displayOrder")),
                cvIdx);
    }

    // (17)
    public List<Skill> getCvSkill(int cvIdx) {
        String getCvSkillQuery = "select CvSkill.skillIdx, title from CvSkill\n" +
                                    "INNER JOIN Skill S on CvSkill.skillIdx = S.skillIdx\n" +
                                    "where cvIdx = ? Order By CvSkill.createdAt";
        return this.jdbcTemplate.query(getCvSkillQuery,
                (rs, rowNum) -> new Skill(
                        rs.getInt("skillIdx"),
                        rs.getString("title")),
                cvIdx);
    }

    // (17)
    public List<Additional> getCvAdditional(int cvIdx) {
        String getCvAdditionalQuery = "select cvAdditionalIdx, date, title, content, displayOrder from CvAdditional where cvIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getCvAdditionalQuery,
                (rs, rowNum) -> new Additional(
                        rs.getInt("cvAdditionalIdx"),
                        rs.getString("date"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("displayOrder")),
                cvIdx);
    }

    // (17)
    public List<Language> getCvLanguage(int cvIdx) {
        String getCvLanguageQuery = "select cvLanguageIdx, category, level, displayOrder from CvLanguage where cvIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getCvLanguageQuery,
                (rs, rowNum) -> new Language(
                        rs.getInt("cvLanguageIdx"),
                        rs.getString("category"),
                        rs.getString("level"),
                        rs.getInt("displayOrder")),
                cvIdx);
    }

    // (17)
    public List<LanguageTest> getCvLanguageTest(int cvLanguageIdx) {
        String getCvCareerAccompQuery = "select cvLanguageTestIdx, title, score, date, displayOrder from CvLanguageTest where cvLanguageIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getCvCareerAccompQuery,
                (rs, rowNum) -> new LanguageTest(
                        rs.getInt("cvLanguageTestIdx"),
                        rs.getString("title"),
                        rs.getString("score"),
                        rs.getString("date"),
                        rs.getInt("displayOrder")),
                cvLanguageIdx);
    }

    // (17)
    public List<Link> getCvLink(int cvIdx) {
        String getCvLinkQuery = "select cvLinkIdx, link, displayOrder from CvLink where cvIdx = ? Order By displayOrder";
        return this.jdbcTemplate.query(getCvLinkQuery,
                (rs, rowNum) -> new Link(
                        rs.getInt("cvLinkIdx"),
                        rs.getString("link"),
                        rs.getInt("displayOrder")),
                cvIdx);
    }

    // (19)
    public int modifyCvStatus(int cvIdx) {
        String modifyCvStatusQuery = "update Cv set status = \"unused\" where cvIdx = ? ";
        int modifyCvStatusParams = cvIdx;
        return this.jdbcTemplate.update(modifyCvStatusQuery, modifyCvStatusParams);

    }

    // (19)
    public int modifyCvMain(int cvIdx) {
        String modifyCvMainQuery = "update Cv set IsMain = 1 where cvIdx = ? ";
        int modifyCvMainParams = cvIdx;
        return this.jdbcTemplate.update(modifyCvMainQuery, modifyCvMainParams);

    }

    // (19)
    public int modifyOtherCvMain(int userIdx) {
        String modifyOtherCvMainQuery = "update Cv set IsMain = 0 where userIdx = ? ";
        int modifyOtherCvMainParams = userIdx;
        return this.jdbcTemplate.update(modifyOtherCvMainQuery, modifyOtherCvMainParams);

    }

    // (19)
    public void deleteAllCvDetail (int cvIdx){
        List<String> cvTableList = new ArrayList<>(Arrays.asList("CvAdditional", "CvCareer", "CvEducation", "CvLanguage", "CvLink", "CvSkill"));

        for (String table : cvTableList) {
            String deleteAllCvDetailQuery = "DELETE FROM " + table + " WHERE cvIdx = ?";
            this.jdbcTemplate.update(deleteAllCvDetailQuery, cvIdx);
        }
    }

    // (19)
    public int isMainCvExist(int userIdx) {
        String isMainCvExistQuery = "Select exists (Select cvIdx From Cv Where userIdx = ? && status = \"used\" && isMain = true);";
        return this.jdbcTemplate.queryForObject(isMainCvExistQuery, int.class, userIdx);
    }

    //// (17), (18), (19)
    public int checkUser(int cvIdx) {
        String checkUserQuery = "SELECT userIdx FROM Cv WHERE cvIdx = ?";
        return this.jdbcTemplate.queryForObject(checkUserQuery, int.class, cvIdx);

    }

    public boolean checkCvStatus(int cvIdx) throws BaseException {
        String checkCvStatusQuery = "SELECT status FROM Cv WHERE cvIdx = ?";
        try {
            if ((this.jdbcTemplate.queryForObject(checkCvStatusQuery, String.class, cvIdx)).equals("used")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new BaseException(RESPONSE_ERROR_CV);
        }
    }


}
