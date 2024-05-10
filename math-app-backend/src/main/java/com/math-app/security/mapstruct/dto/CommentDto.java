package com.alibou.security.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private Integer commentId;
    private String userName;
    private String commentBody;
    private LocalDateTime createDate;
    private double rating;

}
