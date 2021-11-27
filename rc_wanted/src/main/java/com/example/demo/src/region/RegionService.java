package com.example.demo.src.region;

import com.example.demo.config.BaseException;
import com.example.demo.src.region.model.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@Transactional(readOnly = true)
public class RegionService {

	private final RegionDAO regionDAO;

	public RegionService(RegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}

	public Region findRegion(Long regionId) throws BaseException {
		try {
			return regionDAO.findRegion(regionId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public Region findRegion(String regionName) throws BaseException {
		try {
			return regionDAO.findRegion(regionName);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
