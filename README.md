양지철

---
## 실행방법
```
./gradlew bootRun
```
- 서버포트 : 8080
- 기본 TEST Account 계정 insert되어있습니다.
- `Authentication` 헤더값이 없을 경우 외부사용자입니다.
```
INSERT INTO account (id, account_id, account_type) VALUES (2, '47', 'REALTOR');
INSERT INTO account (id, account_id, account_type) VALUES (3, '21', 'LESSOR');
INSERT INTO account (id, account_id, account_type) VALUES (4, '562', 'LESSEE');
```
### 글작성
```
curl --location 'localhost:8080/api/v1/posts' \
--header 'Authentication: Lessee 562' \
--header 'Content-Type: application/json' \
--data '{
    "title":"titleSample",
    "content":"contentSample"
}'
```
- [API 명세](docs/api/글작성.md)

### 글수정
```
curl --location --request PATCH 'localhost:8080/api/v1/posts/1' \
--header 'Authentication: Lessee 562' \
--header 'Content-Type: application/json' \
--data '{
    "title":"titleSample11",
    "content":"contentSample11"
}'
```
- [API 명세](docs/api/글수정.md)

### 글삭제
```
curl --location --request DELETE 'localhost:8080/api/v1/posts/1' \
--header 'Authentication: Lessee 562'
```
- [API 명세](docs/api/글삭제.md)

### 글목록
```
curl --location 'localhost:8080/api/v1/posts' \
--header 'Authentication: Lessee 562'
```
- [API 명세](docs/api/글목록.md)

### 글좋아요
```
curl --location --request POST 'localhost:8080/api/v1/posts/1/like' \
--header 'Authentication: Lessee 562'
```
- [API 명세](docs/api/글좋아요.md)

### 글좋아요 취소
```
curl --location --request DELETE 'localhost:8080/api/v1/posts/1/like' \
--header 'Authentication: Lessee 562'
```
- [API 명세](docs/api/글좋아요취소.md)

### 검증, 구현 방식
로그인 기능이 없고, 계정관리보다 게시판기능의 목적이 강해 SpringSecurity 라이브러리까지 필요하지 않다고 판단했습니다.

가입된 계정과 외부사용자의 권한 관리가 필요하여 인터셉터 `AuthenticationInterceptor`와 애노테이션 `Authority`을 활용하여 API 접근권한 관리를 하였습니다.

각 API에서 사용자 계정정보는 `AccountArgumentResolver`와 `AccountId`를 활용하여 파라미터를 통해 사용자 정보를 받아 올 수 있도록 구현하였습니다.

### 외부 라이브러리
- QueryDSL
  - 추후 검색기능이 추가가된다면 동적쿼리가 필요하다고 판단하여 QueryDSL을 적용하였습니다.

---
## 요구사항 정리
- [x] Java 8 OpenJDK 로 구현해주세요.
    - 8.0.352-zulu
- [x] Spring Boot 2.x 버전대를 사용해주세요.
    - 2.7.8
---
- [x] 글 작성
  - [x] 임대인, 임차인, 공인중개사는 커뮤니티에 글을 쓸 수 있고, 외부 사용자는 글을 쓸 수 없습니다.
  - [x] 작성 시간 확인가능
- [x] 글 수정
  - [x] 작성인만 수정가능
  - [x] 마지막 수정시간 확인가능
- [x] 글 삭제
  - [x] 작성인만 삭제가능
  - [x] 삭제시간 확인가능
- [x] 사용자 타입 구분 `HTTP Header Authentication Value Prefix` 사용
  - Realtor, Lessor, Lessee
- [x] 글 좋아요
  - [x] 글에 `좋아요`는 한 계정이 한 글에 한 번만 할 수 있습니다.
  - [x] 어떤 사용자가 어떤 글에 좋아요 했는지 히스토리를 확인할 수 있어야 합니다.
- [x] 임대인, 임차인, 공인중개사인 경우 커뮤니티에 별도의 회원 테이블을 만들어 사용합니다
- [x] 글 목록
  - [x] 글 목록에선 작성한 사용자가 어떤 계정 타입인지를 표시할 수 있어야 합니다. ex ) 김씨(공인중개사). 계정 타입은 한글로 표시되어야 합니다.
  - [x] 글 목록에는 글에 달린 좋아요 수를 표시할 수 있어야 합니다.
  - [x] 커뮤니티에 가입한 사용자라면 글 목록에 자신이 좋아요한 글인지 아닌지를 표시해줄 수 있어야 합니다.
- [x] 자리톡에 가입되어있는 공인중개사, 임대인, 임차인, 외부 사용자 모두 쓸 수 있도록 해야 합니다.
