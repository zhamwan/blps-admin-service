package com.example.blpsadminservice.model;

import com.example.blpsadminservice.model.ids.TestQuestionId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test_question")
@NoArgsConstructor
public class TestQuestion {
    @EmbeddedId
    private TestQuestionId id;

    @Column(name = "number")
    private Integer number;

    public TestQuestion(TestQuestionId id, Integer number) {
        this.id = id;
        this.number = number;
    }
}
