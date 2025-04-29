package com.jongin.jpa.dto;

import com.jongin.jpa.entity.Member;
import com.jongin.jpa.entity.Question;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private Question question;
    private Member member;
    private String writer;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
