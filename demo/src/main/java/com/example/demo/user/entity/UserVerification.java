package com.example.demo.user.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.demo.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVerification  extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String secret;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date expiresAt;

  @Column(name = "user_id")
  private Long userId;

  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;
}
