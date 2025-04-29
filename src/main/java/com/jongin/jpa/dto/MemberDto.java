package com.jongin.jpa.dto;

import com.jongin.jpa.entity.Comment;
import com.jongin.jpa.entity.Member;
import com.jongin.jpa.entity.Question;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private int no;

    @NotBlank(message = "아이디는 필수입력사항입니다.")
    private String userID;

    @NotBlank(message = "패스워드는 필수입력사항입니다.")
    private String userPW;

    @NotBlank(message = "이름은 필수입력사항입니다.")
    private String userName;

    @Email(message = "이메일 형식에 맞게 써주세요.")
    private String userEmail;

    private String tel;
    private String address01;
    private String address02;

    @Size(min = 5, max = 5, message = "5자리 숫자로 써주세요.")
    private String zipcode;

//    private MultipartFile profile;

    private String originalProfile; //db에 넣을거
    private String renameProfile;
    private String roles;
    private String regdate;
    private List<Question> questionList;
    private List<Comment> commentList;

    public static Member toEntity(MemberDto memberDto){
        return Member.builder()
                .userID(memberDto.getUserID())
                .userPW(memberDto.getUserPW())
                .userName(memberDto.getUserName())
                .userEmail(memberDto.getUserEmail())
                .address01(memberDto.getAddress01())
                .address02(memberDto.getAddress02())
                .zipcode(memberDto.getZipcode())
                .tel(memberDto.getTel())
                .roles(memberDto.getRoles())
                .build();
    }

}
