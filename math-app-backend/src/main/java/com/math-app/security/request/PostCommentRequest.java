package com.alibou.security.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostCommentRequest {

    @NotNull(message = "rating cannot be null")
    private double rating;
    @NotBlank(message = "Comment body can't be blank")
    private String body;
    @NotNull(message = "Exercise id can't be null")
    private Long exerciseId;
}
