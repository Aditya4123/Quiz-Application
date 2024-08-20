package com.aditya.question_service.dao;


import com.aditya.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Questiondao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    boolean existsById(Long id);

    void deleteById(Long id);


    @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Integer> findRandomQuestionsBYCategory(String category, int numQ);
}
