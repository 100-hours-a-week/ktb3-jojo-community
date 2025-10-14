package com.example.anyword.shared.constants;

public class ResponseMessage {

  // 공통
  public static final String SUCCESS = "success";
  public static final String FAIL = "fail";
  public final static String ALREADY_EXISTS = "already_exists";

  // 인증
  public static final String LOGIN_SUCCESS = "login_success";
  public static final String LOGIN_FAIL = "login_fail";
  public static final String LOGOUT_SUCCESS = "logout_success";
  public static final String UNAUTHORIZED = "unauthorized";
  public static final String SESSION_EXPIRED = "session_expired";

  // 회원
  public static final String SIGNUP_SUCCESS = "signup_success";
  public static final String EMAIL_DUPLICATE = "email_duplicated";
  public static final String NICKNAME_DUPLICATE = "nickname_duplicated";
  public static final String USER_NOT_FOUND = "user_not_found";

  // 게시글
  public static final String ARTICLE_CREATE_SUCCESS = "article_create_success";
  public static final String ARTICLE_UPDATE_SUCCESS = "article_update_success";
  public static final String ARTICLE_DELETE_SUCCESS = "article_delete_success";
  public static final String ARTICLE_GET_SUCCESS = "article_get_success";
  public static final String ARTICLE_NOT_FOUND = "article_not_found";

  //좋아요
  public static final String LIKED_CONFLICT = "already_liked_by_me";
  public static final String LIKED_NOT_FOUND = "liked_not_found";

  //코멘트
  public static final String COMMENT_GET_SUCCESS = "comment_get_success";
  public static final String COMMENT_CREATED_SUCCESS = "comment_created_success";
  public static final String COMMENT_UPDATED_SUCCESS = "comment_updated_success";
  public static final String COMMENT_DELETE_SUCCESS = "comment_delete_success";
  public static final String COMMENT_NOT_FOUND = "comment_not_found";

  // 권한
  public static final String FORBIDDEN = "forbidden";
  public static final String NO_UPDATE_PERMISSION = "no_update_permission";
  public static final String NO_DELETE_PERMISSION = "no_delete_permission";
}