package com.example.anyword.service;

import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.FORBIDDEN;
import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.dto.comment.CommentItemDto;
import com.example.anyword.dto.comment.CommentRequestDto;
import com.example.anyword.dto.comment.CreateCommentResponseDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.CommentEntity;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.mapper.CommentMapper;
import com.example.anyword.repository.article.ArticleRepository;
import com.example.anyword.repository.comment.CommentRepository;
import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.shared.exception.ForbiddenException;
import com.example.anyword.shared.exception.NotFoundException;
import com.example.anyword.shared.exception.UnauthorizedException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

  private final CommentMapper commentMapper;


  public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository,
      UserRepository userRepository,
      CommentMapper commentMapper) {
    this.commentRepository = commentRepository;
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.commentMapper = commentMapper;
  }


  private CommentEntity findComment(Long commentId){
    return commentRepository.findById(commentId).orElseThrow(()->
          new NotFoundException(COMMENT_NOT_FOUND));
  }

  private void checkUserIdwithAuthorId(Long userId, Long authorId){
    if (!authorId.equals(userId)) {
      throw new ForbiddenException(FORBIDDEN);
    }
  }

  private ArticleEntity findArticle(Long articleId){
    return articleRepository.findById(articleId).orElseThrow(()->new NotFoundException(ARTICLE_NOT_FOUND));
  }


  @Transactional
  public CommentItemDto createComment(Long articleId, Long userId, CommentRequestDto request) {
    ArticleEntity article = findArticle(articleId);
    UserEntity author = userRepository.findById(userId).orElseThrow(()-> new UnauthorizedException(USER_NOT_FOUND));
    CommentEntity saved = commentRepository.save(new CommentEntity(article, author, request.getContent()));

    return commentMapper.toItem(saved, userId);
  }

  @Transactional
  public CreateCommentResponseDto updateComment(Long commentId, Long userId, CommentRequestDto request) {
    CommentEntity comment = findComment(commentId);
    Long authorId = comment.getAuthor().getId();
    checkUserIdwithAuthorId(userId, authorId);

    CommentEntity updated = comment.returnUpdatedComment(request.getContent());
    CommentEntity saved = commentRepository.save(updated);

    return commentMapper.toResponse(saved);
  }


  @Transactional
  public void deleteComment(Long commentId, Long userId) {
    CommentEntity comment = findComment(commentId);
    Long authorId = comment.getAuthor().getId();
    checkUserIdwithAuthorId(userId, authorId);

    commentRepository.deleteById(commentId);
  }


  @Transactional(readOnly = true)
  public GetCommentListResponseDto getCommentsList(Long articleId, Long currentUserId) {
    findArticle(articleId);
    List<CommentEntity> comments = commentRepository.findAllByArticleIdOrderByCreatedAtAsc(articleId);
    return commentMapper.toListResponse(articleId, comments, currentUserId);
  }

}
