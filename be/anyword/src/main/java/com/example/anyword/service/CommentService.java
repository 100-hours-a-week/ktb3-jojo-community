package com.example.anyword.service;

import static com.example.anyword.shared.constants.ResponseMessage.ARTICLE_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.COMMENT_NOT_FOUND;
import static com.example.anyword.shared.constants.ResponseMessage.FORBIDDEN;

import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.dto.comment.CommentItem;
import com.example.anyword.dto.comment.CommentRequestDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.CommentEntity;
import com.example.anyword.repository.article.ArticleRepository;
import com.example.anyword.repository.comment.CommentRepository;
import com.example.anyword.shared.exception.ForbiddenException;
import com.example.anyword.shared.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;

  private final UserService userService;  //TODO: service 간 의존관계 ..? -> layer 나누기 고민


  public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository,
      UserService userService) {
    this.commentRepository = commentRepository;
    this.articleRepository = articleRepository;
    this.userService = userService;
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
  public CommentEntity createComment(Long articleId, Long userId, CommentRequestDto request) {
    findArticle(articleId);
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


  public GetCommentListResponseDto getCommentsList(Long articleId, Long currentUserId) {
    findArticle(articleId);
    List<CommentEntity> comments = commentRepository.findAllByArticleIdOrderByCreatedAtDesc(articleId);

    //entity -> dto로 변환
    List<CommentItem> items = comments.stream()
        .map(comment -> {
          AuthorInfo author = userService.UserIdToAuthorInfo(comment.getUserId());

          boolean editable = currentUserId != null && comment.getUserId().equals(currentUserId);

          return new CommentItem(
              comment.getId(),
              comment.getContents(),
              author,
              editable,
              comment.getCreatedAt()
          );
        })
        .toList();

    return new GetCommentListResponseDto(articleId, items);
  }

}
