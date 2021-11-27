package com.example.demo.src.recruit;

import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.GetCompanyWithImageRes;
import com.example.demo.src.location.model.GetLocationRes;
import com.example.demo.src.recruit.model.*;
import com.example.demo.src.tag.model.Tag;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.utils.ValidationRegex.isEmpty;

@Service
@Slf4j
@Transactional(readOnly = true)
public class RecruitService {

	private final RecruitDAO recruitDAO;
	private final JwtService jwtService;

	public RecruitService(RecruitDAO recruitDAO, JwtService jwtService) {
		this.recruitDAO = recruitDAO;
		this.jwtService = jwtService;
	}

	public List<GetRecruitsCompany> findRecruitsCompany(Long companyId) throws BaseException {
		try {
			List<Recruit> recruits = recruitDAO.findRecruitAllByCompanyId(companyId);

			return recruits.stream()
				.map(recruit -> new GetRecruitsCompany(recruit.getTitle(), recruit.getReward(),
					recruit.getIsAlways(), recruit.getDeadline()))
				.collect(Collectors.toList());

		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}


	public Recruit findRecruit(Long recruitId) throws BaseException {
		try {
			return recruitDAO.findRecruitById(recruitId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruits() throws BaseException {
		try {
			return recruitDAO.findRecruits();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruits(Long companyId) throws BaseException {
		try {
			return recruitDAO.findRecruits(companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruits(int year, Long companyId) throws BaseException {
		try {
			return recruitDAO.findRecruits(year, companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsJobAll(Long groupId, Long jobId, int year, Long companyId) throws BaseException {
		try {
			return recruitDAO.findRecruitJobAll(groupId, jobId, year, companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsJobAll(Long groupId, Long jobId) throws BaseException {
		try {
			return recruitDAO.findRecruitJobAll(groupId, jobId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsJobAll(Long groupId, Long jobId, Long companyId) throws BaseException {
		try {
			return recruitDAO.findRecruitJobAll(groupId, jobId, companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsGroupAll(Long groupId) throws BaseException {
		try {
			return recruitDAO.findRecruitGroupAll(groupId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsGroupAll(Long groupId, Long companyId) throws BaseException {
		try {
			return recruitDAO.findRecruitGroupAll(groupId, companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsGroupAll(Long groupId, int year, Long companyId) throws BaseException {
		try {
			return recruitDAO.findRecruitGroupAll(groupId, year, companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Recruit> findRecruitsAll(String keyword) throws BaseException {
		try {
			return recruitDAO.findRecruitByTitle(keyword);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Transactional
	public Long updateRecruitStatus(Long recruitId) throws BaseException {
		try {
			return recruitDAO.updateRecruitStatus(recruitId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}



	//(14)
	@Transactional
	public PostApplicationRes createApplicant(PostApplicationReq postApplicationReq) throws BaseException {
		try{
			//recruitIdx로 companyIdx 받아오기
			postApplicationReq.setCompanyIdx(recruitDAO.getCompanyIdx(postApplicationReq.getRecruitIdx()));

			//추천자 존재할 시 userName 가져오기
			if(!isEmpty(postApplicationReq.getRecommenderIdx())){
				postApplicationReq.setRecommenderName(recruitDAO.getUserName(postApplicationReq.getRecommenderIdx()));
			}

			//지원
			int applicationIdx = recruitDAO.createApplicant(postApplicationReq);
			//이력서 등록
			int cvResultNo = recruitDAO.createApplicantCv(applicationIdx, postApplicationReq.getCvIdxes());

			return new PostApplicationRes(postApplicationReq.getUserIdx(),applicationIdx, cvResultNo);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

}
