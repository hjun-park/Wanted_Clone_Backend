package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.*;
import com.example.demo.src.tag.TagService;
import com.example.demo.src.tag.model.Tag;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	private final CompanyDAO companyDAO;
	private final TagService tagService;
	private final JwtService jwtService;


	public CompanyServiceImpl(CompanyDAO companyDAO, TagService tagService, JwtService jwtService) {
		this.companyDAO = companyDAO;
		this.tagService = tagService;
		this.jwtService = jwtService;
	}

	@Override
	public String findOneImage(Long companyId) throws BaseException {
		try {
			Optional<CompanyImage> optImages = companyDAO.findImagesById(companyId)
				.stream().findFirst();

			return optImages.map(CompanyImage::getImage)
				.orElse("unknown");

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public List<String> findImages(Long companyId) throws BaseException {
		try {
			List<CompanyImage> companyImages = companyDAO.findImagesById(companyId);

			return companyImages.stream()
				.map(CompanyImage::getImage)
				.collect(Collectors.toList());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public Company findCompany(Long companyId) throws BaseException {
		try {
			return companyDAO.findCompanyById(companyId);

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public GetCompanyWithImageRes findCompanyWithImage(Long companyId) throws BaseException {
		try {
			Company company = companyDAO.findCompanyById(companyId);

			List<String> images = companyDAO.findImagesById(companyId)
				.stream().map(CompanyImage::getImage).collect(Collectors.toList());

			return extractCompanyWithImageRes(company, images);

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public List<GetSearchCompanyRes> searchCompanies(String keyword) throws BaseException {
		try {
			List<Company> companyList = companyDAO.findCompanyByName(keyword);

			return companyList.stream()
				.map((company) -> (new GetSearchCompanyRes(company.getCompanyIdx(), company.getLogo(),
					company.getName(), company.getResponseRate())))
				.collect(Collectors.toList());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public GetCompanyDetailRes findCompanyDetail(Long companyId) throws BaseException {
		try {
			Company company = companyDAO.findCompanyById(companyId);
			List<String> companyImages = companyDAO.findImagesById(companyId)
				.stream().map(CompanyImage::getImage).collect(Collectors.toList());
			List<String> tags = tagService.findTags(companyId)
				.stream().map(Tag::getName).collect(Collectors.toList());

			return new GetCompanyDetailRes(company.getLogo(), company.getName(),
				company.getResponseRate(), companyImages, company.getIntro(), tags);

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public List<GetCompanyWithImageRes> findCompaniesWithImage(Long countryId) throws BaseException {
		try {
			List<Company> companyList = companyDAO.findCompanies(countryId);

			return companyList.stream()
				.map(company -> {
					try {
						List<String> images = findImages(company.getCompanyIdx());
						return extractCompanyWithImageRes(company, images);
					} catch (Exception exception) {
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());


		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public List<GetCompanyWithImageRes> findCompaniesWithImage(Long countryId, Long regionId) throws BaseException {
		try {
			List<Company> companyList = companyDAO.findCompanies(countryId, regionId);

			return companyList.stream()
				.map(company -> {
					try {
						List<String> images = findImages(company.getCompanyIdx());
						return extractCompanyWithImageRes(company, images);
					} catch (Exception exception) {
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


	@Override
	public List<GetTagCompanyRes> searchTagCompany(String tagName) throws BaseException {

		try {
			Tag tag = tagService.findTag(tagName);
			if (tag == null) return Collections.<GetTagCompanyRes>emptyList();

			List<TagCompany> tagCompany = companyDAO.findTagCompany(tag.getTagIdx());

			return tagCompany.stream()
				.map(tc -> {
					try {
						Company company = companyDAO.findCompanyById(tc.getCompanyIdx());
						List<String> tags = tagService.findTags(tc.getCompanyIdx())
							.stream().map(Tag::getName).collect(Collectors.toList());
						return new GetTagCompanyRes(company.getLogo(), company.getName(), tags);
					} catch (Exception exception) {
						exception.printStackTrace();
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}

	}

	@Override
	public List<TagCompany> findTagCompany(Long tagId) throws BaseException {
		try {
			return companyDAO.findTagCompany(tagId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}

	}

	@Override
	public List<GetCompanyWithImageRes> findCompaniesWithImage() throws BaseException {
		try {
			List<Company> companyList = companyDAO.findCompanies();

			return companyList.stream()
				.map(company -> {
					try {
						List<String> images = findImages(company.getCompanyIdx());
						return extractCompanyWithImageRes(company, images);
					} catch (Exception exception) {
						throw new RuntimeException();
					}
				})
				.collect(Collectors.toList());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public List<GetCompanyNewsRes> crawlingNews(String query) throws BaseException {

		Elements companyNews;

		try {
			String address = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=" + query;
			Document rawData = Jsoup.connect(address).timeout(5000).get();

			companyNews = rawData.select("div.news_area");

		} catch(IOException e) {
			throw new BaseException(RESPONSE_ERROR);
		}

		return companyNews.stream()
			.map(option -> new GetCompanyNewsRes(option.select("a.news_tit").select("a").attr("title"),
				option.select("a.info").text(), option.select("a.news_tit").select("a").attr("href")
			))
			.limit(4)
			.collect(Collectors.toList());
	}


	// # ============================
	// # INTERNAL USE
	// # ============================
	@NotNull
	private GetCompanyWithImageRes extractCompanyWithImageRes(Company company, List<String> images) {
		return new GetCompanyWithImageRes(company.getCompanyIdx(), company.getCountryIdx(), company.getRegionIdx(),
			company.getName(), company.getAddress(), company.getLicenseNumber(), company.getSales(),
			company.getEmployees(), company.getIntro(), company.getYear(), company.getEmail(), company.getPhone(),
			company.getUrl(), company.getKeyword(), company.getResponseRate(), company.getLogo(),
			company.getUserIdx(), company.getLatitude(), company.getLongitude(), images);
	}
}
