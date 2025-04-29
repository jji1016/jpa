package com.jongin.jpa.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ModalDto {
    private String title;
    private String content;
    private boolean isShow;
}
