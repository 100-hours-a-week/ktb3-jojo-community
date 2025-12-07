package com.example.anyword.LikeArticle.service;

import static com.example.anyword.shared.constants.ResponseMessage.ALREADY_EXISTS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.LIKED_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.Article.entity.ArticleEntity;
import com.example.anyword.LikeArticle.entity.LikeArticleEntity;
import com.example.anyword.User.entity.UserEntity;
import com.example.anyword.Article.repository.ArticleRepository;
import com.example.anyword.LikeArticle.repository.LikeArticleRepository;
import com.example.anyword.User.repository.UserRepository;
import com.example.anyword.shared.exception.ConflictException;
import com.example.anyword.shared.exception.NotFoundException;
import com.example.anyword.shared.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

  private final LikeArticleRepository likeRepository;
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

  public LikeService(LikeArticleRepository likeRepository, ArticleRepository articleRepository,
      UserRepository userRepository) {
    this.likeRepository = likeRepository;
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public void addLike(Long articleId, Long userId) {
    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));

    UserEntity me = userRepository.findById(userId).orElseThrow(()-> new UnauthorizedException(USER_NOT_FOUND));

    if (likeRepository.existsByArticleAndAuthor(article, me)) {
      throw new ConflictException(ALREADY_EXISTS);
    }

    LikeArticleEntity like = new LikeArticleEntity(article, me);
    likeRepository.save(like);
  }

  @Transactional
  public void removeLike(Long articleId, Long userId) {
    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));

    UserEntity me = userRepository.findById(userId).orElseThrow(()-> new UnauthorizedException(USER_NOT_FOUND));
    int deleteRow = likeRepository.deleteByIds(articleId, userId);

    boolean deleted = deleteRow == 1;

    if (!deleted) {
      throw new NotFoundException(LIKED_NOT_FOUND);
    }
  }
}
