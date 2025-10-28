package com.example.anyword.repository.articleImage;

import com.example.anyword.entity.ArticleImageEntity;
import com.example.anyword.repository.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class ArticleImageRepositoryImpl extends BaseRepository<ArticleImageEntity> implements ArticleImageRepository{
  public ArticleImageRepositoryImpl(){
    super();
  }


  /**
   * article 의 모든 이미지 list로
   */
  @Override
  public List<String> findByArticleId(Long articleId){
    return store.values()
        .stream()
        .filter(image -> image.getArticleId().equals(articleId))
        .map(ArticleImageEntity::getImageUrl)
        .toList();
  }

  /**
   * 특정 article의 모든 이미지 삭제
   */
  @Override
  public int deleteByArticleId(Long articleId) {
    List<Long> imageIds = store.values().stream()
        .filter(image -> image.getArticleId().equals(articleId))
        .map(ArticleImageEntity::getId)
        .toList();

    imageIds.forEach(store::remove);
    return imageIds.size();


  }
}

