package com.example.anyword.service;

import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.FORBIDDEN;

import com.example.anyword.dto.comment.CommentRequestDto;
import com.example.anyword.entity.CommentEntity;
import com.example.anyword.repository.ArticleRepository;
import com.example.anyword.repository.CommentRepository;
import com.example.anyword.shared.exception.ForbiddenException;
import com.example.anyword.shared.exception.NotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;


  public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
    this.commentRepository = commentRepository;
    this.articleRepository = articleRepository;
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


  @Transactional
  public CommentEntity createComment(Long articleId, Long userId, CommentRequestDto request) {
    articleRepository.findById(articleId).orElseThrow(()->new NotFoundException(ARTICLE_NOT_FOUND));
    CommentEntity comment = new CommentEntity(articleId, userId, request.getContent());

    return commentRepository.save(comment);
  }

  public CommentEntity updateComment(Long commentId, Long userId, CommentRequestDto request) {
    CommentEntity comment = findComment(commentId);
    checkUserIdwithAuthorId(userId, comment.getUserId());

    comment.setContents(request.getContent()); //TODO:불변성 지켜서 수정해보기
    comment.setUpdatedAt(LocalDateTime.now());

    return commentRepository.save(comment);
  }


  public void deleteComment(Long commentId, Long userId) {
    CommentEntity comment = findComment(commentId);
    checkUserIdwithAuthorId(userId, comment.getUserId());

    commentRepository.deleteById(commentId);
  }







}
