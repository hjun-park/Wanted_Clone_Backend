package com.example.demo.src.tag;

import com.example.demo.config.BaseException;
import com.example.demo.src.tag.model.Tag;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

	private final TagDAO tagDAO;
	private final JwtService jwtService;

	public TagServiceImpl(TagDAO tagDAO, JwtService jwtService) {
		this.tagDAO = tagDAO;
		this.jwtService = jwtService;
	}

	@Override
	public List<Tag> findTags(Long companyId) throws BaseException {
		try {
			return tagDAO.findTagsByCompanyId(companyId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	@Transactional
	public int registerTag(Long companyId, String tagName) throws BaseException {
		try {
			int insertedRow = tagDAO.insertTagAndTagCompany(companyId, tagName);
			return insertedRow;

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public Tag findTag(Long tagId) throws BaseException {
		try {
			return tagDAO.findTag(tagId);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	public Tag findTag(String tagName) throws BaseException {
		try {
			return tagDAO.findTag(tagName);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


}
