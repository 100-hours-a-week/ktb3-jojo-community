package com.example.anyword.repository;

import com.example.anyword.entity.ArticleImageEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleImageRepository extends BaseRepository<ArticleImageEntity>{
  public ArticleImageRepository(){
    super();
  }


  /**
   * article 의 모든 이미지 list로
   */
  public List<ArticleImageEntity> findByArticleId(Long articleId){
    return store.values()
        .stream()
        .filter(image -> image.getArticleId().equals(articleId))
        .collect(Collectors.toList());
  }

  /**
   * 특정 article의 모든 이미지 삭제
   */
  public int deleteByArticleId(Long articleId) {
    List<Long> imageIds = store.values().stream()
        .filter(image -> image.getArticleId().equals(articleId))
        .map(ArticleImageEntity::getId)
        .toList();

    imageIds.forEach(store::remove);
    return imageIds.size();


  }
}
