package io.catalyte.training.entities;

import static io.catalyte.training.constants.StringConstants.INVALID_EMAIL;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Template for User entity, and everything it needs to be loaded properly into a database Contains
 * fields, constructors, getters/setters, toString(), equals(), hashCode(), and isEmpty()
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "name" + REQUIRED_FIELD)
  private String name;

  @NotBlank(message = "title" + REQUIRED_FIELD)
  private String title;

  @NotEmpty(message = "roles" + REQUIRED_FIELD)
  private String[] roles;

  @NotBlank(message = "email" + REQUIRED_FIELD)
  @Email(message = "email" + INVALID_EMAIL)
  private String email;

  @NotBlank(message = "password" + REQUIRED_FIELD)
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

  public User() {
  }

  public User(
      @NotBlank(message = "name" + REQUIRED_FIELD) String name,
      @NotBlank(message = "title"
          + REQUIRED_FIELD) String title,
      @NotEmpty(message = "roles"
          + REQUIRED_FIELD) String[] roles,
      @NotBlank(message = "email"
          + REQUIRED_FIELD) @Email(message = "email"
          + INVALID_EMAIL) String email,
      @NotBlank(message = "password"
          + REQUIRED_FIELD) @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
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

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
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
        ", roles=" + Arrays.toString(roles) +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(name, user.name) &&
        Objects.equals(title, user.title) &&
        Arrays.equals(roles, user.roles) &&
        Objects.equals(email, user.email) &&
        Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, name, title, email, password);
    result = 31 * result + Arrays.hashCode(roles);
    return result;
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(title) &&
        Objects.isNull(roles) &&
        Objects.isNull(email) &&
        Objects.isNull(password);
  }

}
