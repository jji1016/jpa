package com.jongin.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@ToString
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //fetch = FetchType.LAZY: 실제 사용할 때까지 Question 객체를 가져오지 않음 (지연 로딩)
    //fetch = FetchType.LAZY를 안쓰면 fetch = FetchType.EAGER가 디폴트로 미리 모든값을 가져옴(무거워짐)
    @JoinColumn(name="questionNum") //이름 설정안하면 question에다가 _id 붙임 -> question_id
    //@JoinColumn(name = "question_id"): 댓글 테이블에 들어갈 FK 컬럼 이름 설정
    @JsonIgnore //무한 참조를 막기위해
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer",referencedColumnName = "userID")
    private Member member;


    @Builder
    public Comment(Integer id, String content, Question question, Member member) {
        this.id = id;
        this.content = content;
        this.question = question;
        this.member = member;
    }

}
