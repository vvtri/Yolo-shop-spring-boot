package com.example.demo.common.constant;

public class GlobalConstant {
  public static final String JWT_SECRET = "secret";
  public static final Integer JWT_EXPIRES_TIME_IN_MS = 3600000; // 1000ms * 60 * 60 = 1h
  public static final Integer EMAIL_LINK_EXPIRES_TIME_IN_MS = 21600000; // 1000ms * 60 * 60 * 6 = 21600000
}
