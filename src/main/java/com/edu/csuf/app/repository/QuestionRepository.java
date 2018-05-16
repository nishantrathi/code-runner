package com.edu.csuf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	List<Question> findByAssign(Assignment a);
}
