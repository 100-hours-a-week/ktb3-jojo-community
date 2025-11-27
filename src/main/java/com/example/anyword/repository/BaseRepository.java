package com.example.anyword.repository;

import com.example.anyword.entity.BaseEntity;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseRepository<T extends BaseEntity<Long>> {
  protected final ConcurrentMap<Long, T> store;
  protected final AtomicLong sequence;

  public BaseRepository(){
    this.store = new ConcurrentHashMap<>();
    this.sequence = new AtomicLong(0L);
  }

  public T save(T entity){
    //post 시 할당
    if (entity.getId() == null) {
      long newId = sequence.incrementAndGet();
      entity.setId(newId);
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

  public boolean deleteById(Long id){
    if(store.containsKey(id)){
      store.remove(id);
      return true;
    }
    return false;
  }

}
