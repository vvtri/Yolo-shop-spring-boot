package com.example.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public User findByEmail(String email);

  @Query("select u from User u join fetch u.userVerification where u.id = ?1")
  public User findByIdWithVerification(Long userId);

  @Query("select u from User u join fetch u.roles where u.id = ?1")
  public User findByIdWithRoles(Long userId);
}
