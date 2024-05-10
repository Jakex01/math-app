package com.alibou.security.auth;

import com.alibou.security.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
  @NotBlank
  private String firstname;
  @NotBlank
  private String lastname;
  @NotBlank
  private String email;
  @NotBlank
  private String password;

  private Role role;
  private boolean mfaEnabled;
}
