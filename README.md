# 오늘노래추천

  
> 음악, 플레이리스트 추천 커뮤니티의 백엔드 api 서버

---

## 1. 프로젝트 개요

- **프로젝트 목적**
    - 하루의 노래 추천
- **주요 기능**
    - 회원 가입 / 로그인 (JWT 기반 인증)
    - 인피니티 스크롤링
    - 게시판 기능 및 댓글, 추천 기능

---

## 2. 기술 스택

### Backend
- Language: Java
- Framework: Spring Boot
- Build Tool: Gradle

### Database & Storage
- DB: MySQL

---

## 3. 프로젝트 구조

```bash
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── anyword
    │   │               ├── AnywordApplication.java
    │   │               ├── aop
    │   │               │   ├── AuthAspect.java
    │   │               │   └── Authable.java
    │   │               ├── config
    │   │               │   └── Appconfig.java
    │   │               ├── controller
    │   │               │   ├── ArticleController.java
    │   │               │   ├── AuthController.java
    │   │               │   ├── CommentController.java
    │   │               │   ├── LikeController.java
    │   │               │   └── UserController.java
    │   │               ├── dto
    │   │               │   ├── BaseResponseDto.java
    │   │               │   ├── article
    │   │               │   │   ├── ArticleListItemDto.java
    │   │               │   │   ├── ArticleStatusInfoDto.java
    │   │               │   │   ├── AuthorInfoDto.java
    │   │               │   │   ├── PageInfoDto.java
    │   │               │   │   ├── request
    │   │               │   │   │   ├── PostArticleRequestDto.java
    │   │               │   │   │   └── PutArticleRequestDto.java
    │   │               │   │   └── response
    │   │               │   │       ├── GetArticleListResponseDto.java
    │   │               │   │       ├── GetArticleResponseDto.java
    │   │               │   │       ├── PostArticleResponseDto.java
    │   │               │   │       └── PutArticleResponseDto.java
    │   │               │   ├── auth
    │   │               │   │   └── RefreshResponseDto.java
    │   │               │   ├── comment
    │   │               │   │   ├── CommentItemDto.java
    │   │               │   │   ├── CommentRequestDto.java
    │   │               │   │   ├── CreateCommentResponseDto.java
    │   │               │   │   └── GetCommentListResponseDto.java
    │   │               │   └── user
    │   │               │       ├── request
    │   │               │       │   ├── LoginRequestDto.java
    │   │               │       │   ├── PutUserRequestDto.java
    │   │               │       │   └── SignupRequestDto.java
    │   │               │       └── response
    │   │               │           ├── LoginResponseDto.java
    │   │               │           ├── SignupResponseDto.java
    │   │               │           └── UserResponseDto.java
    │   │               ├── entity
    │   │               │   ├── ArticleEntity.java
    │   │               │   ├── ArticleImageEntity.java
    │   │               │   ├── BaseEntity.java
    │   │               │   ├── CommentEntity.java
    │   │               │   ├── LikeArticleEntity.java
    │   │               │   ├── RefreshTokenEntity.java
    │   │               │   └── UserEntity.java
    │   │               ├── mapper
    │   │               │   ├── ArticleMapper.java
    │   │               │   ├── ArticleMapperIpl.java
    │   │               │   ├── CommentMapper.java
    │   │               │   ├── CommentMapperIpl.java
    │   │               │   ├── UserMapper.java
    │   │               │   └── UserMapperImpl.java
    │   │               ├── repository
    │   │               │   ├── BaseRepository.java
    │   │               │   ├── article
    │   │               │   │   └── ArticleRepository.java
    │   │               │   ├── articleImage
    │   │               │   │   └── ArticleImageRepository.java
    │   │               │   ├── auth
    │   │               │   │   └── RefreshTokenRepository.java
    │   │               │   ├── comment
    │   │               │   │   └── CommentRepository.java
    │   │               │   ├── like
    │   │               │   │   └── LikeArticleRepository.java
    │   │               │   └── user
    │   │               │       └── UserRepository.java
    │   │               ├── security
    │   │               │   ├── CustomUserDetails.java
    │   │               │   ├── CustomUserDetailsService.java
    │   │               │   ├── JWTUtil.java
    │   │               │   ├── JwtAuthFilter.java
    │   │               │   └── config
    │   │               │       └── WebSecurityConfig.java
    │   │               ├── service
    │   │               │   ├── ArticleService.java
    │   │               │   ├── AuthService.java
    │   │               │   ├── CommentService.java
    │   │               │   ├── LikeService.java
    │   │               │   ├── UserService.java
    │   │               │   └── utils
    │   │               │       └── ArticleUtils.java
    │   │               └── shared
    │   │                   ├── constants
    │   │                   │   ├── Key.java
    │   │                   │   ├── PageConstants.java
    │   │                   │   ├── ResponseMessage.java
    │   │                   │   └── ValidErrorMessage.java
    │   │                   ├── exception
    │   │                   │   ├── BadRequestException.java
    │   │                   │   ├── BusinessException.java
    │   │                   │   ├── ConflictException.java
    │   │                   │   ├── ForbiddenException.java
    │   │                   │   ├── GlobalExceptionHandler.java
    │   │                   │   ├── NotFoundException.java
    │   │                   │   ├── SessionExpiredException.java
    │   │                   │   └── UnauthorizedException.java
    │   │                   └── utils
    │   │                       └── RowsUtil.java
    │   └── resources
    │       └── application.properties
    ├── rest
    │   ├── article
    │   │   ├── createArticle.sh
    │   │   ├── deleteArticle.sh
    │   │   ├── getArticle.sh
    │   │   ├── getArticleList.sh
    │   │   └── putArticle.sh
    │   ├── comments
    │   │   ├── create.sh
    │   │   ├── delete.sh
    │   │   ├── get.sh
    │   │   ├── get_without_cookie.sh
    │   │   └── put.sh
    │   ├── like
    │   │   ├── deleteLike.sh
    │   │   └── like.sh
    │   └── user
    │       ├── current_get.sh
    │       ├── current_put.sh
    │       ├── login.sh
    │       ├── logout.sh
    │       ├── signout.sh
    │       └── signup.sh

```
4. 환경 변수

```
SERVER_PORT=
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET_KEY=
JWT_ACCESS_EXPIRATION=
JWT_REFRESH_EXPIRATION=

```
환경 변수 설정 방법
: .env.properties 생성 후 값 채우기



5. API 문서
Swagger UI: http://localhost:8080/swagger-ui/index.html


7. erd 설계

<iframe width="600" height="336" src="https://www.erdcloud.com/p/QnBFfnuNsbv5cXtHP" frameborder="0" allowfullscreen></iframe>


8. 인증/인가

인증 방식: jwt 사용
인가 방식: Spring security 사용

토큰 구조:
Access Token
Refresh Token
