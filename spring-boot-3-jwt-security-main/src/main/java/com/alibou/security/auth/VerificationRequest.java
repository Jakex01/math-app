package com.alibou.security.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String code;

}
