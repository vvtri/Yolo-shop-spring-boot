package com.example.demo.product.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.constant.RoutePrefix;
import com.example.demo.product.entity.Product;
import com.example.demo.product.service.AdminProductService;

@RestController
@RequestMapping(RoutePrefix.ADMIN + "/product")
public class AdminProductController {
  @Autowired
  private AdminProductService adminProductService;

  @PostMapping("/create")
  public Product createProduct(@Valid @RequestBody Product product) {
    return adminProductService.createProduct(product);
  }

  @PatchMapping("/update")
  public Product updateProduct(@Valid @RequestBody Product product) {
    return adminProductService.updateProduct(product);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteProduct(@PathVariable Long id) {
    adminProductService.deleteProduct(id);
  }
}
