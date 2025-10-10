package com.example.anyword.repository;

import com.example.anyword.dto.UserDTO;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<UserDTO> {

  public UserRepository(){
    super();
  }

  public Optional<UserDTO> findByEmail(String email) {
    return findAll().stream()
        .filter(user -> user.getEmail().equals(email))
        .findFirst();
  }
}
