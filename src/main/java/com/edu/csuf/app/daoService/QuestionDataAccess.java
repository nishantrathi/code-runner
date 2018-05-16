package com.edu.csuf.app.daoService;

import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Answer;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Question;
import com.edu.csuf.app.model.QuestionUserForm;
import com.edu.csuf.app.repository.QuestionRepository;
import com.edu.csuf.app.utility.CreateFile;
import com.edu.csuf.app.utility.Utilities;

@Service
public class QuestionDataAccess {

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private AnswerDataAccess ansDAO;

	@Autowired
	private Utilities utilities;
	
	@Autowired
	private AssignmentDataAccess assignDAO;
	
	@Transactional
	public void insertQuestion(Question ques){
		questionRepo.save(ques);
		//return ques.getQuestionId();
	}

	@Transactional
	public JSONObject getQuestionByAssignmentId(QuestionUserForm quesUser){
		System.out.println("------------Id-->"+quesUser.getQuestion().getAssign().getCourse().getCourseId());
		List<Question> qList = questionRepo.findByAssign(quesUser.getQuestion().getAssign());
		Assignment ass = assignDAO.getSpecificAssignment(quesUser.getQuestion().getAssign().getAssignmentId());
		JSONObject finalJsonObj = new JSONObject();
		try {
			JSONArray quesAnsJsonList = new JSONArray();
			for (Question question : qList) {
				JSONObject questionJsonObj = new JSONObject();
				JSONObject ansJsonObj = new JSONObject();

				Answer ans = ansDAO.findByQuestionAndAssignAndUser(question,question.getAssign(),quesUser.getUser());
				ansJsonObj.put("questionInfo", question.getQuestionDetail());
				ansJsonObj.put("questionNum", question.getQuestionNumber());
				if(ans==null){
					ansJsonObj.put("ans", "");
					ansJsonObj.put("comment", "");
					ansJsonObj.put("ansId", 0);
					ansJsonObj.put("binary", "");
				}else{
					if(!ans.getPath().isEmpty())
					{
						CreateFile cf = new CreateFile();
						ansJsonObj.put("ans", utilities.readAssignmentFile(ans.getPath()));
						ansJsonObj.put("binary", utilities.readAssignmentFile(ans.getPath()));
					}else{
						ansJsonObj.put("ans", "");
						ansJsonObj.put("binary", "");
					}
					ansJsonObj.put("comment", ans.getComment());
					ansJsonObj.put("ansId", ans.getAnswerId());
				}
				questionJsonObj.put(String.valueOf(question.getQuestionId()), ansJsonObj);
				quesAnsJsonList.put(questionJsonObj);
			}
			
			finalJsonObj.put("assignId",quesUser.getQuestion().getAssign().getAssignmentId());
			finalJsonObj.put("assignNumber", ass.getAssignmentNumber());
			finalJsonObj.put("courseId",quesUser.getQuestion().getAssign().getCourse().getCourseId());
			finalJsonObj.put("endDate",ass.getEndDate());
			finalJsonObj.put("userId", quesUser.getUser().getUserId());
			finalJsonObj.put("semId", quesUser.getUser().getUserId());
			finalJsonObj.put("arr", quesAnsJsonList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalJsonObj;
	}
}
