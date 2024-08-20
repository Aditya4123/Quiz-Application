package com.aditya.quiz_service.dao;


import com.aditya.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Quizdao extends JpaRepository<Quiz,Integer> {
}
