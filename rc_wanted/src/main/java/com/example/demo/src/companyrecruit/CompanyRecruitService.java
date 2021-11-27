package com.example.demo.src.companyrecruit;

import com.example.demo.config.BaseException;
import com.example.demo.src.company.CompanyService;
import com.example.demo.src.company.model.GetCompanyWithImageRes;
import com.example.demo.src.company.model.TagCompany;
import com.example.demo.src.country.CountryService;
import com.example.demo.src.like.LikeService;
import com.example.demo.src.location.LocationService;
import com.example.demo.src.location.model.GetLocationIdRes;
import com.example.demo.src.location.model.GetLocationRes;
import com.example.demo.src.recruit.RecruitService;
import com.example.demo.src.recruit.model.GetRecruitDetailRes;
import com.example.demo.src.recruit.model.GetRecruitsReq;
import com.example.demo.src.recruit.model.GetRecruitsRes;
import com.example.demo.src.recruit.model.Recruit;
import com.example.demo.src.tag.TagService;
import com.example.demo.src.tag.model.Tag;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CompanyRecruitService {

	private final CompanyService companyService;
	private final RecruitService recruitService;
	private final LocationService locationService;
	private final CountryService countryService;
	private final LikeService likeService;
	private final TagService tagService;
	private final JwtService jwtService;

	public CompanyRecruitService(CompanyService companyService, RecruitService recruitService, LocationService locationService, CountryService countryService, LikeService likeService, TagService tagService, JwtService jwtService) {
		this.companyService = companyService;
		this.recruitService = recruitService;
		this.locationService = locationService;
		this.countryService = countryService;
		this.likeService = likeService;
		this.tagService = tagService;
		this.jwtService = jwtService;
	}

	public GetRecruitDetailRes findRecruitDetail(Long recruitId) throws BaseException {
		try {
			Recruit recruit = recruitService.findRecruit(recruitId);
			GetCompanyWithImageRes company = companyService.findCompanyWithImage(recruit.getCompanyIdx());
			GetLocationRes location = locationService.findLocation(company.getCountryIdx(), company.getRegionIdx());
			List<Tag> tags = tagService.findTags(recruit.getCompanyIdx());

			return new GetRecruitDetailRes(
				recruit.getRecruitIdx(), company.getImage(), recruit.getTitle(),
				recruit.getContent(), company.getResponseRate(), location.getRegionName(),
				location.getCountryName(), tags.stream().map(Tag::getName).collect(Collectors.toList()),
				company.getName(), recruit.getDeadline(), company.getAddress(),
				recruit.getReward(), company.getLogo(), company.getLatitude(), company.getLongitude());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<GetRecruitsRes> findRecruits(GetRecruitsReq getRecruitsReq) throws BaseException {
		try {
			Long tagId = getRecruitsReq.getTagId();
			Integer year = getRecruitsReq.getYear();
			String regionName = getRecruitsReq.getLocation();
			String countryName = getRecruitsReq.getCountry();

			List<GetCompanyWithImageRes> companyList = new ArrayList<>();


			if (tagId == null && year == null && regionName == null && countryName == null) {
				List<Recruit> recruits = recruitService.findRecruits();
				return getRecruitsRes(recruits);
			}

			if (countryName != null && regionName == null) {
				Long countryId = countryService.findCountry(countryName).getCountryIdx();
				companyList = companyService.findCompaniesWithImage(countryId);
			}

			if (countryName != null && regionName != null) {
				GetLocationIdRes location = locationService.findLocation(countryName, regionName);
				Long countryId = location.getCountryId();
				Long regionId = location.getRegionId();

				companyList = companyService.findCompaniesWithImage(countryId, regionId);
			}

			if (countryName == null && regionName == null) {
				companyList = companyService.findCompaniesWithImage();
			}


			if (tagId != null) {
				List<Long> tagCompany = companyService.findTagCompany(tagId)
					.stream().map(TagCompany::getCompanyIdx).collect(Collectors.toList());
				companyList = companyList.stream()
					.filter(company -> tagCompany.contains(company.getCompanyIdx()))
					.collect(Collectors.toList());
			}


			List<List<GetRecruitsRes>> getResultListRes = companyList.stream()
				.map(company -> {
					try {
						GetLocationRes location = locationService.findLocation(company.getCountryIdx(), company.getRegionIdx());

						if (year != null) {
							List<Recruit> recruits = recruitService.findRecruits(year, company.getCompanyIdx());

							return recruits.stream()
								.map(recruit -> {
									try {
										return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
											company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
											likeService.getLikeCount(recruit.getRecruitIdx()), company.getResponseRate(), recruit.getMinExperience());
									} catch (BaseException exception) {
										exception.printStackTrace();
										throw new RuntimeException();
									}
								})
								.collect(Collectors.toList());
						}
						List<Recruit> recruits = recruitService.findRecruits(company.getCompanyIdx());

						return recruits.stream()
							.map(recruit -> {
								try {
									return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
										company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
										likeService.getLikeCount(recruit.getRecruitIdx()) , company.getResponseRate(), recruit.getMinExperience());
								} catch (BaseException e) {
									e.printStackTrace();
									throw new RuntimeException();
								}
							})
							.collect(Collectors.toList());

					} catch (Exception exception) {
						exception.printStackTrace();
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());


			return getResultListRes.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());


		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}



	public List<GetRecruitsRes> findRecruitJob(Long groupId, Long jobId, GetRecruitsReq getRecruitsReq) throws BaseException {
		try {
			Long tagId = getRecruitsReq.getTagId();
			Integer year = getRecruitsReq.getYear();
			String regionName = getRecruitsReq.getLocation();
			String countryName = getRecruitsReq.getCountry();

			List<GetCompanyWithImageRes> companyList = new ArrayList<>();


			if (tagId == null && year == null && regionName == null && countryName == null) {
				List<Recruit> recruits = recruitService.findRecruitsJobAll(groupId, jobId);
				return getRecruitsRes(recruits);
			}

			if (countryName != null && regionName == null) {
				Long countryId = countryService.findCountry(countryName).getCountryIdx();
				companyList = companyService.findCompaniesWithImage(countryId);
			}

			if (countryName != null && regionName != null) {
				GetLocationIdRes location = locationService.findLocation(countryName, regionName);
				Long countryId = location.getCountryId();
				Long regionId = location.getRegionId();

				companyList = companyService.findCompaniesWithImage(countryId, regionId);
			}

			if (countryName == null && regionName == null) {
				companyList = companyService.findCompaniesWithImage();
			}


			if (tagId != null) {
				List<Long> tagCompany = companyService.findTagCompany(tagId)
					.stream().map(TagCompany::getCompanyIdx).collect(Collectors.toList());
				companyList = companyList.stream()
					.filter(company -> tagCompany.contains(company.getCompanyIdx()))
					.collect(Collectors.toList());
			}


			List<List<GetRecruitsRes>> getResultListRes = companyList.stream()
				.map(company -> {
					try {
						GetLocationRes location = locationService.findLocation(company.getCountryIdx(), company.getRegionIdx());

						if (year != null) {
							List<Recruit> recruits = recruitService.findRecruitsJobAll(groupId, jobId, year, company.getCompanyIdx());
							return recruits.stream()
								.map(recruit -> {
									try {
										return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
											company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
											likeService.getLikeCount(recruit.getRecruitIdx()), company.getResponseRate(), recruit.getMinExperience());
									} catch (BaseException e) {
										e.printStackTrace();
										throw new RuntimeException();
									}
								})
								.collect(Collectors.toList());
						}
						List<Recruit> recruits = recruitService.findRecruitsJobAll(groupId, jobId, company.getCompanyIdx());

						return recruits.stream()
							.map(recruit -> {
								try {
									return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
										company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
										likeService.getLikeCount(recruit.getRecruitIdx()), company.getResponseRate(), recruit.getMinExperience());
								} catch (BaseException e) {
									e.printStackTrace();
									throw new RuntimeException();
								}
							})
							.collect(Collectors.toList());

					} catch (Exception exception) {
						exception.printStackTrace();
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());


			return getResultListRes.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());


		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


	public List<GetRecruitsRes> findRecruitGroup(Long groupId, GetRecruitsReq getRecruitsReq) throws BaseException {
		try {
			Long tagId = getRecruitsReq.getTagId();
			Integer year = getRecruitsReq.getYear();
			String regionName = getRecruitsReq.getLocation();
			String countryName = getRecruitsReq.getCountry();

			List<GetCompanyWithImageRes> companyList = new ArrayList<>();

			if (tagId == null && year == null && regionName == null && countryName == null) {
				List<Recruit> recruits = recruitService.findRecruitsGroupAll(groupId);
				return getRecruitsRes(recruits);
			}

			if (countryName != null && regionName == null) {
				Long countryId = countryService.findCountry(countryName).getCountryIdx();
				companyList = companyService.findCompaniesWithImage(countryId);
			}

			if (countryName != null && regionName != null) {
				GetLocationIdRes location = locationService.findLocation(countryName, regionName);
				Long countryId = location.getCountryId();
				Long regionId = location.getRegionId();

				companyList = companyService.findCompaniesWithImage(countryId, regionId);
			}

			if (countryName == null && regionName == null) {
				companyList = companyService.findCompaniesWithImage();
			}

			if (tagId != null) {
				List<Long> tagCompany = companyService.findTagCompany(tagId)
					.stream().map(TagCompany::getCompanyIdx).collect(Collectors.toList());
				companyList = companyList.stream()
					.filter(company -> tagCompany.contains(company.getCompanyIdx()))
					.collect(Collectors.toList());
			}


			List<List<GetRecruitsRes>> getResultListRes = companyList.stream()
				.map(company -> {
					try {
						GetLocationRes location = locationService.findLocation(company.getCountryIdx(), company.getRegionIdx());

						if (year != null) {
							List<Recruit> recruits = recruitService.findRecruitsGroupAll(groupId, year, company.getCompanyIdx());
							return recruits.stream()
								.map(recruit -> {
									try {
										return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
											company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
											likeService.getLikeCount(recruit.getRecruitIdx()), company.getResponseRate(), recruit.getMinExperience());
									} catch (BaseException e) {
										e.printStackTrace();
										throw new RuntimeException();
									}
								})
								.collect(Collectors.toList());
						}
						List<Recruit> recruits = recruitService.findRecruitsGroupAll(groupId, company.getCompanyIdx());

						return recruits.stream()
							.map(recruit -> {
								try {
									return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
										company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
										likeService.getLikeCount(recruit.getRecruitIdx()), company.getResponseRate(), recruit.getMinExperience());
								} catch (BaseException e) {
									e.printStackTrace();
									throw new RuntimeException();
								}
							})
							.collect(Collectors.toList());

					} catch (Exception exception) {
						exception.printStackTrace();
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());


			return getResultListRes.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());


		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


	public List<GetRecruitsRes> searchRecruit(String keyword) throws BaseException {

		try {
			List<Recruit> recruits = recruitService.findRecruitsAll(keyword);
			return getRecruitsRes(recruits);

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


	// #=================================================
	// # INTERNAL SERVICE
	// #=================================================
	@NotNull
	private List<GetRecruitsRes> getRecruitsRes(List<Recruit> recruits) {
		return recruits.stream()
			.map(recruit -> {
				try {
					GetCompanyWithImageRes company = companyService.findCompanyWithImage(recruit.getCompanyIdx());
					GetLocationRes location = locationService.findLocation(company.getCountryIdx(), company.getRegionIdx());

					return new GetRecruitsRes(recruit.getRecruitIdx(), company.getCompanyIdx(), company.getImage().isEmpty() ? null : company.getImage().get(0), recruit.getTitle(),
						company.getName(), location.getCountryName(), location.getRegionName(), recruit.getReward(),
						likeService.getLikeCount(recruit.getRecruitIdx()), company.getResponseRate(), recruit.getMinExperience());
				} catch (Exception exception) {
					exception.printStackTrace();
					throw new RuntimeException();
				}
			})
			.collect(Collectors.toList());
	}


}
