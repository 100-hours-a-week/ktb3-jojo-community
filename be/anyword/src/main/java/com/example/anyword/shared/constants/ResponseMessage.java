package com.example.anyword.shared.constants;

public class ResponseMessage {

  // 공통
  public static final String SUCCESS = "success";
  public static final String FAIL = "fail";

  // 인증
  public static final String LOGIN_SUCCESS = "login_success";
  public static final String LOGIN_FAIL = "login_fail";
  public static final String LOGOUT_SUCCESS = "logout_success";
  public static final String UNAUTHORIZED = "unauthorized";

  // 회원
  public static final String SIGNUP_SUCCESS = "signup_success";
  public static final String EMAIL_DUPLICATE = "email_duplicated";
  public static final String NICKNAME_DUPLICATE = "nickname_duplicated";
  public static final String USER_NOT_FOUND = "user_not_found";

  // 게시글
  public static final String ARTICLE_CREATE_SUCCESS = "article_create_success";
  public static final String ARTICLE_UPDATE_SUCCESS = "article_update_success";
  public static final String ARTICLE_DELETE_SUCCESS = "article_delete_success";
  public static final String ARTICLE_NOT_FOUND = "article_not_found";

  // 권한
  public static final String FORBIDDEN = "forbidden";
  public static final String NO_UPDATE_PERMISSION = "no_update_permission";
  public static final String NO_DELETE_PERMISSION = "no_delete_permission";
}