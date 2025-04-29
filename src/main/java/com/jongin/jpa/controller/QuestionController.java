package com.jongin.jpa.controller;

import com.jongin.jpa.dto.CustomUserDetails;
import com.jongin.jpa.dto.CustomUserDetails2;
import com.jongin.jpa.dto.QuestionDto;
import com.jongin.jpa.entity.Question;
import com.jongin.jpa.service.QuestionService;
import jakarta.servlet.annotation.WebServlet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    public final QuestionService questionService;

    @GetMapping("/write")
    public String write() {
        return "question/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute QuestionDto questionDto,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        questionDto.setWriter(customUserDetails.getLoggedMember());
        questionService.write(questionDto);
        return "redirect:../question/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> list = questionService.list();
        model.addAttribute("list", list);
        return "question/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        //front에서 넘어오는거는 dto로 받아서 service로 던져주면 entity로 바꿔서 저장
        //db에서 조회된 entity는 dto로 바꿔서 front로 전달
        Question question = questionService.view(id);
        QuestionDto questionDto = null;
        if (question != null) {
//             questionDto = QuestionDto.builder()
//                    .id(question.getId())
//                    .title(question.getTitle())
//                    .content(question.getContent())
//                    .regDate(question.getRegDate())
//                    .build();
            questionDto = Question.toDto(question);
        }

        model.addAttribute("questionDto", questionDto);
        return "question/view";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, Model model) {
        System.out.println("inininin");
        Question question = questionService.view(id);
        QuestionDto questionDto = null;
        if (question != null) {
            questionDto = Question.toDto(question);
        }

        model.addAttribute("questionDto", questionDto);
        return "question/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute QuestionDto questionDto) {
        Question question = questionService.modify(questionDto);
        log.info("questionDto:::::::: "+ questionDto);
        if (question != null) {
            return "redirect:/question/list";
        }
        return "redirect:/question/modify/"+question.getId();
    }
}
