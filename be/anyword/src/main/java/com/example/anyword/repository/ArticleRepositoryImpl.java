package com.example.anyword.repository;


import com.example.anyword.entity.ArticleEntity;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryImpl extends BaseRepository<ArticleEntity> implements ArticleRepository {
  public ArticleRepositoryImpl(){
    super();
  }

  /**
   * 최신순 조회
   */
  @Override
  public List<ArticleEntity> findAllByOrderByCreatedAtDesc(int page, int size) {
    return findAll().stream()
        .sorted(Comparator.comparing(ArticleEntity::getCreatedAt).reversed())
        .skip((long) page * size)
        .limit(size)
        .collect(Collectors.toList());
  }

  /**
   * 조회순으로 게시글 목록 조회
   */
  @Override
  public List<ArticleEntity> findAllOrderByPopularity(int page, int size) {
    return findAll().stream()
        .sorted(Comparator.comparing(ArticleEntity::getCreatedAt).reversed())
        .skip((long) page * size)
        .limit(size)
        .collect(Collectors.toList());
  }


  /**
   * 전체 게시글 개수
   */
  @Override
  public long count() {
    return findAll().size();
  }
}
