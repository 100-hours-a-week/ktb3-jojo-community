package com.example.anyword.service;

import static com.example.anyword.service.utils.ArticleUtils.merge;
import static com.example.anyword.service.utils.ArticleUtils.validatePage;
import static com.example.anyword.service.utils.ArticleUtils.validatePageSize;
import static com.example.anyword.service.utils.ArticleUtils.validateSort;
import static com.example.anyword.shared.constants.PageConstants.SORT_POPULARITY;
import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.FORBIDDEN;

import com.example.anyword.dto.article.ArticleListItemDto;
import com.example.anyword.dto.article.ArticleStatusInfoDto;
import com.example.anyword.dto.article.AuthorInfoDto;
import com.example.anyword.dto.article.PageInfoDto;
import com.example.anyword.dto.article.response.GetArticleListResponseDto;
import com.example.anyword.dto.article.response.GetArticleResponseDto;
import com.example.anyword.dto.article.request.PostArticleRequestDto;
import com.example.anyword.dto.article.request.PutArticleRequestDto;
import com.example.anyword.dto.article.response.PostArticleResponseDto;
import com.example.anyword.dto.article.response.PutArticleResponseDto;
import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.ArticleImageEntity;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.mapper.ArticleMapper;
import com.example.anyword.repository.articleImage.ArticleImageRepository;
import com.example.anyword.repository.article.ArticleRepository;
import com.example.anyword.repository.comment.CommentRepository;
import com.example.anyword.repository.like.LikeArticleRepository;
import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.shared.exception.ForbiddenException;
import com.example.anyword.shared.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleImageRepository imageRepository;
  private final LikeArticleRepository likeRepository;
  private final CommentRepository commentRepository;

  private final UserRepository userRepository;
  private final ArticleMapper articleMapper;

  public ArticleService(ArticleRepository articleRepository, ArticleImageRepository imageRepository,
      LikeArticleRepository likeRepository, CommentRepository commentRepository,
      UserRepository userRepository, ArticleMapper articleMapper) {
    this.articleRepository = articleRepository;
    this.imageRepository = imageRepository;
    this.likeRepository = likeRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.articleMapper = articleMapper;
  }

  @Transactional
  public PostArticleResponseDto createArticle(Long userId, PostArticleRequestDto request){

    UserEntity author = userRepository.getReferenceById(userId);
    ArticleEntity article = new ArticleEntity(author, request.getTitle(), request.getContent());
    ArticleEntity saved = articleRepository.save(article);

    if (request.getImageUrls()!= null && !request.getImageUrls().isEmpty()){
      for (String imageUrl : request.getImageUrls()){
        ArticleImageEntity image = new ArticleImageEntity(saved, imageUrl);
        imageRepository.save(image);
      }
    }

    return articleMapper.toPostArticleResponse(saved);
  }

  @Transactional
  public PutArticleResponseDto putArticle(Long userId, Long articleId, PutArticleRequestDto request) {
    ArticleEntity article = findArticle(articleId);
    Long authorId = article.getAuthor().getId();

    if (!authorId.equals(userId)) {
      throw new ForbiddenException(FORBIDDEN);
    }

    String newTitle = merge(request.getTitle(), article.getTitle());
    String newContent = merge(request.getContents(), article.getContents());

    ArticleEntity updated = article.copyWith(newTitle, newContent);
    ArticleEntity saved = articleRepository.save(updated);

    if (request.getImageUrls()!=null) {

      imageRepository.deleteByArticleId(articleId);

      for (String imageUrl : request.getImageUrls()) {
        ArticleImageEntity image = new ArticleImageEntity(article, imageUrl);
        imageRepository.save(image);
      }

    }

    return articleMapper.toPutArticleResponse(saved);
  }

  @Transactional
  public void deleteArticle(Long userId, Long articleId) {

    ArticleEntity article = findArticle(articleId);
    Long authorId = article.getAuthor().getId();

    if (!authorId.equals(userId)) {
      throw new ForbiddenException(FORBIDDEN);
    }

    imageRepository.deleteByArticleId(articleId);
    articleRepository.deleteById(articleId);
  }

  public ArticleEntity findArticle(Long articleId){
    return articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));
  }


  @Transactional(readOnly = true)
  public GetArticleResponseDto getArticle(Long articleId, Long currentUserId) {
    ArticleEntity article = articleRepository.findById(articleId)
        .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND));

    article.incrementViews();

    UserEntity author = article.getAuthor();

    AuthorInfoDto authorInfoDto = AuthorInfoDto.from(author);

    long likesCount = likeRepository.countByArticleId(articleId);
    long commentsCount = commentRepository.countByArticleId(articleId);

    ArticleStatusInfoDto status = new ArticleStatusInfoDto(likesCount, commentsCount, article.getViewCnt());

    boolean likedByMe = (currentUserId != null)
        && likeRepository.existsByArticleAndAuthor(article, userRepository.getReferenceById(currentUserId));

    Long authorId = article.getAuthor().getId();
    boolean isMyContents = authorId.equals(currentUserId);

    return articleMapper.toGetArticleResponse(
        article, authorInfoDto, status, likedByMe, isMyContents
    );
  }


  /**
   * 게시글 목록 조회
   */
  @Transactional(readOnly = true)
  public GetArticleListResponseDto getArticleList(Integer currentPage, Integer pageSize, String sort) {
    int validPage = validatePage(currentPage);
    int validPageSize = validatePageSize(pageSize);
    String validSort = validateSort(sort);

    Pageable pageable = PageRequest.of(validPage - 1, pageSize, Sort.by(sort).descending());


    List<ArticleEntity> articles;
    if (SORT_POPULARITY.equals(validSort)) {
      articles = articleRepository.findAlByOrderByViewCntDesc(pageable);
    } else {
      articles = articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    List<ArticleListItemDto> items = articles.stream()
        .map(this::convertToListItemDto)
        .toList();

    long totalCount = articleRepository.count();
    boolean hasNext = (long) validPage * validPageSize < totalCount;

    PageInfoDto pageInfoDto = new PageInfoDto(
        hasNext ? validPage + 1 : null,
        hasNext,
        validPageSize
    );

    return articleMapper.toGetArticleListResponse(items, pageInfoDto);
  }

  private ArticleListItemDto convertToListItemDto(ArticleEntity article) {
    UserEntity author =  article.getAuthor();

    AuthorInfoDto authorInfoDto = new AuthorInfoDto(author.getId(), author.getNickname(),
        author.getProfileImageUrl());

    long likesCount = likeRepository.countByArticleId(article.getId());
    long commentsCount = commentRepository.countByArticleId(article.getId());

    ArticleStatusInfoDto status = new ArticleStatusInfoDto(likesCount, commentsCount, article.getViewCnt());

    return ArticleListItemDto.from(article, authorInfoDto, status);
  }

}
