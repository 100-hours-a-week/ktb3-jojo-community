package com.example.anyword.shared.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RowsUtil {
  private RowsUtil() {}
  public static Map<Long, Long> toIdCountMap(List<Object[]> rows) {
    return rows.stream().collect(Collectors.toMap(
        r -> (Long) r[0], r -> (Long) r[1]
    ));
  }
}