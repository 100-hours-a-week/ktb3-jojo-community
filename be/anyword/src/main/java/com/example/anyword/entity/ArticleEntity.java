package com.example.anyword.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleEntity implements BaseEntity<Long> {
  @Id @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id; //PK

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false) @Setter
  private UserEntity author; //FK

  @Column(nullable = false) @Setter
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT") @Setter
  private String contents;

  @Column(nullable = false) @Setter
  private long viewCnt;

  @Column(nullable = false)
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @Column(nullable = false) @Setter
  @JsonFormat(timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "article")
  @BatchSize(size=20)
  private List<ArticleImageEntity> articleImageEntities = new ArrayList<>();

  public ArticleEntity(UserEntity author, String title, String contents){
    this.author = author;
    this.title = title;
    this.contents = contents;
    this.viewCnt = 0;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  //TODO: setter 사용이 최선?
  public ArticleEntity copyWith(String newTitle, String newContent) {
    this.setTitle(newTitle);
    this.setContents(newContent);

    return this;
  }

  public void incrementViews() {
    this.viewCnt++;
  }

}
