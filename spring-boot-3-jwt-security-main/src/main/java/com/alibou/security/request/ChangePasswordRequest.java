package com.alibou.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "Current password should not be empty")
    private String currentPassword;
    @NotBlank(message = "new password should not be empty")
    private String newPassword;
    @NotBlank(message = "Confirmation password should not be empty")
    private String confirmationPassword;
}
