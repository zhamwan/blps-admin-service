package com.example.blpsadminservice.repositories;

import com.example.blpsadminservice.model.Test;
import com.example.blpsadminservice.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    @Query(value = "select description from test_result where test_id = :testId and :rate between left_bound and right_bound", nativeQuery = true)
    String getDescByTestAndBounds(@Param("testId") Long testId, @Param("rate") Integer rate);

    List<TestResult> findAllByTestId(Test test);
}
