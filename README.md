# Sporting 어플리케이션
![로고](https://user-images.githubusercontent.com/118786401/235388216-78fb76d0-b9ec-4dad-ab40-c12c2a86176b.png)

## 시연 영상
TODO 유튜브 링크 들어갈 곳

## 프로젝트 2조
- 김태훈(팀장)
- 전국일(Front-End)
- 이상현(Back-End)
- 임원빈(Back-End)
- 박지연(Back-End)

## 기술 스택
### Back-End
|<img src = "https://blog.kakaocdn.net/dn/cKtAuQ/btrAIO5fzCU/NVWnU8UlhL93kq81Ve87uK/img.png" width="150" height="150" />|
|:--:|
|SpringBoot|

### Front-End
|<img src = "https://user-images.githubusercontent.com/118786401/235388856-a9918dac-e5ed-4cb3-86cc-6bf65ba00e85.png" width="150" height="150" />|
|:--:|
|Flutter|
### 협업툴
|<img src = "https://blog.kakaocdn.net/dn/eyjfrN/btrAKvXV0RA/zkyytdkZy7ESd85knYRDq1/img.png" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/mEK9t/btrAHjxWZX3/iEGILm2rWSrOKsfilmPUA1/img.png" width="150" height="150" />|<img src = "https://user-images.githubusercontent.com/118786401/235388614-9304e93a-be4c-43ee-a203-22ff64ddd1da.png" width="150" height="150" />|
|:--:|:--:|:--:|
|Git|Github|Jira|

### DataBase
|<img src = "https://user-images.githubusercontent.com/118786401/235388737-3027032d-547f-433d-aaad-703e83c86397.svg" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/ccYmmf/btrAGfJoswn/gVqLJkEUq6WgY1MwOEopD1/img.png" width="150" height="150" />|
|:--:|:--:|
|H2|MySQL|

## 사용한 라이브러리
-  boot:spring-boot-starter-mail
-  boot:spring-boot-starter-validation
-  boot:spring-boot-starter-security
-  boot:spring-boot-starter-data-jpa
-  boot:spring-boot-starter-web
-  boot:spring-boot-starter-test

-  security:spring-security-test
-  servlet:jstl
-  embed:tomcat-embed-jasper
-  codec:commons-codec:1.15
-  cloud:spring-cloud-starter-aws:2.2.6.RELEASE
-  projectlombok:lombok
-  boot:spring-boot-devtools
-  h2database:h2
-  projectlombok:lombok

-  querydsl:querydsl-jpa:5.0.0
-  querydsl:querydsl-core:5.0.0
-  querydsl:querydsl-apt:5.0.0:jpa
-  persistence:hibernate-jpa-2.1-api:1.0.2.Final
-  annotation:javax.annotation-api:1.3.2

## 기능 설명
**스포르팅**은 전국의 경기장을 예약 및 결제할 수 있고, 업주로서 경기장을 등록하는 기능을 제공하는 어플리케이션입니다.

### 일반 회원 관련 기능
- 이메일 회원가입 기능
- 이메일 로그인 기능
- 내 정보 수정 기능

|||
| :------------: | :-------------: |
| 회원가입 | 로그인 |
| ![회원가입 (1)](https://user-images.githubusercontent.com/118786401/235410401-2dc8e4aa-6044-478d-9c48-f94326645663.gif) | ![로그인 (1)](https://user-images.githubusercontent.com/118786401/235410412-9ee2a68d-bba0-4b51-9fb0-f3dedb265d13.gif) |

### 기업 회원 관련 기능
- 이메일 회원가입 기능
- 이메일 로그인 기능
- 내 정보 수정 기능

|||
| :------------: | :-------------: |
| 회원가입 | 로그인 |
| ![회원가입 (1)](https://user-images.githubusercontent.com/118786401/235410401-2dc8e4aa-6044-478d-9c48-f94326645663.gif) | ![로그인 (1)](https://user-images.githubusercontent.com/118786401/235410412-9ee2a68d-bba0-4b51-9fb0-f3dedb265d13.gif) |

### 경기장 관련 기능
- 경기장 목록보기
- 경기장 상세보기
- 경기장 등록하기 기능
- 내가 등록한 경기장 목록보기
- 내가 등록한 경기장 상세보기
- 내가 등록한 경기장 수정하기
- 결제 기능

|||
| :------------: | :-------------: |
| 경기장 목록보기 |  |
| ![경기장-리스트](https://user-images.githubusercontent.com/118786401/235410807-0d7a5bbd-2841-409e-bd3e-bfeeb35ef20a.gif) |  |

### 기타 기능
- 이달의 선수 보기 기능
- 이달의 경기장 보기 기능

|||
| :------------: | :-------------: |
| 이달의 선수 보기 기능 | 이달의 경기장 보기 기능 |
| ![명예의선수](https://user-images.githubusercontent.com/118786401/235411237-b8f0940c-3990-4f4f-9cb5-050116f2930f.gif) | ![이달의구장](https://user-images.githubusercontent.com/118786401/235411254-7c8348f2-a02e-4fd0-8c73-4d7176604ec1.gif) |

## 테이블 설계
![DB설계](https://user-images.githubusercontent.com/118786401/235402286-40c1a4a8-8624-4600-b9d9-5d12499ea795.png)

## Jira
https://nomadhuns.atlassian.net/jira/software/projects/GFP/boards/1/roadmap   
역할분담, 계획수립 등 지라를 유용하게 사용하였다.

## 블로깅 링크
https://spark-mailbox-fe3.notion.site/K-Digital-Final-Project-7414fcb59b90426199456f580585a91e

## 테스트 코드 링크
### S3
 - Github : https://github.com/aozp73/S3_Sample
 - Blog : https://blog.naver.com/aozp73/223079979159 (AWS-S3란)
		
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  https://blog.naver.com/aozp73/223083368954 (가입 및 버킷세팅)  
  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  https://blog.naver.com/aozp73/223083368954 (SpringBoot 세팅)  
  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  https://blog.naver.com/aozp73/223083879283 (전체 진행과정)  
  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  https://blog.naver.com/aozp73/223083889674 (버킷 공유-IAM)  

### QueryDSL
 - Github : https://github.com/aozp73/querydsl
 - Blog : https://blog.naver.com/aozp73/223079188235 (전체 진행과정)

### Admin
 - Github : https://github.com/aozp73/JPA-AdministratorSample_layout-paging-searching
 - Blog : https://blog.naver.com/aozp73/223083980064 (전체 진행과정)  
     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  https://blog.naver.com/aozp73/223084021492 (페이징 및 검색) 

## 보완할 점
- OAuth를 통한 회원가입 및 로그인
- 일반 회원 및 기업 회원 통계
- 지도 API를 사용하여서 주변 경기장 찾기 기능
- 리뷰 관련 기능
- 고객센터 문의 기능
- 포인트 기능

## 프로젝트 진행하면서 느낀점
- AWS S3에 사진파일을 저장하여 활용하였다. 기존 DB와의 통신과 더불어 외부 통신 코드가 추가되어 부하가 커지지 않을까에 대한 고민이 있었다.
  평소 최대한 클린코드 및 간결한 코드를 작성하여 전체적인 부하에 대해 신경써야겠다는 생각이 들었다.
- 1~2차 프로젝트에선 Mybatis를 활용하였고, 3차 프로젝트에선 JPA를 공부하며 사용하였다. 
  JpaRepository 상속에 따른 기본 CRUD 간결화 및 네이밍 쿼리 사용으로 Repository 진행보다 비지니스 로직에 조금 더 집중할 수 있었다.
- Test 코드에 대해 많은 관심을 가지고 프로젝트를 시작하였지만, 진행한 Test 코드가 실질적으로 많은 상황을 보완해줄 수 있을까라는 생각이 들었다.
  Repository 테스트, 단위 테스트 및 통합 테스트를 진행한 결과 아쉬운 부분이 존재하였고, 기회가 된다면 Test코드에 대해 전문적으로 배우고 싶다는 
  생각이 들었다.
  
