package com.example.demo.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.product.dto.FilterProductDto;
import com.example.demo.product.entity.Product;
import com.example.demo.product.repository.ProductRepository;

@Service
@Transactional
public class ClientProductService {
  @Autowired
  private ProductRepository productRepo;

  public List<Product> getProducts(FilterProductDto dto) {
    String color = dto.getColor();
    String size = dto.getSize();

    if (!color.isEmpty() && !size.isEmpty()) {
      return productRepo.filterProductByColorSize(color, size);
    } else if (!color.isEmpty()) {
      return productRepo.filterProductByColor(color);
    } else if (!size.isEmpty()) {
      return productRepo.filterProductBySize(size);
    } else {
      return productRepo.findAll();
    }
  }

  public Product getProduct(Long id) {
    Optional<Product> result = productRepo.findById(id);
    if (result.isPresent() == false)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product with id: " + id + " not found");
    return result.get();
  }
}
