package com.jongin.jpa.entity;

import com.jongin.jpa.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
//@Setter
@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class) //JPA의 **감사 기능(Auditing)**을 쓰기 위한 설정. 생성일/수정일 자동 세팅 가능하게 해줌.
//@Table(name = "table_question")
public class Question extends BaseEntity {
    @Id //이 필드가 **기본 키(PK)**라는 뜻.
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //시퀀스 기반으로 ID를 자동 생성해줘. Oracle에 잘 맞는 방식.
    private Integer id;
    private String title;
    private String content;

    //하나의 question에 여러개의 comment가 있을 수 있다. 1:N
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    //mappedBy = "question": 이건 주인이 아님을 의미 → FK는 댓글(Comment) 테이블에 있음
    //cascade = CascadeType.REMOVE: 질문 삭제 시 연결된 댓글들도 자동 삭제
    private List<Comment> commentList;
    //commentList에 값이 담기는 시점은 Question이 조회될때 (Question 객체가 호출되어 사용될때)

    @ManyToOne
    @JoinColumn(name = "userID",referencedColumnName = "userID")
    private Member writer;
    //@ManyToOne의 @JoinColumn은 외래키를 설정하는 역할을 하며, @OneToMany(mappedBy = "writer")는 외래키 설정을 반영하지 않고,
    //단지 관계의 반대편을 매핑하는 역할을 합니다. 이로 인해 Question 테이블은 userID라는 외래키를 갖게 되고, Member는 해당 userID를 기반으로 여러 Question을 참조할 수 있습니다.

    @Builder
    Question(Integer id ,String title, String content, Member writer, LocalDateTime regDate, LocalDateTime modifyDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modifyDate = modifyDate;
    }

    public static QuestionDto toDto(Question question){
        QuestionDto questionDto = QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .commentList(question.getCommentList())
                .regDate(question.getRegDate())
                .modifyDate(question.getModifyDate())
                .build();
        return questionDto;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

}
