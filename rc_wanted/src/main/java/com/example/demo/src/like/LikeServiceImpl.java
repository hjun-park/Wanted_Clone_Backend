package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.company.CompanyService;
import com.example.demo.src.company.model.GetCompanyWithImageRes;
import com.example.demo.src.companyrecruit.CompanyRecruitService;
import com.example.demo.src.like.model.GetLikeUserRes;
import com.example.demo.src.like.model.GetLikesRes;
import com.example.demo.src.like.model.LikeDTO;
import com.example.demo.src.location.LocationService;
import com.example.demo.src.location.model.GetLocationRes;
import com.example.demo.src.profile.ProfileDao;
import com.example.demo.src.profile.model.UserDTO;
import com.example.demo.src.recruit.RecruitService;
import com.example.demo.src.recruit.model.GetRecruitDetailRes;
import com.example.demo.src.recruit.model.Recruit;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

	private final LikeDAO likeDAO;
	private final CompanyService companyService;
	private final RecruitService recruitService;
	private final ProfileDao profileDao;
	private final JwtService jwtService;
	private final LocationService locationService;

	public LikeServiceImpl(LikeDAO likeDAO, CompanyService companyService, RecruitService recruitService, ProfileDao profileDao, JwtService jwtService, LocationService locationService) {
		this.likeDAO = likeDAO;
		this.companyService = companyService;
		this.recruitService = recruitService;
		this.profileDao = profileDao;
		this.jwtService = jwtService;
		this.locationService = locationService;
	}

	@Override
	public List<GetLikesRes> findLikes() throws BaseException {
		try {
			int userId = jwtService.getUserIdx();
			List<LikeDTO> likes = likeDAO.findLikes(userId);

			return likes.stream()
				.map(like -> {
					try {
						Recruit recruit = recruitService.findRecruit(like.getRecruitIdx());
						GetCompanyWithImageRes companyWithImage = companyService.findCompanyWithImage(recruit.getRecruitIdx());
						GetLocationRes location = locationService.findLocation(companyWithImage.getCountryIdx(), companyWithImage.getRegionIdx());
						return new GetLikesRes(companyWithImage.getImage().isEmpty() ? null : companyWithImage.getImage().get(0),
							recruit.getTitle(), companyWithImage.getName(), location.getCountryName(),
							recruit.getReward(), location.getRegionName());
					} catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				})
				.collect(Collectors.toList());


		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}





	@Override
	@Transactional
	public int createLike(Long recruitId) throws BaseException {

		// JWT 토근
		int userId = jwtService.getUserIdx();

		// 좋아요 생성
		if (likeDAO.findLike(userId, recruitId) == null) {
			return likeDAO.insertLike(userId, recruitId);
		} else { // 기존 등록된 좋아요가 있는 경우 에러처리하지 않고 변경 row 0
			return 0;
		}


	}

	@Override
	@Transactional
	public int modifyLikeStatus(Long likeId) throws BaseException {

		// JWT 토큰 검증
		int userId = jwtService.getUserIdx();

		int updatedRow = likeDAO.updateLikeStatus(likeId, userId);

		if (updatedRow == 0) {
			throw new BaseException(INVALID_USER_JWT);
		} else {
			return updatedRow;
		}
	}

	@Override
	public List<GetLikeUserRes> findLikeUsers(Long recruitId) throws BaseException {
		try {
			List<LikeDTO> likes = likeDAO.findLikes(recruitId);

			return likes.stream()
				.map(like -> {
					UserDTO user = profileDao.getUser(like.getUserIdx());
					return new GetLikeUserRes(like.getUserIdx(), user.getProfileImage(),
						user.getName());
				})
				.collect(Collectors.toList());

		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}


	// 값을 +1 하는 방식이 아닌 필요할 때마다 서비스로부터 얻어오는 방법
	@Override
	public int getLikeCount(Long recruitId) throws BaseException {
		try {
			int likeCount = likeDAO.findLikeCount(recruitId);

			if(likeCount == -1) {
				throw new BaseException(RESPONSE_ERROR);
			} else {
				return likeCount;
			}
		} catch(Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
