package com.aditya.question_service.Service;


import com.aditya.question_service.dao.Questiondao;
import com.aditya.question_service.model.Question;
import com.aditya.question_service.model.QuestionWrapper;
import com.aditya.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    Questiondao questiondao;
    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            return new ResponseEntity<>(questiondao.findAll(), HttpStatus.OK);
            }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questiondao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> add(Question question) {
        questiondao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public String update(Question question) {
        if (questiondao.existsById(question.getId())) {
            questiondao.save(question);
            return "success";
        } else {
            return "question not found";
        }
    }

    @Transactional
    public String delete(Long id) {
        if (questiondao.existsById(id)) {
            questiondao.deleteById(id);
            return "success";
        } else {
            return "question not found";
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questiondao.findRandomQuestionsBYCategory(categoryName,numQuestions);
        return  new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : questionIds){
            questions.add(questiondao.findById(id).get());
        }
        for(Question question : questions){
            QuestionWrapper wrapper  = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestiontitle(question.getQuestiontitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());

            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;

        for(Response response : responses){
            Question question = questiondao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightanswer()))
                right++;
        }
        return  new ResponseEntity<>(right,HttpStatus.OK);
    }
}
