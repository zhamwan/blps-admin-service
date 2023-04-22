package com.example.blpsadminservice.model;

import com.example.blpsadminservice.model.dto.QuestionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "questions")
@NoArgsConstructor
public class Question {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    public static QuestionDTO toDto(Question question, List<String> answers) {
        return new QuestionDTO(question.text, answers);
    }

    public Question(String text) {
        this.text = text;
    }
}
