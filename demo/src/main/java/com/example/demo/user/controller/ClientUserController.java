package com.example.demo.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.constant.RoutePrefix;
import com.example.demo.user.dto.SigninDto;
import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.ClientUserService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(RoutePrefix.CLIENT + "/user")
@Validated
public class ClientUserController {

  @Autowired
  private ClientUserService clientUserService;

  @GetMapping("/current")
  public User getCurrentUser(HttpServletRequest request) {
    return clientUserService.getCurrentUser(request);
  }

  @PostMapping("/signup")
  public void signup(@Valid @RequestBody SignupDto body) {
    clientUserService.signup(body);
  }

  @PostMapping("/signin")
  public void signin(@Valid @RequestBody SigninDto body, HttpServletResponse response)
      throws StreamWriteException, DatabindException, IOException {
    clientUserService.signin(body, response);
  }

  @GetMapping("/verify/{userId}")
  public User verifyUser(@PathVariable @Min(1) Long userId, @RequestParam @NotBlank String secret) {
      return clientUserService.verifyUser(userId, secret);
  }
}
