# 개발일지

># 2021-07-31 [day1]
> ## 1. Done
> 1. 기획 회의
> - https://docs.google.com/document/d/1B-4DvFsIwb0ooG8phCHyo0MLnmyfJ8UfaZaufjcFnwc/edit
>
> 2. 1차 피드백 전 작업 목록 리스트
>  - ~ 8/2 오전 10시 회의: ERD 설계
>  - ~ 8/3 오전 10시 회의: API 명세서 작성
>  - ~ 8/5 오후 7시 피드백: 기본 정보 회원가입 / 로그인 API 개발
>
>3. 구현 페이지별 1차 분석
>  - https://docs.google.com/presentation/d/1tVtzhpDJD1YNrRoEpiDoL-78ol38lKL8Sk2MXTQQfoE/edit?usp=sharing
>
>4. ERD 설계
>  - User, Specialty, UserSpecialty, SpecialtySub, UserSpecialtySub, Skill, UserSkill 논리적 설계 완료
>
>## 2. Issue
>
>## 3. To Do (Tommorow)
>1. 앱 분석 완료
>2. 사용자 관련 파트 ERD 완성 
>   - 이력서, 지원, 좋아요, 북마크
>3. 로그인, 프로필 URI 작성

># 2021-08-01 [day2]
> ## 1. Done
> 1. ERD 1차 완성
> - https://aquerytool.com/aquerymain/index/?rurl=1af75366-4423-42e7-8a80-9452e0d04eea
> - password: 8ma2pe
>
>## 2. Issue
>
>## 3. To Do (Tommorow)
>1. API 리스트 업
>2. ERD 수정

># 2021-08-02 [day3]
> ## 1. Done
> 1. ERD 마무리
>
> 2. API 리스트업 (유저, 지원, 좋아요, 이력서, 북마크 관련)
> - https://docs.google.com/spreadsheets/d/1rSiTMdtzv797--5XRRukg2lqC1Q1ojtM8bSyj1ydH0w/edit#gid=1298737800
>
>## 2. Issue
>
>## 3. To Do (Tommorow)
>1. 전문분야, 스킬 관련 더미데이터 수집
>2. 1차 피드백
>3. 로그인/회원가입 API 구현

># 2021-08-03 [day4]
> ## 1. Done
> 1. 1차 피드백
> - ERD 수정 필요 내용: Cv 테이블 학력/경력 중복 저장에 관해 / 입력 글자수 제한이 정확하지 않은 경우 text타입으로 / displayOrder대로 출력되도록
> - API 수정 필요 내용: 개발 순서에 맞게 조정 / POST USER 부분 분리 필요
> 
> 2. API 리스트 수정
> - 개발 우선순위에 따라 조정
>
>## 2. Issue
>
>## 3. To Do (Tommorow)
>1. Login / POST USER 구현
>2. 1차 피드백 적용
>3. 직무 직군 관련 더미데이터 

># 2021-08-04 [day5]
> ## 1. Done
> 1. api 구현
> - [POST] /profile/email (1번): 로그인 또는 회원가입 전 이미 등록된 email인지 확인하는 api가 필요하다고 판단, 명세에 추가한 뒤 구현 완료
> - [POST] /profile (4번): 기본정보 회원가입 api 명세 추가 및 구현 완료 
> - [POST] /profile/login (5번): 로그인 api 명세 추가 및 구현 완료 
> 
> 2. ec2 spring boot 환경 구축
> - 자바 설치
> - 프록시 패스 설정
> 
> 3. 1에서 구축한 api 적용하여 빌드 후 nohup 백그라운드 실행
> 
>## 2. Issue
> 1. nginx 프록시 패스 설정 안되는 문제
> - 처음에 proxy_pass http://localhost:9000; 지정하였으나 실행되지 않았음
> -> 해결: 단순히 config 수정 후 restart 안했던 게 원인.........
>
> 2. 빌드 파일 실행 후 404 not found 뜨는 문제
> - jar 파일 실행 후 /test/log 하였으나 지속적으로 404 not found 오류 발생
> -> 해결: war로 빌드 후 실행 (https://yoursyun.tistory.com/entry/spring-boot-Jar-%EC%8B%A4%ED%96%89-%EC%8B%9C-Witelabel-Error-Page-404-Not-Found-%EC%97%90%EB%9F%AC-JSP)
> -> https://kingname.tistory.com/215
>
>## 3. To Do (Tommorow)
>

># 2021-08-05 [day6]
> ## 1. Done
> 1. api 구현 (dev 서버)
> - [POST] /profile/speciality 
> - [POST] /profile/career 
> - [POST] /profile/keyword
> 
> 2. erd 수정
> - api 개발하며 cv, cvCareer, cvEducation 테이블 컬럼 nullable 수정

># 2021-08-06 [day7]
> ## 1. Done
> 1. api 수정
> - [POST] /profile/speciality 
> - [POST] /profile/career 
> - [POST] /profile/keyword
> 
> 2. erd 수정
> - api 개발하며 cv, user 테이블 수정
> 

># 2021-08-07 [day8]
> ## 1. Done
> 1. api 개발 - 현재 dev 서버에서 테스트 진행중
> - [POST] /applicants/:recruitId
> - [GET] /cvs
> - [POST] /cvs
> - [GET] /cvs/:cvId
> - [PATCH] /cvs/:cvId
> - [PATCH] /cvs/:cvId/status
> 
> 2. erd 수정
> - Cv 간단소개, cvCareer 시작날짜, cvEdu 시작날짜 nullable로 바꿨고 날짜의 경우 VARCHAR(7)로 타입 변경 (YYYY-MM 형식으로 지정되어야 함)
> Application의 applicationStatus default ‘지원 완료’로 변경
> CvUploded 테이블 삭제 후 그냥 Cv에 병합
> ApplicationCv 관계 테이블 생성
> 
>## 2. Issue
> 1. github /feature/profile 병합 안되는 문제
> - dev에서 테스트 진행하며 변경되는 bin, log 파일 등의 수정사항으로 인해 WTD-1.0.0 브랜치와 자동 병합이 안됨
> - /feature/profile 브랜치의 파일을 삭제한 뒤 병합 시도하였으나 이 역시 되지 않음
> -> 해결: 변경된 profile 패키지의 폴더를 WTD-1.0.0의 profile 패키지에 덮어씌운 뒤 commit&push
>
>## 3. To Do (Tommorow)
> 1. 테스트한 api 모두 명세서 작성 후 서버 반영
> 2. [PATCH] profile 관련 api 개발 & 명세서 작성 & 서버 반영

># 2021-08-08 [day9]
> ## 1. Done
> 1. api 개발 - 명세 작성 필요
> - [POST] /applicants/:recruitId
> - [GET] /cvs
> - [POST] /cvs
> - [GET] /cvs/:cvId
> - [PATCH] /cvs/:cvId/status
> 
> 2. api 개발 - dev 서버 테스트 진행중
> - [PATCH] /cvs/:cvId
> - [GET]	/skills?skill={skill}
> - [GET]	/profile
> - [PATCH]	/profile/:profileId/status 

># 2021-08-09 [day10]
> ## 1. Done
> 1. api 개발 후 dev 서버 테스트, 명세작성 완료, prod 서버 업로드중 
> - [POST] /applicantions/:recruitId
> - [GET] /cvs
> - [POST] /cvs
> - [GET] /cvs/:cvId
> - [POST] /cvs/:cvId
> - [PATCH] /cvs/:cvId
> - [GET] /profile
> - [PUT] /profile
> - [GET] /profile/specialty
> - [GET] /profile/career
> - [GET] /applicants?applicantstatus = {applicantstatus}
> 
> 2. 수정할 사항 (회의 후 기록)
> - [PATCH] /cvs/:cvId -> 각 Cv 항목에 대해 분리 필요
> - [POST]	/applicantions/:recruitId -> [POST] /recurit/:recruitId/applications 로 URI 변경?
>
>## 2. To Do (Tommorow)
> 1. [GET] /skills?title={title} 개발, dev 서버 테스트, 명세 작성
> 2. [PATCH] /cvs/:cvId -> 각 Cv 항목에 대해 분리 
> 3. [GET] /jobGroups
> 4. [GET] /jobs?jobGroupIdx={jobGroupIdx} -> 추가

># 2021-08-10 [day11]
> ## 1. Done
> 1. [PATCH] /cvs/:cvId -> 각 Cv 항목에 대해 분리 api 구현 
> - [POST] /cvs/:cvId ...  7개
> - [DELETE] /cvs/:cvId ... 7개
>
> 2. [POST]	/applicantions/:recruitId -> [POST] /recurit/:recruitId/applications 로 URI 변경
>
> 3. api 구현 후 dev 서버 테스트
> - [GET] /skills?title={title} 
> - [GET] /jobGroups
> - [GET] /jobs?jobGroupIdx={jobGroupIdx}
> 
> 4. 추가적으로 구현할 기능 고민
> - 지원 상태 변경에 따른 알림센터 혹은 직군별 연봉에 대한 배치 프로그래밍
>
>## 2. To Do (Tommorow)
> 1. 개발 api 명세 작성 후 prod 서버 적용
> 2. 추가적으로 구현할 기능 확정 후 

># 2021-08-11 [day12]
> ## 1. Done
> 1. API 명세서 최신 업데이트 - 모든 API 명세서 업로드, prod 업로드 완료
> - profile 관련 API URI 수정 - 복수형 사용
> - cv 분리 API 구현 완료 및 테스트 완료
> - [GET] /skills?skill={skill} 구현 및 업로드 완료
> 
> 2. 챌린지...
> - FCM 활용한 웹 푸시알림 기능 시도중.....
> - 지원 사황이 변할때마다 알림 가도록....
> 
>## 2. To Do (Tommorow)
> 1. 최종마무리 및 영상 제



 
