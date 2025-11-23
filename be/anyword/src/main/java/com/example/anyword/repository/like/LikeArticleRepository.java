package com.example.anyword.repository.like;

import com.example.anyword.entity.ArticleEntity;
import com.example.anyword.entity.LikeArticleEntity;
import com.example.anyword.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LikeArticleRepository extends JpaRepository<LikeArticleEntity, Long> {
  @Query("select count(l) from LikeArticleEntity l where l.article.id in (:article_id)")
  long countByArticleId(@Param("article_id") Long articleId);

  @Query("""
  select l.article.id, count(l)
  from LikeArticleEntity l
  where l.article.id in :articleIds
  group by l.article.id
""")
  List<Object[]> bulkCountByArticleId(@Param("articleIds") List<Long> articleId);
  boolean existsByArticleAndAuthor(ArticleEntity article, UserEntity author);

  @Modifying
  @Query("delete from LikeArticleEntity l where l.article.id = :articleId and l.author.id  = :authorId")
  int deleteByIds(@Param("articleId") Long articleId, @Param("authorId")  Long authorId);
}