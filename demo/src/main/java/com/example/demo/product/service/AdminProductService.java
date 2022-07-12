package com.example.demo.product.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.product.entity.Product;
import com.example.demo.product.repository.ProductRepository;

@Service
@Transactional
public class AdminProductService {

  @Autowired
  private ProductRepository productRepo;

  public Product createProduct(Product product) {
    product.setId(null);
    return productRepo.save(product);
  }

  public Product updateProduct(Product product) {
    if (product.getId() == null)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id not valid");
    return productRepo.save(product);
  }

  public void deleteProduct(Long productId) {
    productRepo.deleteById(productId);
  }
}
