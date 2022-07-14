package com.example.demo.user.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.demo.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
 // Error when jackson convert bidirectional relationship to json
  // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class User extends BaseEntity  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isVerified = false;

  // Error when cascade with insertable false, updateable false
  // https://stackoverflow.com/questions/58996129/do-cascadetype-all-and-insertable-false-updatable-false-exclude-each-othe
  @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
  private UserVerification userVerification;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
  private UserResetPassword userResetPassword;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  private List<User> roles;
}
