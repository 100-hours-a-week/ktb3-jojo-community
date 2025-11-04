package com.example.anyword.aop;
import static com.example.anyword.shared.constants.ResponseMessage.UNAUTHORIZED;

import com.example.anyword.repository.user.UserRepository;
import com.example.anyword.shared.constants.Key;
import com.example.anyword.shared.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.Getter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * pointcut + 어드바이스
 */
@Aspect
@Component
public class AuthAspect {

  @Getter
  private final UserRepository userRepository;

  public AuthAspect(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  /**
   * 포인트컷
   */
  @Pointcut("@annotation(com.example.anyword.aop.Authable)")
  private void authableMethods(){}

  /**
   * 어드바이스
   */
  @Before("authableMethods()")
  public void ensureAuthenticated() {
    var reqAttr = RequestContextHolder.getRequestAttributes();
    if (!(reqAttr instanceof ServletRequestAttributes attrs)) {
      throw new UnauthorizedException(UNAUTHORIZED);
    }

    HttpSession session = attrs.getRequest().getSession(false);
    if (session == null) {
      throw new UnauthorizedException(UNAUTHORIZED);
    }

    Long userId = Optional.ofNullable((Long) session.getAttribute(Key.SESSION_USER_ID)).orElseThrow(()->
        new UnauthorizedException(UNAUTHORIZED));
  }
}
