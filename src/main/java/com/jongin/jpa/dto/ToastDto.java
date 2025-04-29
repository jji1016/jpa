package com.jongin.jpa.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToastDto {
    private String title;
    private String content;
    private boolean isShow;
}
