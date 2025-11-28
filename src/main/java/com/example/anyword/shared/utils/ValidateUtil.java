package com.example.anyword.shared.utils;

import com.example.anyword.shared.exception.ConflictException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ValidateUtil {
  public static void validateDuplicateField(
      String newValue,
      String originalValue,
      Predicate<String> existsFunc,
      String errorMessage
  ) {
    if (newValue != null
        && !Objects.equals(newValue, originalValue)
        && existsFunc.test(newValue)) {
      throw new ConflictException(errorMessage);
    }
  }
}
