package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.INVALID_EMAIL;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Tables")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name" + REQUIRED_FIELD)
  private String name;

  @NotBlank(message = "title" + REQUIRED_FIELD)
  private String title;

  @NotEmpty(message = "roles" + REQUIRED_FIELD)
  private ArrayList<String> roles;

  @NotBlank(message = "email" + REQUIRED_FIELD)
  @Email(message = "email" + INVALID_EMAIL)
  private String email;

  @NotBlank(message = "password" + REQUIRED_FIELD)
  @Min(value = 8, message = "password should be at least 8 characters long")
  private String password;

  public User() {
  }

  public User(
      @NotBlank(message = "name" + REQUIRED_FIELD) String name,
      @NotBlank(message = "title"
          + REQUIRED_FIELD) String title,
      @NotEmpty(message = "roles"
          + REQUIRED_FIELD) ArrayList<String> roles,
      @NotBlank(message = "email"
          + REQUIRED_FIELD) @Email(message = "email"
          + INVALID_EMAIL) String email,
      @NotBlank(message = "password"
          + REQUIRED_FIELD) @Min(value = 8, message = "password should be at least 8 characters long") String password) {
    this.name = name;
    this.title = title;
    this.roles = roles;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArrayList<String> getRoles() {
    return roles;
  }

  public void setRoles(ArrayList<String> roles) {
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", title='" + title + '\'' +
        ", roles=" + roles +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
