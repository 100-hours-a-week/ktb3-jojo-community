package com.example.anyword.service;

import static com.example.anyword.shared.constants.ResponseMessage.ALREADY_EXISTS;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.LIKED_NOT_FOUND;

import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.repository.article.ArticleRepository;
import com.example.anyword.repository.like.LikeArticleRepository;
import com.example.anyword.shared.exception.ConflictException;
import com.example.anyword.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

  private final LikeArticleRepository likeRepository;
  private final ArticleRepository articleRepository;

  public LikeService(LikeArticleRepository likeRepository, ArticleRepository articleRepository) {
    this.likeRepository = likeRepository;
    this.articleRepository = articleRepository;
  }

  @Transactional
  public boolean addLike(Long articleId, Long userId) {
    articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));

    if (likeRepository.existsByArticleIdAndUserId(articleId, userId)) {
      throw new ConflictException(ALREADY_EXISTS);
    }

    LikeArticleEntity like = new LikeArticleEntity(articleId, userId);
    likeRepository.save(like);
    return true;
  };

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
