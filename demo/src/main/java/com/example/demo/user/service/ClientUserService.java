package com.example.demo.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.common.constant.GlobalConstant;
import com.example.demo.security.dto.JwtBodyDto;
import com.example.demo.security.utility.SecurityUtility;
import com.example.demo.user.dto.SigninDto;
import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.UserVerification;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.repository.UserVerificationRepository;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClientUserService {

  @Autowired
  private SecurityUtility securityUtility;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepo;
  @Autowired
  private UserVerificationRepository userVerificationRepo;

  public User getCurrentUser(HttpServletRequest httpServletRequest) {
    Long userId = (Long) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userRepo.findById(userId).get();
  }

  public void signup(SignupDto signupDto) {
    User existUser = userRepo.findByEmail(signupDto.getEmail());
    if (existUser != null)
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User eixsts");

    String secret = UUID.randomUUID().toString();

    UserVerification userVerification = UserVerification.builder()
        .secret(passwordEncoder.encode(secret))
        .expiresAt(new Date(System.currentTimeMillis() + GlobalConstant.EMAIL_LINK_EXPIRES_TIME_IN_MS))
        .build();

    User user = User.builder()
        .email(signupDto.getEmail())
        .password(passwordEncoder.encode(signupDto.getPassword()))
        .userVerification(userVerification)
        .build();

    userRepo.save(user);
  }

  public void signin(SigninDto signinDto, HttpServletResponse response)
      throws StreamWriteException, DatabindException, IOException {
    User user = userRepo.findByEmail(signinDto.getEmail());
    if (user == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

    if (passwordEncoder.matches(signinDto.getPassword(), user.getPassword()) == false)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password not match");

    if (user.getIsVerified() == false)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not verified");

    String token = securityUtility.signToken(user.getId());
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("access_token", token);

    response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
  }

}
