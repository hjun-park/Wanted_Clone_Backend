package com.example.demo.src.content;

import com.example.demo.src.content.model.*;

import java.util.List;

public interface ContentDAO {

	// Content 내용 1개
	ContentDTO findContent(Long contentId);

	// Content - 0개
	List<ContentDTO> findContentsAll();

	// Content - 1개(label)
	List<ContentDTO> findContentsByLabel(Long labelId);

	// Content - 1개(type)
	List<ContentDTO> findContentsByType(Long typeId);

	// Content - 1개(pay)
	List<ContentDTO> findContentsByPay(String pay);

	// Content - 2개 (label, type)
	List<ContentDTO> findContentsByLabelAndType(Long labelId, Long typeId);

	// Content - 2개 (label, pay)
	List<ContentDTO> findContentsByLabelAndPay(Long labelId, String pay);

	// Content - 2개 (type, pay)
	List<ContentDTO> findContentsByTypeAndPay(Long typeId, String pay);

	// Content - 3개 (label, type, pay)
	List<ContentDTO> findContentsByLabelAndTypeAndPay(Long labelId, Long typeId, String pay);

	// Type 내용 1개
	TypeDTO findType(Long typeId);

	// Type 내용 여러 개
	List<TypeDTO> findTypes();

	// ContentLabel 내용 1개
	ContentLabelDTO findContentLabel(Long contentLabelId);

	// ContentLabel 내용 여러개
	List<ContentLabelDTO> findContentLabels();

	// ContentLabel 내용 여러개 by contentId;
	List<ContentLabelDTO> findContentLabelsBy(Long contentId);

	// ContentCategory 내용 1개
	ContentCategoryDTO findContentCategory(Long contentCategoryId);

	// ContentCategory 내용 여러 개
	List<ContentCategoryDTO> findContentCategories();

	// Label 내용 1개
	LabelDTO findLabel(Long labelId);

    Long insertContents(PostContentReq postContentReq);

	Long updateContentsStatus(Long contentId);
}
