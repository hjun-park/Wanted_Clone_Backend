package com.example.demo.src.location;

import com.example.demo.config.BaseException;
import com.example.demo.src.country.CountryService;
import com.example.demo.src.country.model.CountryDTO;
import com.example.demo.src.location.model.GetLocationIdRes;
import com.example.demo.src.location.model.GetLocationRes;
import com.example.demo.src.region.RegionService;
import com.example.demo.src.region.model.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
@Slf4j
public class LocationService {

	private final CountryService countryService;
	private final RegionService regionService;

	public LocationService(CountryService countryService, RegionService regionService) {
		this.countryService = countryService;
		this.regionService = regionService;
	}

	public GetLocationRes findLocation(Long countryId, Long regionId) throws BaseException {
		try {
			CountryDTO countryDTO = countryService.findCountry(countryId);
			Region region = regionService.findRegion(regionId);

			return new GetLocationRes(countryDTO.getName_eng(), region.getName_eng());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public GetLocationIdRes findLocation(String countryName, String regionName) throws BaseException {
		try {
			CountryDTO countryDTO = countryService.findCountry(countryName);
			Region region = regionService.findRegion(regionName);

			return new GetLocationIdRes(countryDTO.getCountryIdx(), region.getRegionIdx());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
