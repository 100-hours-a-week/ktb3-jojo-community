package com.example.anyword.service;

import static com.example.anyword.shared.constants.ResponseMessage.ALREADY_EXISTS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.LIKED_NOT_FOUND;

import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.article.ArticleRepository;
import com.example.anyword.repository.like.LikeArticleRepository;
import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.shared.exception.ConflictException;
import com.example.anyword.shared.exception.NotFoundException;
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

    UserEntity author = userRepository.nonOptionalFindById(userId);

    if (likeRepository.existsByArticleIdAndUserId(articleId, userId)) {
      throw new ConflictException(ALREADY_EXISTS);
    }

    LikeArticleEntity like = new LikeArticleEntity(article, author);
    likeRepository.save(like);
  }

  @Transactional
  public void removeLike(Long articleId, Long userId) {
    articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));

    boolean deleted = likeRepository.deleteByArticleIdAndUserId(articleId, userId);

    if (!deleted) {
      throw new NotFoundException(LIKED_NOT_FOUND);
    }
  }
}
