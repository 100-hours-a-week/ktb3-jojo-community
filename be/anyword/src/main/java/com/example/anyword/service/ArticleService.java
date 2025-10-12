package com.example.anyword.service;

import com.example.anyword.dto.article.PostArticleRequestDto;
import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.ArticleImageEntity;
import com.example.anyword.repository.ArticleImageRepository;
import com.example.anyword.repository.ArticleRepository;
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

    if (request.getImageUrls()!= null && request.getImageUrls().isEmpty()){
      for (String imageUrl : request.getImageUrls()){
        ArticleImageEntity image = new ArticleImageEntity(saved.getId(), imageUrl);
        imageRepository.save(image);
      }
    }

    return saved.getId();
  }
}
