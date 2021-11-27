package com.example.demo.src.profile;


import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.*;
import org.intellij.lang.annotations.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;

@Repository
public class ProfileDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


	public UserDTO getUser(int userIdx){
		String getUserQuery = "select * from User where userIdx = ? and status = 'used'";
		int getUserParams = userIdx;
		return this.jdbcTemplate.queryForObject(getUserQuery,
			(rs, rowNum) -> UserDTO.builder()
				.userIdx(rs.getLong("userIdx"))
				.email(rs.getString("email"))
				.name(rs.getString("name"))
				.password(rs.getString("password"))
				.isAcceptEmail(rs.getBoolean("isAcceptEmail"))
				.phone(rs.getString("phone"))
				.salary(rs.getInt("salary"))
				.salaryPeriod(rs.getString("salaryPeriod"))
				.salaryCurrency(rs.getString("salaryCurrency"))
				.careerPeriod(rs.getInt("careerPeriod"))
				.searchStatus(rs.getString("searchStatus"))
				.recommenderIdx(rs.getLong("recommenderIdx"))
				.profileImage(rs.getString("profileImage"))
				.profileLevel(rs.getInt("profileLevel"))
				.purpose(rs.getString("purpose"))
				.phoneCountryCode(rs.getString("phoneCountryCode"))
				.build()
			,getUserParams);
	}


    //(2)
    public int createProfile(PostProfileReq postUserReq){
        String createProfileQuery = "insert into User (name, email, password, phone, phoneCountryCode, isAcceptEmail) VALUES (?,?,?,?,?,?)";
        Object[] createProfileParams = new Object[]{postUserReq.getName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getPhone(), postUserReq.getPhoneCountryCode(), postUserReq.getIsAcceptEmail()};
        this.jdbcTemplate.update(createProfileQuery, createProfileParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //(3)
    public int createUserGroup(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String createUserGroupQuery = "insert into UserGroups (userIdx, jobGroupIdx) VALUES (?,?)";
        Object[] createUserGroupParams = new Object[]{postProfileSpecialtyReq.getUserIdx(), postProfileSpecialtyReq.getJobGroupIdx()};
        this.jdbcTemplate.update(createUserGroupQuery, createUserGroupParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //(3)
    public int createUserJob(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String createUserJobQuery = "insert into UserJob (userIdx, jobIdx) VALUES (?,?)";

        int userIdx = postProfileSpecialtyReq.getUserIdx();
        int result = 0;

        int[] jobIdxes= postProfileSpecialtyReq.getJobIdxes();
        for(int jobIdx : jobIdxes){
            Object[] createUserJobParams = new Object[]{userIdx, jobIdx};
            this.jdbcTemplate.update(createUserJobQuery, createUserJobParams);
            result += 1;
        }
        return result;
    }

    //(3)
    public int createUserSkill(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String createUserSkillQuery = "insert into UserSkill (userIdx, skillIdx) VALUES (?,?)";
        int userIdx = postProfileSpecialtyReq.getUserIdx();
        int result = 0;

        int[] skillIdxes= postProfileSpecialtyReq.getSkillIdxes();
        for(int skillIdx : skillIdxes){
            Object[] createUserSkillParams = new Object[]{userIdx, skillIdx};
            this.jdbcTemplate.update(createUserSkillQuery, createUserSkillParams);
            result += 1;
        }
        return result;
    }

    //(3)
    public void deleteUserGroup(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String deleteUserGroupQuery = "delete from UserGroups where userIdx = ?";
        int deleteUserGroupParams = postProfileSpecialtyReq.getUserIdx();
        this.jdbcTemplate.update(deleteUserGroupQuery, deleteUserGroupParams);
    }

    //(3)
    public void deleteUserJob(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String deleteUserJobQuery = "delete from UserJob where userIdx = ?";
        int deleteUserJobParams = postProfileSpecialtyReq.getUserIdx();
        this.jdbcTemplate.update(deleteUserJobQuery, deleteUserJobParams);
    }

    // (5)
    public void deleteUserInterests(PostProfileInterestReq postProfileInterestReq){
        String deleteUserInterestsQuery = "delete from UserKeyword where userIdx = ?";
        int deleteUserInterestsParams = postProfileInterestReq.getUserIdx();
        this.jdbcTemplate.update(deleteUserInterestsQuery, deleteUserInterestsParams);
    }

    //(3)
    public void deleteUserSkill(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String deleteUserSkillQuery = "delete from UserSkill where userIdx = ?";
        int deleteUserSkillParams = postProfileSpecialtyReq.getUserIdx();
        this.jdbcTemplate.update(deleteUserSkillQuery, deleteUserSkillParams);
    }

    // (5)
    public int createUserKeyword(PostProfileInterestReq postProfileInterestReq){
        String createUserKeywordQuery = "insert into UserKeyword (userIdx, keywordIdx) VALUES (?,?)";

        int userIdx = postProfileInterestReq.getUserIdx();
        int result = 0;

        int[] keywords = postProfileInterestReq.getInterestIdxes();

        for(int keyword : keywords){
            Object[] createUserKeywordParams = new Object[]{userIdx, keyword};
            this.jdbcTemplate.update(createUserKeywordQuery, createUserKeywordParams);
            result += 1;
        }

        return result;
    }

    //(4)
    public int createCv(Cv cv){
        String createCvQuery = "insert into Cv (title, name, introduction, email, phone, userIdx, isMain) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object[] createCvParams = new Object[] {cv.getTitle(), cv.getName(), cv.getIntroduction(), cv.getEmail(), cv.getPhone(), cv.getUserIdx(), cv.getIsMain()};
        this.jdbcTemplate.update(createCvQuery, createCvParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //(4)
    public int createCvCareer(PostProfileCareerReq postProfileCareerReq, int cvIdx){
        String createCvCareerQuery = "insert into CvCareer (company, cvIdx, displayOrder,startDate,endDate) VALUES (?, ?, ?, ? ,?)";
        Object[] createCvCareerParams = new Object[] {postProfileCareerReq.getCompany(), cvIdx, 1, postProfileCareerReq.getStartDate(), postProfileCareerReq.getEndDate()};
        this.jdbcTemplate.update(createCvCareerQuery, createCvCareerParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //(4)
    public int createCvEducation(PostProfileCareerReq postProfileCareerReq, int cvIdx){
        String createCvCareerQuery = "insert into CvEducation (school, cvIdx, displayOrder) VALUES (?, ?, ?)";
        Object[] createCvCareerParams = new Object[] {postProfileCareerReq.getSchool(), cvIdx, 1};
        this.jdbcTemplate.update(createCvCareerQuery, createCvCareerParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //(1)
    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    //(1)
    public String checkUser(String email) {
        String checkUserQuery = "select status from User where email = ?";
        String checkUserParams = email;
        return this.jdbcTemplate.queryForObject(checkUserQuery,
                String.class,
                checkUserParams);
    }

    // (5)
    public int updateUserPurpose(PostProfileInterestReq postProfileInterestReq){
        String updateUserPurposeQuery = "update User set purpose = ? where userIdx = ? ";
        Object[] updateUserPurposeParams = new Object[]{postProfileInterestReq.getPurpose(), postProfileInterestReq.getUserIdx()};
        this.jdbcTemplate.update(updateUserPurposeQuery,updateUserPurposeParams);

        return postProfileInterestReq.getUserIdx();
    }

    //(3)
    public int updateUserCareerPeriod(PostProfileSpecialtyReq postProfileSpecialtyReq){
        String updateUserCareerPeriodQuery = "update User set careerPeriod = ? where userIdx = ? ";
        Object[] updateUserCareerPeriodParams = new Object[] {postProfileSpecialtyReq.getCareerPeriod(), postProfileSpecialtyReq.getUserIdx()};
        this.jdbcTemplate.update(updateUserCareerPeriodQuery,updateUserCareerPeriodParams);

        return postProfileSpecialtyReq.getUserIdx();
    }

    //(3)
    public void updateUserSalary(PostProfileSpecialtyReq postProfileSpecialtyReq){

        String updateUserSalaryQuery = "update User set salary = ?, salaryPeriod = ?, salaryCurrency = ? where userIdx = ? ";
        Object[] updateUserSalaryParams = new Object[] {postProfileSpecialtyReq.getSalary().getSalaryValue(), postProfileSpecialtyReq.getSalary().getSalaryPeriod(), postProfileSpecialtyReq.getSalary().getSalaryCurrency(),postProfileSpecialtyReq.getUserIdx()};

        this.jdbcTemplate.update(updateUserSalaryQuery,updateUserSalaryParams);
    }

    //(4)
    public Cv getUserCvInfo(int userIdx){
        String getUserCvInfoQuery = "select userIdx, name, email, phone from User where userIdx = ?";
        int getUserCvInfoParams = userIdx;

        return this.jdbcTemplate.queryForObject(getUserCvInfoQuery,
                (rs,rowNum)-> new Cv(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ),
                getUserCvInfoParams
        );
    }



    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, password from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("password")),
                getPwdParams
        );

    }

    public int getUserCareerPeriod(int userIdx) throws BaseException {
        String getUserCareerPeriodQuery = "select careerPeriod from User where userIdx = ?";
        int getUserCareerPeriodParams = userIdx;
        try {
            return this.jdbcTemplate.queryForObject(getUserCareerPeriodQuery,
                    int.class,
                    getUserCareerPeriodParams);
        } catch (Exception e){
            throw new BaseException(RESPONSE_EMPTY);
        }
    }

    public int getUserJobIdx(int userIdx){
        String getJobNameQuery = "select jobIdx from UserJob where (userIdx = ?) & (status = \"used\") group by userIdx;";
        int getJobNameParams = userIdx;
        return this.jdbcTemplate.queryForObject(getJobNameQuery,
                int.class,
                getJobNameParams);
    }

    public String getJobName(int jobIdx){
        String getJobNameQuery = "select title from Job where jobIdx = ?";
        int getJobNameParams = jobIdx;
        return this.jdbcTemplate.queryForObject(getJobNameQuery,
                String.class,
                getJobNameParams);
    }

    // (21)
    public GetUserRes getUserNecessary(int userIdx){
        String getUserQuery = "Select userIdx, profileImage, name, email, phone, purpose From User Where userIdx = ?";
        int getUserParams = userIdx;

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum)-> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("profileImage"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("purpose")
                ),
                getUserParams
        );



    }


    // (21) ISSUE 3
    public Optional<String> getInterest(int userIdx) {
        String getInterestQuery = "Select title From Keyword where keywordIdx = " +
                "(select keywordIdx from UserKeyword where userKeywordIdx = " +
                "    (Select max(userKeywordIdx) From UserKeyword where userIdx = ? group by userIdx))";
        int getInterestParams = userIdx;

        List<String> result = jdbcTemplate.query(
                getInterestQuery,
                (rs,rowNum)-> (rs.getString("title")),
                getInterestParams);

        return Optional.ofNullable(result.isEmpty() ? "없음" : result.get(0));

    }

    public void setUserProfileStatus(int level, int userIdx){
        String setUserProfileStatusQuery = "update User set profileLevel = ? where userIdx = ?";
        Object[] setUserProfileStatusParams = {level, userIdx};
        this.jdbcTemplate.update(setUserProfileStatusQuery, setUserProfileStatusParams);
    }

    public int modifyUser(PutUserReq putUserReq){
        String modifyUserQuery = "update User set email = ?, name = ?, phone = ?, phoneCountryCode = ? where userIdx = ?";
        Object[] modifyUserParams = {putUserReq.getEmail(),putUserReq.getName(), putUserReq.getPhone(), putUserReq.getPhoneCountryCode(), putUserReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyUserQuery, modifyUserParams);
    }

    public jobs getJobGroups(int userIdx) throws BaseException {
        String getJobGroupsQuery = "Select UG.jobGroupIdx as jobsIdx, userGroupIdx as jobsUserIdx, title " +
                "from JobGroups INNER JOIN UserGroups UG on JobGroups.jobGroupIdx = UG.jobGroupIdx " +
                "Where userIdx = ?";
        try {
            return this.jdbcTemplate.queryForObject(getJobGroupsQuery,
                    (rs, rowNum) -> new jobs(
                            rs.getInt("jobsIdx"),
                            rs.getInt("jobsUserIdx"),
                            rs.getString("title")
                    ),
                    userIdx);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_JOBS);
        }
    }

    public List<jobs> getJobsList(int userIdx){
        String getJobsListQuery = "Select UJ.jobIdx as jobsIdx, userJobIdx as jobsUserIdx, title " +
                "from Job INNER JOIN UserJob UJ on Job.jobIdx = UJ.jobIdx " +
                "Where userIdx = ?";
        return this.jdbcTemplate.query(getJobsListQuery,
                (rs, rowNum) -> new jobs(
                        rs.getInt("jobsIdx"),
                        rs.getInt("jobsUserIdx"),
                        rs.getString("title")
                ),
                userIdx);
    }

    public Salary getUserSalary(int userIdx) throws BaseException {
        String getUserSalaryQuery = "Select salaryPeriod, salaryCurrency, salary as salaryValue From User Where userIdx = ?";
        try {
            return this.jdbcTemplate.queryForObject(getUserSalaryQuery,
                    (rs, rowNum) -> new Salary(
                            rs.getString("salaryPeriod"),
                            rs.getString("salaryCurrency"),
                            rs.getInt("salaryValue")
                    ),
                    userIdx);
        } catch (Exception e){
            throw new BaseException(RESPONSE_EMPTY);
        }
    }

    public List<Skill> getUserSkillList(int userIdx){
        String getUserSkillListQuery = "Select UserSkill.skillIdx as skillIdx, title " +
                "from UserSkill INNER JOIN Skill S on UserSkill.skillIdx = S.skillIdx " +
                "Where userIdx = ?";
        return this.jdbcTemplate.query(getUserSkillListQuery,
                (rs, rowNum) -> new Skill(
                        rs.getInt("skillIdx"),
                        rs.getString("title")
                ),
                userIdx);
    }

    public GetProfileCareerRes getCvInfo(int userIdx) throws BaseException {
        String GetProfileCareerResQuery = "Select Cv.cvIdx as cvIdx, title, introduction, school, major, company, position\n" +
                "from Cv\n" +
                "    INNER JOIN CvEducation CE on Cv.cvIdx = CE.cvIdx\n" +
                "    INNER JOIN CvCareer CC on Cv.cvIdx = CC.cvIdx\n" +
                "WHERE userIdx = ? && CE.displayOrder = 1 && CC.displayOrder = 1;";
        try {
            return this.jdbcTemplate.queryForObject(GetProfileCareerResQuery,
                    (rs, rowNum) -> new GetProfileCareerRes(
                            rs.getInt("cvIdx"),
                            rs.getString("title"),
                            rs.getString("introduction"),
                            rs.getString("school"),
                            rs.getString("major"),
                            rs.getString("company"),
                            rs.getString("position")
                    ),
                    userIdx);
        } catch (Exception e){
            throw new BaseException(RESPONSE_ERROR_CV);
        }

    }

    public boolean checkUserStatus(int userIdx) throws BaseException {
        String checkUserStatusQuery = "SELECT status FROM User WHERE userIdx = ?";
        try {
            if ((this.jdbcTemplate.queryForObject(checkUserStatusQuery, String.class, userIdx)).equals("used")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new BaseException(RESPONSE_ERROR_USER);
        }
    }

    public int checkUserProfileLevel(int userIdx) throws BaseException {
        String checkUserProfileLevelQuery = "SELECT profileLevel FROM User WHERE userIdx = ?";

        return this.jdbcTemplate.queryForObject(checkUserProfileLevelQuery,int.class,userIdx);

    }

	public UserDTO getUser(String email){
		String getUserQuery = "select * from User where email = ? and status = 'used'";
		String getUserParams = email;

		try {
			return this.jdbcTemplate.queryForObject(getUserQuery,
				(rs, rowNum) -> UserDTO.builder()
					.userIdx(rs.getLong("userIdx"))
					.email(rs.getString("email"))
					.name(rs.getString("name"))
					.password(rs.getString("password"))
					.isAcceptEmail(rs.getBoolean("isAcceptEmail"))
					.phone(rs.getString("phone"))
					.salary(rs.getInt("salary"))
					.salaryPeriod(rs.getString("salaryPeriod"))
					.salaryCurrency(rs.getString("salaryCurrency"))
					.careerPeriod(rs.getInt("careerPeriod"))
					.searchStatus(rs.getString("searchStatus"))
					.recommenderIdx(rs.getLong("recommenderIdx"))
					.profileImage(rs.getString("profileImage"))
					.profileLevel(rs.getInt("profileLevel"))
					.purpose(rs.getString("purpose"))
					.phoneCountryCode(rs.getString("phoneCountryCode"))
					.build()
				, getUserParams);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}





}
