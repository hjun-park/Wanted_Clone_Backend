package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.src.content.model.*;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

	private final ContentDAO contentDAO;
	private final JwtService jwtService;

	public ContentServiceImpl(ContentDAO contentDAO, JwtService jwtService) {
		this.contentDAO = contentDAO;
		this.jwtService = jwtService;
	}

	@Override
	public List<GetContentRes> findContents(GetContentReq getContentReq) throws BaseException {
		try {

			Long labelId = getContentReq.getLabelId();
			Long typeId = getContentReq.getTypeId();
			String pay = getContentReq.getPay();

			List<ContentDTO> contents = filterContents(labelId, typeId, pay);

			return contents.stream()
				.map(content -> {
					try {
						TypeDTO type = contentDAO.findType(content.getTypeId());
						List<ContentLabelDTO> contentLabels = contentDAO.findContentLabelsBy(content.getContentId());
						List<LabelDTO> labels = contentLabels.stream()
							.map(contentLabel -> (contentDAO.findLabel(contentLabel.getLabelId())))
							.collect(Collectors.toList());

						return new GetContentRes(content.getTitleImage(), type.getName(), content.getTitle(),
							content.getStartDate(), content.getEndDate(), content.getIsAllTime(),
							labels.stream().map(LabelDTO::getName).collect(Collectors.toList()));

					} catch (Exception exception) {
						throw new RuntimeException();
					}
				}).collect(Collectors.toList());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


	@Override
	public GetContentDetailRes findContentDetail(Long contentId) throws BaseException {
		try {
			ContentDTO content = contentDAO.findContent(contentId);
			return new GetContentDetailRes(content.getTitle(), content.getContent());

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	@Transactional
	public Long registerContents(PostContentReq postContentReq) throws BaseException {
		try {
			return contentDAO.insertContents(postContentReq);
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}

	@Override
	@Transactional
	public Long updateContentsStatus(Long contentId) throws BaseException {
		try {
			return contentDAO.updateContentsStatus(contentId);
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new BaseException(DATABASE_ERROR);
		}
	}


	// #==========================================
	// # INTERNAL USE
	// #==========================================
	private List<ContentDTO> filterContents(Long labelId, Long typeId, String pay) {

		int filterValue = 0;
		if (labelId == null) filterValue += 1;
		if (typeId == null) filterValue += 10;
		if (pay == null) filterValue += 100;

		switch (filterValue) {
			case (0):
				return contentDAO.findContentsByLabelAndTypeAndPay(labelId, typeId, pay);
			case (1):
				return contentDAO.findContentsByTypeAndPay(typeId, pay);
			case (10):
				return contentDAO.findContentsByLabelAndPay(labelId, pay);
			case (100):
				return contentDAO.findContentsByLabelAndType(labelId, typeId);
			case (11):
				return contentDAO.findContentsByPay(pay);
			case (101):
				return contentDAO.findContentsByType(typeId);
			case (110):
				return contentDAO.findContentsByLabel(labelId);
		}

		return contentDAO.findContentsAll();
	}


}
