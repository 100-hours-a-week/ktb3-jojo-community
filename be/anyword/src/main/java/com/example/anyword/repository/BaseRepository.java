package com.example.anyword.repository;

import com.example.anyword.entity.BaseEntity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BaseRepository<T extends BaseEntity<Long>> {
  protected final Map<Long, T> store;
  protected long sequence;

  public BaseRepository(){
    this.store = new LinkedHashMap<>();
    this.sequence = 0L;
  }

  public T save(T entity){
    //post 시 할당
    if (entity.getId() == null) {
      sequence++;
      entity.setId(sequence);
    }

    store.put(entity.getId(), entity);
    return entity;
  }

  public Optional<T> findById(Long id){
    return Optional.ofNullable(store.get(id));
  }

  public ArrayList<T> findAll(){
    return new ArrayList<>(store.values());
  }

}
