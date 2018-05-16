package com.edu.csuf.app.daoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer.AmbiguousBindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.QueryAnswer;
import com.edu.csuf.app.model.Question;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.StudentCourseSemMap;
import com.edu.csuf.app.model.StudentQuery;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.repository.QueryAnswerRepository;
import com.edu.csuf.app.repository.QueryRepository;
import com.edu.csuf.app.repository.StudentCourseMappingRepository;
import com.edu.csuf.app.repository.UserRepository;
import com.edu.csuf.app.utility.Utilities;

@Service
public class QueryDataAccess {
	@Autowired
	private QueryRepository queryRepo;

	@Autowired
	private Utilities utility;

	@Autowired
	private StudentCourseMappingRepository scmRepo;

	@Autowired
	private QueryAnswerRepository qaRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Transactional
	public long saveQuery(JSONObject queryJson, int semId, long userId){
		StudentQuery sq = new StudentQuery();
		User user = new User();
		user.setUserId(userId);

		Semester sem = new Semester();
		sem.setSemId(semId);

		Course course = new Course();
		Assignment assignment = new Assignment();
		Question question = new Question();
		try {
			course.setCourseId(Integer.parseInt(queryJson.get("courseId").toString()));
			assignment.setAssignmentId(Integer.parseInt(queryJson.get("assignId").toString()));
			question.setQuestionId(Integer.parseInt(queryJson.get("quesId").toString()));

			sq.setUser(user);
			sq.setSem(sem);
			sq.setAssignment(assignment);
			sq.setQuestion(question);
			sq.setCourse(course);
			sq.setQuery(queryJson.get("query").toString());
			sq.setYear(utility.getCurrentYear());
			sq.setSubmittedDate(utility.currentDateTime());
			queryRepo.save(sq);
		} catch (NumberFormatException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sq.getQueryId();
	}

	@Transactional
	public List<StudentQuery> getQueries(int semId, int courseId, int assignId){
		List<StudentQuery> queryList = null;
		Semester sem = new Semester();
		sem.setSemId(semId);
		Course course = new Course();
		course.setCourseId(courseId);
		Assignment assign = new Assignment();
		assign.setAssignmentId(assignId);
		queryList = queryRepo.findBySemAndCourseAndAssignmentAndYear(sem, course, assign, utility.getCurrentYear());
		return queryList;
	}

	@Transactional
	public HashMap<Integer, String> getStudentEnrolledClasses(long userId, int semId){
		List<StudentCourseSemMap> courseSemList = null;
		HashMap<Integer, String> courseMap = new HashMap<>();
		User user = new User();
		user.setUserId(userId);
		Semester sem = new Semester();
		sem.setSemId(semId);
		courseSemList = scmRepo.findByUserAndSemAndYear(user, sem, utility.getCurrentYear());
		for(int i=0;i<courseSemList.size();i++){
			System.out.println(courseSemList.get(i).getCourse().getCourseName());
			courseMap.put(courseSemList.get(i).getCourse().getCourseId(), courseSemList.get(i).getCourse().getCourseCode());
		}
		return courseMap;
	}

	@Transactional
	public JSONArray getQueryAnswerforStudentQuery(StudentQuery studentquery){
		List<QueryAnswer> ansList = new ArrayList<>();
		ansList = qaRepo.findByStudentQuery(studentquery);
		JSONArray ansJsonArr = new JSONArray();
		try {
			/*if(ansList.size()>0){
				for(int i=0;i<ansList.size();i++){
					JSONObject ansJsonObj = new JSONObject();
					if(i==0){
						JSONObject query = new JSONObject();
						query.put("query", ansList.get(i).getStudentQuery().getQuery());
						query.put("queryId", ansList.get(i).getStudentQuery().getQueryId());
						ansJsonArr.put(query);
					} 
					ansJsonObj.put("answer",ansList.get(i).getQueryAnswer());
					ansJsonObj.put("name", ansList.get(i).getUser().getFirstName()+" "+ansList.get(i).getUser().getLastName());
					ansJsonObj.put("cwid", ansList.get(i).getUser().getCwid());
					ansJsonObj.put("date", ansList.get(i).getSubmittedDate());
					ansJsonArr.put(ansJsonObj);
				}
			}else{
				StudentQuery sq = queryRepo.findOne(studentquery.getQueryId());
				JSONObject ansJsonObj = new JSONObject();
				ansJsonObj.put("query", sq.getQuery());
				ansJsonObj.put("queryId", sq.getQueryId());
				ansJsonArr.put(ansJsonObj);
			}*/
			StudentQuery sq = queryRepo.findOne(studentquery.getQueryId());
			ansJsonArr.put(ansList);
			ansJsonArr.put(sq);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ansJsonArr;
	}
	
	@Transactional
	public QueryAnswer saveQueryAnswer(JSONObject ansQueryJson, long userId){
		QueryAnswer qa = new QueryAnswer();
		QueryAnswer newQA = null;
		try {
			qa.setQueryAnswer(ansQueryJson.get("queryAnswer").toString());
			StudentQuery sq = new StudentQuery();
			sq.setQueryId(Integer.parseInt(ansQueryJson.get("queryId").toString()));
			User user = new User();		
			user.setUserId(userId);
			
			qa.setStudentQuery(sq);
			qa.setUser(user);
			qa.setSubmittedDate(utility.currentDateTime());
			newQA = qaRepo.save(qa);
			newQA.setUser(userRepo.findOne(newQA.getUser().getUserId()));
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return newQA;
	}


}
