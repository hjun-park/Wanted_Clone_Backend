# 네이선 개발 일지


### [개발 이슈]
1. Company <-> Recruit 간 상호의존 관계 발생
2. jdbc queryForObject 쿼리 결과 null 반환으로 인한 NPE 문제
3. 채용공고 및 기업 리스트 필터 비효율적인 코드 개선
4. 필터에 대한 고민 (jdbcTemplate의 문제)
    - StringBuilder를 이용한 동적 쿼리 이용
    - 각 필터에 대해 DAO 메소드 생성
5. Location 도메인의 꽉막힌 구조 (Country + Region)
6. 서버 2명에 따른 Github 브랜치 구조

<br />

---

<br />

### [2021-07-31] 진행상황

- 기획서 제출[100%]
- EC2 서버 구축[30%]
- 1차 ERD 설계[35%]

<br />

### [2021-08-01] 진행상황

- 피드백을 바탕으로 기획서 수정[100%]
    - 개발 진행순서 변경
    - 네이선 개발 계획 순서 변경
- 1차 ERD 설계 [60%]
- EC2 서버 구축[100%]

<br />

### [2021-08-02] 진행상황

- 서버 회의 [100%]
    - ERD 테이블 통합 및 수정
    - Rest API 명세서 양식 공유
    - 서버 협업 방법 논의  (코드 공유)
- ERD 수정 [100%]
- RDS 구축 및 dev/prod 스키마 구분 [100%]
- 1차 Rest API 명세서 리스트 업 [50%]

<br />

### [2021-08-03] 진행상황

- 1차 피드백 완료
    - 클라이언트와 소통 방법 찾기
    - API 리스트업 추가 필요
- 더미 데이터 이미지 수집[100%]
- spring boot RDS 분리(profile)[100%]
- ERD 설계서 피드백 반영
    - 기업대표이미지 처리방식
- API 명세서 피드백 반영
    - 개발 기획서에 맞추어 순서 변경
- 개발 기획서 개발순서 일부 변경


<br />


### [2021-08-04] 진행상황

[금일 진행상황]
- RDS
    - RDS 테이블 생성[08/04][100%]
    - 더미데이터 입력[08/04][100%]
- ERD
    - '원티드 성장 커리어' 관련 테이블 추가[08/04][100%]
- API 명세서
    - '원티드 성장 커리어' 관련 API 추가[08/04][100%]
- EC2
    - 포트설정 및 jar 파일 실행 적용[08/04][100%]
- Spring Boot
    - 채용공고 조회 API 개발[08/04 ~ 08/06][35%]
 

[익일 진행예정]
- 팀 회의[08/05][100%]
- Spring Boot
    - 채용공고 관련 API 개발[08/04 ~ 08/06][80%]
- EC2
    - 빌드 및 실행 스크립트 제작[08/05 ~ 08/06][50%]

<br />

### [2021-08-05] 진행상황

[금일 진행상황]
- RDS & ERD
    - '채용공고' 테이블 최소경력 컬럼 추가[08/05][100%]
- EC2
    - build 도중 서버가 죽는 문제점 해결[08/05][100%]
- Spring Boot
    - 채용공고 관련 API 개발[08/04 ~ 08/06][75%]
- 팀 회의[08/05][100%]
    - 프론트, 백엔드 간 조율
    - 다음 회의 일정 결정

[익일 진행예정]
- EC2
    - build 및 실행 스크립트 제작[08/06 ~ 08/07][80%]
- Spring Boot
    - 채용공고 관련 API 개발[08/04 ~ 08/06][100%]
    - Search 관련 API 개발[08/06 ~ 08/07][50%]

<br />

### [2021-08-06] 진행상황

[금일 진행상황]
- EC2
    - build 및 실행 스크립트 제작[08/06 ~ 08/07][100%]
- Spring Boot
    - 채용공고 관련 API 개발[08/04 ~ 08/06][100%]
    - Search 관련 API 개발[08/06 ~ 08/07][50%]

[익일 진행예정]
- API 명세서
    - 채용공고 관련 API 명세서 작성[08/07][100%]
- Spring Boot
    - Search 관련 API 개발[08/06 ~ 08/07][100%]
    - 기업 관련 API 개발[08/07 ~ 08/09][30%]

<br />

### [2021-08-07] 진행상황

[금일 진행상황]
- ERD 설계서
    - '기업' 테이블 위도, 경도 컬럼 추가[08/07][100%]
- API 명세서
    - 채용공고 관련 API 명세서 작성[08/07][100%]
- Spring Boot
    - 채용공고 관련 API 일부 수정[08/07][100%]
        - 위도 경도 컬럼 추가에 따른 내용 수정
	- 기업이미지가 없는 경우 null 반환하지 않도록 변경
    - Search 관련 API 개발[08/06 ~ 08/08][80%]
    - 기업 관련 API 개발[08/07 ~ 08/09][30%]

[익일 진행예정]
- API 명세서
    - 기업 관련 API 명세서 작성[08/08][70%]
- Spring Boot
    - 기업 관련 API 개발[08/08][80%]
    - Search 관련 API 개발[08/07 ~ 08/08][100%]


<br />


### [2021-08-08] 진행상황

[금일 진행상황]
- Spring Boot
    - 기업 관련 API 개발[08/08][100%]
    - Search 관련 API 개발[08/07 ~ 08/08][100%]

[익일 진행예정]
- API 명세서
    - 기업 관련 API 명세서 작성[08/09][70%]
- Spring Boot
    - 북마크 API 개발[08/09][100%]
    - 좋아요 API 개발[08/09][80%]

<br />


### [2021-08-09] 진행상황

[금일 진행상황]
- API 명세서
    - 기업 관련 API 명세서 작성[08/09][100%]
    - 북마크 API 명세서 작성[08/09][100%]
    - 좋아요 API 명세서 작성[08/09][100%]
    - 태그 API 명세서 작성[08/09][100%]

- Spring Boot
    - 북마크 API 개발[08/09][100%]
    - 좋아요 API 개발[08/09][100%]
    - 태그 API 개발[08/09][100%]

[익일 진행예정]
- API 명세서
    - 커리어 성장 API 명세서 작성[08/10][100%]
- Spring Boot
    - 커리어 성장 API 명세서 작성[08/10][100%]
- 개발팀장님 2차 피드백[08/10][100%]


<br />


### [2021-08-10] 진행상황

[금일 진행상황]
- API 명세서
    - 커리어 성장 API 명세서 작성[08/10][100%]
- Spring Boot
    - 커리어 성장 API 명세서 작성[08/10][100%]
    - 채용공고 탐색 필터 개선[08/10][100%]
    - Location 도메인 country, region 분리[08/10][100%]
- 개발팀장님 2차 피드백[08/09][100%]

[익일 진행예정]
- API 명세서
    - 추가 개발 API 리스트업(25개)[08/11][100%]
- Spring Boot
    - 추가된 API 개발[08/11 ~ 08/12][50%]
    - Path Variable에 대한 result Code 처리[08/11][100%]
    - Google OAuth API 개발 [08/11][100%]

<br />

### [2021-08-11] 진행상황

[금일 진행상황]
- API 명세서
    - API 추가 리스트업(53~57) 
    - (53~57) API 명세서 작성[08/11][100%]
- Spring Boot
    - 채용공고 및 컨텐츠 API  구현[08/11][100%]
    - 기업 뉴스 크롤링 API 구현[08/11][100%]
    - Google OAuth API 구현[08/11][100%] 
- 기타
    - master 릴리즈 브랜치 병합 및 RDS Prod로 설정[08/11][100%]

[익일 진행예정]
- RDS
    - Prod RDS 데이터 입력[08/12][100%]
- Spring Boot
    - EC2 서버 반영 및 테스트[08/12][100%]
- 기타
    - 발표 영상 제작[08/12 ~ 08/13][70%]

<br />

### [2021-08-12] 진행상황

[금일 진행상황]
- RDS
    - Prod RDS 데이터 입력[08/12][100%]
    - Prod RDS 모든 이미지 URL 수정 적용[08/12][100%]
- Spring Boot
    - EC2 서버 반영 및 테스트[08/12][70%]
- CDN
    - S3 Cloudfront 이미지 서버 구축[08/12][100%]
- 기타
    - API 시연 영상 제작[08/12 ~ 08/13][30%]

[익일 진행예정]
- 기타
    - API 시연 영상 제작[08/12 ~ 08/13][100%]
- Spring Boot
    - EC2 서버 반영 및 테스트[08/12 ~ 08/13][100%]



<br />

---

<br />

### 받은 조언 

- open jdk 11 -> adopt open jdk ([https://inma.tistory.com/146](https://inma.tistory.com/146))
- 코드가 긴 내용의 삼항연산자의 경우 차라리 if else 로 사용할 것
- intellij 오른쪽 아래 branch 사용 할 것, git bash도 IDE에서 쓸 수 있다.
- annotation 순서는 되도록 맞추는 것이 중요(lombok -> Spring)

```java
// 이전

@RestController
@Slf4j
@RequestMapping("/bookmarks")
@Validated

// 이후

@Slf4j  // lombok 
@Validated
@RestController  // spring
@RequestMapping("/bookmarks")

```

- [modifyBookmarkStatus] JWT 검증은 interceptor 에서 해 주는 것이 좋은 듯 하다.
    - [https://www.google.com/search?q=interceptor+layer&tbm=isch&source=iu&ictx=1&fir=uR5DUxpOdeCU4M%2CMgiqLjOkS1Ph0M%2C_&vet=1&usg=AI4_-kQrcdTgklz_Ieed6N0O1CzvMElwMg&sa=X&ved=2ahUKEwjbi6qN-7DyAhUSC6YKHTyGCG8Q9QF6BAgXEAE&biw=1998&bih=1135#imgrc=uR5DUxpOdeCU4M](https://www.google.com/search?q=interceptor+layer&tbm=isch&source=iu&ictx=1&fir=uR5DUxpOdeCU4M%252CMgiqLjOkS1Ph0M%252C_&vet=1&usg=AI4_-kQrcdTgklz_Ieed6N0O1CzvMElwMg&sa=X&ved=2ahUKEwjbi6qN-7DyAhUSC6YKHTyGCG8Q9QF6BAgXEAE&biw=1998&bih=1135#imgrc=uR5DUxpOdeCU4M)
- 객체의 특징을 쓸 것이 아니라면 Long이 아닌 long을 사용할 것
    - toString이나 null값과 같은 것을 필히 이용할 것이 아니라면 long을 쓰는게 좋다 (무거우니까)
- [GetRecruitDetailRes] 는 우발적 중복
    - ![image](https://user-images.githubusercontent.com/70880695/129455587-edef8edd-6ec6-4ac6-84af-94c3f06f5e3c.png)
    - Recruit 도메인에서만 쓰고 싶었지만 Bookmark 등등 여러 곳에서 쓰이고 있다.
    - 그렇다면 어떻게? -> 똑같은 내용을 다른 도메인에서도 DTO를 만들어준다.
    - 이유: Recruit에 쓰고싶어서 만든 거를 Bookmark에서도 어쩔 수 없이 쓰게 되었을 때,
    이후에 Bookmark에서 GetRecruitDetailRes 변형된 걸 원하는 경우라면 수정하기 곤란.
    따라서 각 도메인마다 분리되어 있었다면 수정에 용이함
    - 참고: [https://www.google.com/search?q=클린+아키텍쳐+우발적+중복&ei=8aAXYaTuB_G4mAW6grmgAw&oq=클린+아키텍쳐+우발적+중복&gs_lcp=Cgdnd3Mtd2l6EANKBAhBGAFQq0hYyWNgg2VoCXAAeAKAAegBiAHPFJIBBjAuMTcuMZgBAKABAcABAQ&sclient=gws-wiz&ved=0ahUKEwjkqp3crLDyAhVxHKYKHTpBDjQQ4dUDCA4&uact=5](https://www.google.com/search?q=%ED%81%B4%EB%A6%B0+%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90+%EC%9A%B0%EB%B0%9C%EC%A0%81+%EC%A4%91%EB%B3%B5&ei=8aAXYaTuB_G4mAW6grmgAw&oq=%ED%81%B4%EB%A6%B0+%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90+%EC%9A%B0%EB%B0%9C%EC%A0%81+%EC%A4%91%EB%B3%B5&gs_lcp=Cgdnd3Mtd2l6EANKBAhBGAFQq0hYyWNgg2VoCXAAeAKAAegBiAHPFJIBBjAuMTcuMZgBAKABAcABAQ&sclient=gws-wiz&ved=0ahUKEwjkqp3crLDyAhVxHKYKHTpBDjQQ4dUDCA4&uact=5)


- findRecruitDetail 에서 return new GetRecruitDetailRes에서 생성자를 이용하여 값을 복잡하게 담으면,
대체 그 객체에 뭐가 담기는지 보기가 너무 어렵다. 차라리 코드가 길어져도 builder 쓰는 것이 좋다.

```java
[이전 방법]
			return new GetRecruitDetailRes(
				recruit.getRecruitIdx(),
				company.getImage(),
				recruit.getTitle(),
				recruit.getContent(),
				company.getResponseRate(), location.getRegionName(),
				location.getCountryName(), tags.stream().map(Tag::getName).collect(Collectors.toList()),
				company.getName(), recruit.getDeadline(), company.getAddress(),
				recruit.getReward(), company.getLogo(), company.getLatitude(), company.getLongitude());

[개선된 방법]
return GetRecruitDetailRes.builder()
				.recruitIdx(recruit.getRecruitIdx())
				.imageList(company.getImage())
				.recruitTitle(recruit.getTitle())
				.recruitContent(recruit.getContent())
				.responseRate(company.getResponseRate())
				.region(location.getRegionName())
				.country(location.getCountryName())
				.tags(tags.stream().map(Tag::getName).collect(Collectors.toList())) // private method ********
				.companyName(company.getName())
				.deadline(recruit.getDeadline())
				.address(company.getAddress())
				.reward(recruit.getReward())
				.companyLogo(company.getLogo())
				.latitude(company.getLatitude())
				.longitude(company.getLongitude())
				.build();

[구현 방법마다 다른 의견들이 있지만 이 방법은 코드가 더 간결하다]
 // GetRecruitDetailRes 생성자에 도메인 자체를 받는 방식
	public GetRecruitDetailRes(Company company, Recruit recruit){
		recruitIdx = company.getidx
    ...
	}

// 실제 코드가 짧게 나오긴하지만 도메인이 변하는 경우(컬럼이라던지) 수정이 필요하므로
// 이런 부분에 대해서는 고민할 필요가 있다.

// 아래는 전체코드 ( Query Param 여러개를 객체로 받는 방법 )

package com.example.demo.src.recruit.model;

import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

@ToString
public class GetRecruitsReq implements Serializable {

	@Nullable
	private String country;

	// 필드로 Optional 쓰지 않는 이유
	@Nullable
	private Integer year;

	@Nullable
	@Positive
	private Long tagId;

	@Nullable
	private String location;

	public GetRecruitsReq(String country, Integer year, Long tagId, String location) {
		this.country = country;
		this.year = year;
		this.tagId = tagId;
		this.location = location;
	}

	public Optional<String> getCountry() {
		return Optional.ofNullable(country);
	}

//	@Nullable
	public OptionalInt getYear() {

		if (year == null) {
			return OptionalInt.empty();
		}

		return OptionalInt.of(year);
	}

//	@Nullable
	public OptionalLong getTagId() {
		if (tagId == null) {
			return OptionalLong.empty();
		}

		return OptionalLong.of(tagId);
	}

//	@Nullable
	public Optional<String> getLocation() {

		return Optional.ofNullable(location);
	}
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public void setYear(Integer year) {
//		this.year = year;
//	}
//
//	public void setTagId(Long tagId) {
//		this.tagId = tagId;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
}
```

- null이 반환될 수 있는 경우라면 @nullable 을 사용하여 알리는 것이 좋고, 가장 최고의 방법은 Optional을 쓰는 것.

```java
[개선 전]
public BookmarkDTO findBookmark(int userId, Long recruitId) {

		String findBookmarkQuery = "select * from Bookmark " +
			"where userIdx = ? and recruitIdx = ? and status = 'used'";
		Object[] insertBookmarkParam = new Object[]{Integer.toString(userId), recruitId.toString()};

		try {
			return this.jdbcTemplate.queryForObject(findBookmarkQuery,
				(rs, rowNum) -> BookmarkDTO.builder()
					.bookmarkIdx(rs.getLong("bookmarkIdx"))
					.userIdx(rs.getLong("userIdx"))
					.recruitIdx(rs.getLong("recruitIdx"))
					.build()
				, insertBookmarkParam);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

[개선 후]
//	@Nullable
	public Optional<BookmarkDTO> findBookmark(int userId, Long recruitId) {

		String findBookmarkQuery = "select * from Bookmark " +
			"where userIdx = ? and recruitIdx = ? and status = 'used'";
		Object[] insertBookmarkParam = new Object[]{Integer.toString(userId), recruitId.toString()};

		try {
			return Optional.ofNullable(this.jdbcTemplate.queryForObject(findBookmarkQuery,
				(rs, rowNum) -> BookmarkDTO.builder()
					.bookmarkIdx(rs.getLong("bookmarkIdx"))
					.userIdx(rs.getLong("userIdx"))
					.recruitIdx(rs.getLong("recruitIdx"))
					.build()
				, insertBookmarkParam));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

====================================================================================
if (!tagId.isPresent() && !year.isPresent() && !regionName.isPresent() && !countryName.isPresent()) {
				List<Recruit> recruits = recruitService.findRecruits();
				return getRecruitsRes(recruits);
			}

```

- checked exception과 unchecked exception을 알아볼 것
    - 추가적으로 @RestControllerAdvice 사용 예제들도 볼 것
- 제네릭 vs Object(Java Object) 차이점 조사
- checkstyle 사용은 생산성을 높여준다.
    - 참고: [https://naver.github.io/hackday-conventions-java/#checkstyle](https://naver.github.io/hackday-conventions-java/#checkstyle)
- static method 사용처에 대해 알아볼 것
- service, DAO 개발 전 interface 부터 설계하는 것이 좋음
- 의존성 주입 시 interface를 받아야하는 이유, 구체 클래스는 왜 안되는지 조사
- 함수명은 서술방식으로 표현하려 해볼 것

    ```java
    - 회사 ID로 회사 객체를 얻어오는 경우
    - Company findCompanyById(Long companyId) -> 기존 내가 한 방식
        >> 이미 함수명에 ID로 회사를 찾는다 ID를 이용하고 회사를 반환한다. ( 복잡함 )
    - Company find(Long companyId)
    -   >> 찾는데 companyId를 찾는거고 Company를 반환한다는 걸 알 수 있음
    ```

- Bean Validation 사용할 것
- Location에 보면 Region과 Country가 같이 들어가 있는 상태
    - Region, Country 가져오려면 Location을 거쳐야 함
    - 만약 Region만 따로 빼는 인터페이스를 요구한다면 Location에 새로 만들어 줘야 한다.
    - 그렇게 되면 Location은 Region, Country, Region/Country 모두 다 책임져야 한다. (꽉막힌 구조)
    - Region, Location, Country 3개로 쪼갤 것
- @Transactional 전파 설정별 동작에 대해 알아보기
    - [https://deveric.tistory.com/86](https://deveric.tistory.com/86)
- updateBookmarkStatus 라고하면 Status를 무엇으로 바꿀 지 모른다.
updateBookmarkStatusToDelete 이런 식으로 어떤 값으로 바꾸는 건지 명시해줄 것
