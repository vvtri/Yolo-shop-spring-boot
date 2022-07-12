package com.example.demo.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.product.dto.FilterProductDto;
import com.example.demo.product.entity.Product;
import com.example.demo.product.service.ClientProductService;
import com.example.demo.common.constant.RoutePrefix;

@RestController
@RequestMapping(RoutePrefix.CLIENT + "/product")
public class ClientProductController {

  @Autowired
  private ClientProductService clientProductService;

  @GetMapping("/")
  public List<Product> getProducts(@Valid @RequestBody FilterProductDto filterProductDto) {
    return clientProductService.getProducts(filterProductDto);
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable Long id) {
    return clientProductService.getProduct(id);
  }
}
