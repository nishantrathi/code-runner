package com.edu.csuf.app.daoService;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Answer;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.Question;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.model.UserAssignmentMapping;
import com.edu.csuf.app.repository.AnswerRepository;
import com.edu.csuf.app.utility.CreateFile;
import com.edu.csuf.app.utility.Utilities;

@Service
public class AnswerDataAccess {

	@Autowired
	private AnswerRepository ansRepo;
	@Autowired
	private Utilities utilities;
	@Autowired
	private MiscellaneousDataAccess miscDAO;

	@Transactional
	public long saveAnswer(JSONObject ans, long uId, int sem){
		long id = 0;
		try{
			CreateFile cf = new CreateFile();
			StringBuilder sb = new StringBuilder();
			String halfPath =utilities.createPath(utilities.getCurrentYear(),sem , uId,Integer.parseInt(ans.get("course.courseId").toString()));
			sb.append(halfPath);
			sb.append("\\");
			sb.append(ans.get("assignment.assignmentId").toString());
			String assignFolder = sb.toString();
			utilities.createFolder(sb.toString());
			sb.append("\\");
			String fileName = "ques_"+ans.get("question.questionId").toString()+"_ans";
			sb.append(fileName+".cpp");
			String path = utilities.createAssignmentFile((String)ans.get("binary"),sb.toString());
			Assignment ass = new Assignment();
			ass.setAssignmentId(Integer.parseInt(ans.get("assignment.assignmentId").toString()));

			Question ques = new Question();
			ques.setQuestionId(Integer.parseInt(ans.get("question.questionId").toString()));

			User user = new User();
			user.setUserId(uId);

			Answer answer = new Answer();
			answer.setAssignment(ass);
			answer.setQuestion(ques);
			answer.setUser(user);
			answer.setSubmittedOn(utilities.generateTodaysDate());
			answer.setPath(path);
			id = ansRepo.save(answer).getAnswerId();

			if(id>0){
				int year = utilities.getCurrentYear();
				UserAssignmentMapping uam = null;
				Course c = new Course();
				c.setCourseId(Integer.parseInt(ans.get("course.courseId").toString()));
				Semester s = new Semester();
				s.setSemId(sem);
				System.out.println("user assignment mapping 0");
				List<UserAssignmentMapping>uamList = miscDAO.findAssignmentRecord(user, s, c,ass, year);
				if(uamList.size()==0){
					System.out.println("user assignment mapping 1");
					uam = new UserAssignmentMapping();
					uam.setSem(s);
					uam.setCourse(c);
					uam.setUser(user);
					uam.setAssignment(ass);
					uam.setYear(year);
					uam.setFinalSubmittedDate(utilities.currentDateTime());
					miscDAO.insertUserAssignmentMapping(uam);
				}else{
					miscDAO.updateFinalSubmittedDate(utilities.currentDateTime(), uamList.get(0).getAssignment());
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Transactional
	public Answer findByQuestionAndAssignAndUser(Question ques, Assignment ass, User user){
		return ansRepo.findByQuestionAndAssignmentAndUser(ques, ass,  user);
	}

	@Transactional
	public long execute(JSONObject ans, long uId, int sem){
		long id=0;
		try {
			id = Long.parseLong(ans.get("answerId").toString());
			StringBuilder sb = new StringBuilder();
			String halfPath =utilities.createPath(utilities.getCurrentYear(),sem , uId,Integer.parseInt(ans.get("course.courseId").toString()));
			sb.append(halfPath);
			sb.append("\\");
			sb.append(ans.get("assignment.assignmentId").toString());
			String assignFolder = sb.toString();
			sb.append("\\");
			String fileName = "ques_"+ans.get("question.questionId").toString()+"_ans";
			sb.append(fileName+".cpp");
			if(utilities.fileExistsThenDelete(sb.toString())){
				String path = utilities.createAssignmentFile((String)ans.get("binary"),sb.toString());
				ansRepo.updateSubmittedDate(id);
			}

		} catch (NumberFormatException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public String executeCode(JSONObject ans, long uId, int sem){
		JSONArray output = new JSONArray();
		try 
		{
			StringBuilder sb = new StringBuilder();
			String halfPath =utilities.createPath(utilities.getCurrentYear(),sem , uId,Integer.parseInt(ans.get("course.courseId").toString()));
			sb.append(halfPath);
			sb.append("\\");
			sb.append(ans.get("assignment.assignmentId").toString());
			String assignFolder = sb.toString();
			String fileName = "ques_"+ans.get("question.questionId").toString()+"_ans";

			StringBuilder sbCommand = new StringBuilder();
			sbCommand.append("cd \""+assignFolder+"\"");
			sbCommand.append(" && g++ -o "+fileName+" "+fileName+".cpp");
			sbCommand.append(" && "+fileName);
			//String command =  "cd \"E:\\codeRunner\\assignments\\2018\\3\\1\\1\\1\" && g++ -o nishant ques_1_ans.cpp && nishant";
			System.out.println("command -->"+sbCommand.toString());
			output= utilities.executeCppCode(sbCommand.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.toString();
	}

	@Transactional
	public int saveComment(String comment, long answerId)
	{
		return ansRepo.updateCommentForAnswer(comment, answerId);
	}

	@Transactional
	public JSONArray executeAssignmentForProf(JSONObject studentAssignJson, int sem){
		JSONArray outputArray = new JSONArray();
		try {
			/*StringBuilder sb = new StringBuilder();
			String halfPath =utilities.createPath(utilities.getCurrentYear(),sem , Long.parseLong(studentAssignJson.get("user.userId").toString()),Integer.parseInt(studentAssignJson.get("course.courseId").toString()));
			sb.append(halfPath);
			sb.append("\\");
			sb.append(studentAssignJson.get("assignment.assignmentId").toString());
			String assignFolder = sb.toString();
			String fileName = "ques_"+studentAssignJson.get("question.questionId").toString()+"_ans";
			StringBuilder sbCommand = new StringBuilder();
			sbCommand.append("cd \""+assignFolder+"\"");
			sbCommand.append(" && g++ -o "+fileName+" "+fileName+".cpp");
			sbCommand.append(" && "+fileName);
			System.out.println("command -->"+sbCommand.toString());*/
			Answer ans = ansRepo.findByAnswerId(Long.parseLong(studentAssignJson.get("answerId").toString()));
			String fileName = "ques_"+ans.getQuestion().getQuestionId()+"_ans";
			System.out.println(ans.getPath());
			String filePath[] = ans.getPath().split("\\\\");
			StringBuilder path = new StringBuilder();
			for(int i=0;i<filePath.length-1;i++){
				path.append(filePath[i]);
				path.append("\\");
			}
			System.out.println(path.toString());
			StringBuilder sbCommand = new StringBuilder();
			sbCommand.append("cd \""+path.toString()+"\"");
			sbCommand.append(" && g++ -o "+fileName+" "+fileName+".cpp");
			sbCommand.append(" && "+fileName);
			outputArray= utilities.executeCppCode(sbCommand.toString());
			
		} catch (NumberFormatException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputArray;

	}
}
