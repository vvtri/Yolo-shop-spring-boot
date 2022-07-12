package com.example.demo.product.repository;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query(value = "select p from Product p where p.color = ?1 AND p.size = ?2")
  public List<Product> filterProductByColorSize(String color, String size);

  @Query(value = "select p from Product p where p.color = ?1")
  public List<Product> filterProductByColor(String color );

  @Query(value = "select p from Product p where p.size = ?1")
  public List<Product> filterProductBySize(String size );
}
