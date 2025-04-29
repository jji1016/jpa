package com.jongin.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jongin.jpa.dto.MemberDto;
import com.jongin.jpa.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="entity_member")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column(unique=true)
    private String userID;

    private String userPW;
    private String userName;

    private String userEmail;
    private String tel;
    private String address01;
    private String address02;
    private String zipcode;
//    private String profile;
//    private String originalProfile; //db에 넣을거
//    private String renameProfile;
    private String roles;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionList;

    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList;

    public static MemberDto toDto(Member member) {
        MemberDto memberDto = MemberDto.builder()
                .userID(member.getUserID())
                .userPW(member.getUserPW())
                .userName(member.getUserName())
                .userEmail(member.getUserEmail())
                .questionList(member.getQuestionList())
                .commentList(member.getCommentList())
                .tel(member.getTel())
                .address01(member.getAddress01())
                .address02(member.getAddress02())
                .zipcode(member.getZipcode())
                .roles(member.getRoles())
                .build();
        return memberDto;
    }
}
