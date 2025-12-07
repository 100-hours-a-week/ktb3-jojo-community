package com.example.anyword.security.customUserDetailsService;

import com.example.anyword.User.entity.UserEntity;
import com.example.anyword.User.repository.UserRepository;
import com.example.anyword.security.customUserDetails.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Primary
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));

    return new CustomUserDetails(user, List.of(new SimpleGrantedAuthority("ROLE_USER"))); //TODO: 임시, 나중에 권한 세부화
  }
}
