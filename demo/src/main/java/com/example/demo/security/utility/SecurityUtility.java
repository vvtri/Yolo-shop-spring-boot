package com.example.demo.security.utility;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.constant.GlobalConstant;
import com.example.demo.security.dto.JwtBodyDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SecurityUtility {

  public static final Algorithm ALGORITHM = Algorithm.HMAC256(GlobalConstant.JWT_SECRET);
  public static final JWTVerifier JWT_VERIFIER = JWT.require(ALGORITHM).build();
  public static final String BEARER = "Bearer ";

  public String signToken(Long id) {
    String token = JWT.create().withClaim("userId", id)
        .withExpiresAt(new Date(System.currentTimeMillis() + GlobalConstant.JWT_EXPIRES_TIME_IN_MS)).sign(ALGORITHM);
    return token;
  }

  public Long verifyToken(String token) throws JWTVerificationException {
    DecodedJWT decodedJWT = JWT_VERIFIER.verify(token);
    Long id = decodedJWT.getClaim("userId").as(Long.class);
    return id;
  }

  public Long verifyToken(HttpServletRequest request) throws JWTVerificationException {
    String jwtToken = getTokenFromReq(request);
    return verifyToken(jwtToken);
  }

  public String getTokenFromReq(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken == null || bearerToken.startsWith(BEARER) == false)
      return null;
    return bearerToken.substring(BEARER.length());
  }

}
