package com.example.anyword.service;

import com.example.anyword.dto.article.PostArticleRequestDto;
import com.example.anyword.dto.article.PutArticleRequestDto;
import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.ArticleImageEntity;
import com.example.anyword.repository.ArticleImageRepository;
import com.example.anyword.repository.ArticleRepository;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.shared.exception.ForbiddenException;
import com.example.anyword.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleImageRepository imageRepository;


  public ArticleService(ArticleRepository articleRepository, ArticleImageRepository imageRepository) {
    this.articleRepository = articleRepository;
    this.imageRepository = imageRepository;
  }

  @Transactional
  public Long createArticle(Long userId, PostArticleRequestDto request){
    ArticleEntity article = request.toEntity(userId);
    ArticleEntity saved = articleRepository.save(article);

    if (request.getImageUrls()!= null && !request.getImageUrls().isEmpty()){
      for (String imageUrl : request.getImageUrls()){
        ArticleImageEntity image = new ArticleImageEntity(saved.getId(), imageUrl);
        imageRepository.save(image);
      }
    }

    return saved.getId();
  }

  @Transactional
  public Long putArticle(Long userId, Long articleId, PutArticleRequestDto request) {
    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ResponseMessage.ARTICLE_NOT_FOUND));

    if (!article.getUserId().equals(userId)) {
      throw new ForbiddenException(ResponseMessage.FORBIDDEN);
    }

    String newTitle = merge(request.getTitle(), article.getTitle());
    String newContent = merge(request.getContents(), article.getContents());

    ArticleEntity updated = ArticleEntity.copyWith(article, newTitle, newContent);
    ArticleEntity saved = articleRepository.save(updated);

    if (request.getImageUrls()!=null) {

      imageRepository.deleteByArticleId(articleId);

      for (String imageUrl : request.getImageUrls()) {
        ArticleImageEntity image = new ArticleImageEntity(articleId, imageUrl);
        imageRepository.save(image);
      }

    }

    return saved.getId();
  }

  @Transactional
  public void deleteArticle(Long userId, Long articleId) {

    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ResponseMessage.ARTICLE_NOT_FOUND));


    if (!article.getUserId().equals(userId)) {
      throw new ForbiddenException(ResponseMessage.FORBIDDEN);
    }

    imageRepository.deleteByArticleId(articleId);
    articleRepository.deleteById(articleId);
  }



  private <T> T merge(T newValue, T originalValue) {
    return newValue != null ? newValue : originalValue;
  }
}
