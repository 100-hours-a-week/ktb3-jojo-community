package com.example.anyword.mapper;

import static com.example.anyword.shared.constants.ResponseMessage.USER_NOT_FOUND;

import com.example.anyword.dto.article.AuthorInfoDto;
import com.example.anyword.dto.comment.CommentItemDto;
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
  public CommentItemDto toItem(CommentEntity comment, Long currentUserId) {
    Long authorId = comment.getAuthor().getId();
    UserEntity author = userRepository.findById(authorId)
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

    AuthorInfoDto authorInfoDto = new AuthorInfoDto(
        author.getId(),
        author.getNickname(),
        author.getProfileImageUrl()
    );

    boolean editable = currentUserId != null && currentUserId.equals(authorId);

    return new CommentItemDto(
        comment.getId(),
        comment.getContents(),
        authorInfoDto,
        editable,
        comment.getCreatedAt()
    );
  }

  /**
   * CommentEntity 리스트 → CommentItem 리스트
   */
  @Override
  public List<CommentItemDto> toItems(List<CommentEntity> comments, Long currentUserId) {
    return comments.stream()
        .map(comment -> toItem(comment, currentUserId))
        .collect(Collectors.toList());
  }

  /**
   * commentEntity -> put/create DTO 로 변환
   */
  @Override
  public CreateCommentResponseDto toResponse(CommentEntity comment){
    Long articleId = comment.getArticle().getId();
    return new CreateCommentResponseDto(comment.getId(), articleId);
  }

  /**
   * 전체 응답 DTO로 변환
   */
  @Override
  public GetCommentListResponseDto toListResponse(Long articleId, List<CommentEntity> comments, Long currentUserId) {
    List<CommentItemDto> items = toItems(comments, currentUserId);
    return new GetCommentListResponseDto(articleId, items);
  }

}
