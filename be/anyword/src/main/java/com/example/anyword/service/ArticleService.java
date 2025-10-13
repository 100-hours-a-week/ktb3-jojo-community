package com.example.anyword.service;

import static com.example.anyword.service.utils.ArticleUtils.merge;
import static com.example.anyword.service.utils.ArticleUtils.validatePage;
import static com.example.anyword.service.utils.ArticleUtils.validatePageSize;
import static com.example.anyword.service.utils.ArticleUtils.validateSort;
import static com.example.anyword.shared.constants.PageConstants.SORT_POPULARITY;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.FORBIDDEN;
import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.dto.article.ArticleListItem;
import com.example.anyword.dto.article.ArticleStatusInfo;
import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.dto.article.PageInfo;
import com.example.anyword.dto.article.response.GetArticleListResponseDto;
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
      throw new ForbiddenException(FORBIDDEN);
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
      throw new ForbiddenException(FORBIDDEN);
    }

    imageRepository.deleteByArticleId(articleId);
    articleRepository.deleteById(articleId);
  }

  public ArticleEntity findArticle(Long articleId){
    return articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));
  }


  @Transactional
  public GetArticleResponseDto getArticle(Long articleId, Long currentUserId) {
    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));

    //TODO: 조회수 자동 갱신;

     UserEntity author = userRepository.findById(article.getUserId())
         .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

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


  /**
   * 게시글 목록 조회
   */
  public GetArticleListResponseDto getArticleList(Integer currentPage, Integer pageSize, String sort) {
    int validPage = validatePage(currentPage);
    int validPageSize = validatePageSize(pageSize);
    String validSort = validateSort(sort);


    List<ArticleEntity> articles;
    if (SORT_POPULARITY.equals(validSort)) {
      articles = articleRepository.findAllOrderByPopularity(validPage - 1, validPageSize);
    } else {
      articles = articleRepository.findAllByOrderByCreatedAtDesc(validPage - 1, validPageSize);
    }

    List<ArticleListItem> items = articles.stream()
        .map(this::convertToListItemDto)
        .toList();

    long totalCount = articleRepository.count();
    boolean hasNext = (long) validPage * validPageSize < totalCount;

    PageInfo pageInfo = new PageInfo(
        hasNext ? validPage + 1 : null,
        hasNext,
        validPageSize
    );

    return new GetArticleListResponseDto(items, pageInfo);
  }

  private ArticleListItem convertToListItemDto(ArticleEntity article) {
    UserEntity author = userRepository.findById(article.getUserId())
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

    AuthorInfo authorInfo = new AuthorInfo(author.getId(), author.getNickname(),
        author.getProfileImageUrl());

    long likesCount = likeRepository.countByArticleId(article.getId());
    long commentsCount = commentRepository.countByArticleId(article.getId());

    ArticleStatusInfo status = new ArticleStatusInfo(likesCount, commentsCount, article.getViewCnt());

    return ArticleListItem.from(article, authorInfo, status);
  }




}
