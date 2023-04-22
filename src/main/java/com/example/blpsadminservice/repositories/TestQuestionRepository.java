package com.example.blpsadminservice.repositories;

import com.example.blpsadminservice.model.TestQuestion;
import com.example.blpsadminservice.model.ids.TestQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, TestQuestionId> {

    @Query(value = "select q_id from test_question where test_id = :testId and number = :qNumber", nativeQuery = true)
    Long getByTestIdAndNumber(@Param("testId") Long testId, @Param("qNumber") Integer qNumber);

    @Query(value = "select count(*) from test_question where test_id = :testId", nativeQuery = true)
    Integer countByTestId(@Param("testId") Long testId);

    @Query(value = "select * from test_question where test_id = :testId", nativeQuery = true)
    List<TestQuestion> getAllByTestId(@Param("testId")Long testId);
}
