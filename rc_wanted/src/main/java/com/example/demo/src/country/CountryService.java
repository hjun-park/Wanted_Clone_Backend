package com.example.demo.src.country;

import com.example.demo.config.BaseException;
import com.example.demo.src.country.model.CountryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CountryService {

	private final CountryDAO countryDAO;

	public CountryService(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	public CountryDTO findCountry(Long countryId) throws BaseException {
		try {
			return countryDAO.findCountry(countryId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public CountryDTO findCountry(String countryName) throws BaseException {
		try {
			return countryDAO.findCountry(countryName);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
