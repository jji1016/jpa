package com.jongin.jpa.controller;

import com.jongin.jpa.dto.CommentDto;
import com.jongin.jpa.dto.CustomUserDetails;
import com.jongin.jpa.entity.Comment;
import com.jongin.jpa.entity.Member;
import com.jongin.jpa.entity.Question;
import com.jongin.jpa.service.CommentService;
import com.jongin.jpa.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final QuestionService questionService;

    @PostMapping("/write/{id}")
    public String write(@PathVariable int id,
                        @RequestParam String content,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Question question = questionService.view(id);
        commentService.write(content,question,customUserDetails.getLoggedMember());
        return "redirect:/question/view/"+id;
    }

    @PostMapping("/write-ajax/{id}")
    @ResponseBody
    public Map<String, Object> writeAjax(@PathVariable int id,
                                         @RequestBody CommentDto commentDto,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Question question = questionService.view(id);
        Comment comment = commentService.write(commentDto.getContent(),question,customUserDetails.getLoggedMember());
        Map<String,Object> result = new HashMap<>();
        CommentDto responseCommentDto = CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(comment.getMember().getUserName())
                .regDate(comment.getRegDate())
                .build();
        result.put("comment",responseCommentDto);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id) {
        commentService.delete(id);
        Map<String,Object> result = new HashMap<>();
        result.put("isCommentDelete",true);
        return result;
    }
}
