package com.alibou.security.entity;

import com.alibou.security.token.Token;
import com.alibou.security.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  @NotBlank(message = "First name is mandatory")
  private String firstname;
  @NotBlank(message = "Lastname is mandatory")
  private String lastname;
  @NotBlank(message = "Email is mandatory")
  @Email
  private String email;
  @NotBlank
  private String password;

  @Lob
  private byte[] profilePicture;

  private boolean mfaEnabled;
  private String secret;


  @CreatedDate
  @Column(
          nullable = false,
          updatable = false
  )
  private LocalDateTime createDate;
  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime lastModified;


  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
