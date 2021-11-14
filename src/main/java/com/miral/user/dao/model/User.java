package com.miral.user.dao.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.Min;
import org.hibernate.annotations.NaturalId;

@Entity
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @NaturalId
  @Column(unique = true)
  @Min(6)
  private String username;

  private String password;

  protected User() {

  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
