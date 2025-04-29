package com.jongin.jpa.service;

import com.jongin.jpa.dto.QuestionDto;
import com.jongin.jpa.entity.Question;
import com.jongin.jpa.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public void write(QuestionDto questionDto) {
//        Question question = Question.builder()
//                .title(questionDto.getTitle())
//                .content(questionDto.getContent())
//                .build();
        Question question = QuestionDto.toEntity(questionDto);
        questionRepository.save(question);

    }

    public List<Question> list() {
        return questionRepository.findAll();
    }

    public Question view(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get(); //optionalQuestion.get()을 써줘야 Question 타입으로 변경됨, 아니면 Optional타입(디폴트)
        }
        return null;
    }

//    public Question modify(QuestionDto questionDto) {
//        //update 쿼리가 나가야 한다.
//        //jap방식의 save: 다를경우 insert문으로 나가고 같은경우 update문으로 나감
//        Optional<Question> optionalQuestion = questionRepository.findById(questionDto.getId());
//        if (optionalQuestion.isPresent()) {
//            return questionRepository.save(QuestionDto.toEntity(questionDto));
//        }
//        return null;
//    }

    @Transactional //@Service에서만 쓸 수 있는 어노테이션
    public Question modify(QuestionDto questionDto) {
        Question question = questionRepository.findById(questionDto.getId()).orElseThrow(); //일단 찾아서 setter로 바꿔치기
//        question.setTitle(questionDto.getTitle().trim());
//        question.setContent(questionDto.getContent().trim());
        question.changeContent(questionDto.getContent());
        question.changeTitle(questionDto.getTitle());
//        Optional<Question> optionalQuestion = questionRepository.findById(questionDto.getId());
//        optionalQuestion.ifPresent(question -> {
//            Question updateQuestion = optionalQuestion.get();
//            updateQuestion = QuestionDto.toEntity(questionDto);
//        });
        return question;
    }
}
