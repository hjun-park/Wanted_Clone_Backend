package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.GetLikeUserRes;
import com.example.demo.src.like.model.GetLikesRes;

import java.util.List;

public interface LikeService {

	// 31 좋아요 목록 조회 (JWT 토큰 사용)
	List<GetLikesRes> findLikes() throws BaseException;

	// 32 좋아요 등록
	int createLike(Long Id) throws BaseException;

	// 33 좋아요 삭제
	int modifyLikeStatus(Long Id) throws BaseException;

	// 34 해당 채용공고 좋아요 유저 리스트
	List<GetLikeUserRes> findLikeUsers(Long Id) throws BaseException;

	int getLikeCount(Long recruitId) throws BaseException;
}
