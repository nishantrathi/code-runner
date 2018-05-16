package com.edu.csuf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.csuf.app.model.QueryAnswer;
import com.edu.csuf.app.model.StudentQuery;

public interface QueryAnswerRepository extends JpaRepository<QueryAnswer, Long>{
	public List<QueryAnswer>findByStudentQuery(StudentQuery sq);

}
