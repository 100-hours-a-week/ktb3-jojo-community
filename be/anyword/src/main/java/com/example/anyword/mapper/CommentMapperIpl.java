package com.example.anyword.mapper;

import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.dto.article.AuthorInfo;
import com.example.anyword.dto.comment.CommentItem;
import com.example.anyword.dto.comment.CreateCommentResponseDto;
import com.example.anyword.dto.comment.GetCommentListResponseDto;
import com.example.anyword.entity.CommentEntity;
import com.example.anyword.entity.UserEntity;
import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.shared.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperIpl implements CommentMapper {
  private final UserRepository userRepository;

  public CommentMapperIpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * CommentEntity → CommentItem
   */
  @Override
  public CommentItem toItem(CommentEntity comment, Long currentUserId) {
    UserEntity author = userRepository.findById(comment.getUserId())
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

    AuthorInfo authorInfo = new AuthorInfo(
        author.getId(),
        author.getNickname(),
        author.getProfileImageUrl()
    );

    boolean editable = currentUserId != null && currentUserId.equals(comment.getUserId());

    return new CommentItem(
        comment.getId(),
        comment.getContents(),
        authorInfo,
        editable,
        comment.getCreatedAt()
    );
  }

  /**
   * CommentEntity 리스트 → CommentItem 리스트
   */
  @Override
  public List<CommentItem> toItems(List<CommentEntity> comments, Long currentUserId) {
    return comments.stream()
        .map(comment -> toItem(comment, currentUserId))
        .collect(Collectors.toList());
  }

  /**
   * commentEntity -> put/create DTO 로 변환
   */
  @Override
  public CreateCommentResponseDto toResponse(CommentEntity comment){
    return new CreateCommentResponseDto(comment.getId(), comment.getArticleId());
  }

  /**
   * 전체 응답 DTO로 변환
   */
  @Override
  public GetCommentListResponseDto toListResponse(Long articleId, List<CommentEntity> comments, Long currentUserId) {
    List<CommentItem> items = toItems(comments, currentUserId);
    return new GetCommentListResponseDto(articleId, items);
  }

}
