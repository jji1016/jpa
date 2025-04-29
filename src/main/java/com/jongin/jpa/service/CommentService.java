package com.jongin.jpa.service;

import com.jongin.jpa.entity.Comment;
import com.jongin.jpa.entity.Member;
import com.jongin.jpa.entity.Question;
import com.jongin.jpa.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public Comment write(String content, Question question, Member member) {
        Comment comment = Comment.builder()
                .content(content)
                .question(question)
                .member(member)
                .build();
        return commentRepository.save(comment); //entity 타입을 리턴
    }

    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
