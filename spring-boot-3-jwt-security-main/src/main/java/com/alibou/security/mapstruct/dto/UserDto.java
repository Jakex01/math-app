package com.alibou.security.mapstruct.dto;

import com.alibou.security.entity.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {


    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean mfaEnabled;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    @Enumerated(EnumType.STRING)
    private Role role;
}
