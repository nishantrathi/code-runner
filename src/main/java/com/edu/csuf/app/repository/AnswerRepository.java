package com.edu.csuf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edu.csuf.app.model.Answer;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Question;
import com.edu.csuf.app.model.User;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
	
	public Answer findByQuestionAndAssignmentAndUser(Question ques, Assignment ass, User user);
	
	@Modifying
	@Query("update Answer a set a.submittedOn = now() where a.answerId = ?1")
	public void updateSubmittedDate(long answerId);

	@Modifying
	@Query("update Answer a set a.comment =?1 where a.answerId = ?2")
	public int updateCommentForAnswer(String comment, long answerId);
	
	public Answer findByAnswerId(long ansId);
}
