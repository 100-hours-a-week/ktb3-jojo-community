package com.example.anyword.service;


import com.example.anyword.dto.article.ArticleStatusInfo;
import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.dto.article.response.GetArticleResponseDto;
import com.example.anyword.dto.article.request.PostArticleRequestDto;
import com.example.anyword.dto.article.request.PutArticleRequestDto;
import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.ArticleImageEntity;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.ArticleImageRepository;
import com.example.anyword.repository.ArticleRepository;
import com.example.anyword.repository.CommentRepository;
import com.example.anyword.repository.LikeArticleRepository;
import com.example.anyword.repository.UserRepository;
import com.example.anyword.shared.constants.ResponseMessage;
import com.example.anyword.shared.exception.ForbiddenException;
import com.example.anyword.shared.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleImageRepository imageRepository;
  private final LikeArticleRepository likeRepository;
  private final CommentRepository commentRepository;

  private final UserRepository userRepository;


  public ArticleService(ArticleRepository articleRepository, ArticleImageRepository imageRepository,
      LikeArticleRepository likeRepository, CommentRepository commentRepository,
      UserRepository userRepository) {
    this.articleRepository = articleRepository;
    this.imageRepository = imageRepository;
    this.likeRepository = likeRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;

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
    ArticleEntity article = findArticle(articleId);

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

    ArticleEntity article = findArticle(articleId);

    if (!article.getUserId().equals(userId)) {
      throw new ForbiddenException(ResponseMessage.FORBIDDEN);
    }

    imageRepository.deleteByArticleId(articleId);
    articleRepository.deleteById(articleId);
  }

  public ArticleEntity findArticle(Long articleId){
    return articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ResponseMessage.ARTICLE_NOT_FOUND));
  }


  @Transactional
  public GetArticleResponseDto getArticle(Long articleId, Long currentUserId) {
    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ResponseMessage.ARTICLE_NOT_FOUND));

    //TODO: 조회수 자동 갱신;

     UserEntity author = userRepository.findById(article.getUserId())
         .orElseThrow(() -> new NotFoundException(ResponseMessage.USER_NOT_FOUND));

     AuthorInfo authorInfo = new AuthorInfo(author.getId(), author.getNickname(),
         author.getProfileImageUrl());

     long likesCount = likeRepository.countByArticleId(articleId);
     long commentsCount = commentRepository.countByArticleId(articleId);

    ArticleStatusInfo status = new ArticleStatusInfo(likesCount, commentsCount, article.getViewCnt());

    boolean likedByMe = (currentUserId != null)
        && likeRepository.existsByArticleIdAndUserId(articleId, currentUserId);

    boolean isMyContents = article.getUserId().equals(currentUserId);

    List<String> imageUrls = imageRepository.findByArticleId(articleId);

    //TODO: 다른 부분도 정적 팩토리 메서드 패턴으로 변경
    return GetArticleResponseDto.from(article, authorInfo, status, likedByMe, isMyContents, imageUrls);
  }


  private <T> T merge(T newValue, T originalValue) {
    return newValue != null ? newValue : originalValue;
  }
}
