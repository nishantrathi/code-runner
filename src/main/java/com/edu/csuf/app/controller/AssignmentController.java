package com.edu.csuf.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.csuf.app.daoService.AnswerDataAccess;
import com.edu.csuf.app.daoService.AssignmentDataAccess;
import com.edu.csuf.app.daoService.CourseDataAccess;
import com.edu.csuf.app.daoService.MiscellaneousDataAccess;
import com.edu.csuf.app.daoService.QueryDataAccess;
import com.edu.csuf.app.daoService.QuestionDataAccess;
import com.edu.csuf.app.model.Answer;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.CourseAssignmentQuestionForm;
import com.edu.csuf.app.model.Question;
import com.edu.csuf.app.model.QuestionUserForm;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.StudentCourseSemMap;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.model.UserAssignmentMapping;
import com.edu.csuf.app.utility.Utilities;

@Controller
@RequestMapping(value="/assignment")
public class AssignmentController {
	@Autowired
	private CourseDataAccess courseDAO;
	@Autowired
	private AssignmentDataAccess assignDAO;
	@Autowired
	private QuestionDataAccess quesDAO;
	@Autowired
	private AnswerDataAccess ansDAO;
	@Autowired
	private Utilities utilities;
	@Autowired
	private MiscellaneousDataAccess miscDAO;
	@Autowired
	private QueryDataAccess queryDAO;

	@RequestMapping(value="/add")
	public String redirectOnAssignmentPage(Model model){
		CourseAssignmentQuestionForm objCAQForm= new CourseAssignmentQuestionForm();
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("caqForm",objCAQForm);
		return "addAssignment";
	}

	@RequestMapping(value="/addAssign")
	public String insertAssignment(@Valid @ModelAttribute("caqForm") CourseAssignmentQuestionForm objCAQForm, BindingResult bindingResult,Model model, HttpServletRequest request){
		System.out.println(objCAQForm.getAssignment().getEndDate()+"-->>>");
		Assignment assign = objCAQForm.getAssignment();
		int year = utilities.getCurrentYear();
		long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
		User user = new User();
		user.setUserId(userId);
		Semester sem = new Semester();
		sem.setSemId(semId);

		assign.setSem(sem);
		assign.setUser(user);
		assign.setCourse(objCAQForm.getCourse());
		assign.setYear(year);
		
		String enddate = assign.getEndDate();

		int aId = assignDAO.insertAssignment(assign);
		System.out.println("aid-->"+aId);
		System.out.println("----3");
		objCAQForm.getAssignment().setAssignmentId(aId);
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		objCAQForm.getAssignment().setEndDate(enddate);
		model.addAttribute("acForm",objCAQForm);
		return "addAssignment";
	}

	@RequestMapping(value="/addQuestion")
	public String insertQuestion(@Valid @ModelAttribute("caqForm") CourseAssignmentQuestionForm objCAQForm, Model model){
		Question ques = objCAQForm.getQues();
		ques.setAssign(objCAQForm.getAssignment());
		quesDAO.insertQuestion(ques);
		/*System.out.println("----"+objCAQForm.getCourse().getCourseId());
		System.out.println("-----"+objCAQForm.getAssignment().getAssignmentNumber());*/
		objCAQForm.setQues(null);
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("acForm",objCAQForm);
		return "addAssignment";
	}

	//Get assignment url
	@RequestMapping(value="/findAssign", method=RequestMethod.POST)
	public ModelAndView findAssignmentByCourse(@ModelAttribute("assignModelForm") Assignment assignObj){
		System.out.println("from assin obj-->"+assignObj.getCourse().getCourseId());
		List<Assignment> assignList =assignDAO.getAssignmentByCourse(assignObj);
		ModelAndView mav = new ModelAndView();
		mav.addObject("assignmentList",assignList);
		QuestionUserForm quesUserForm = new QuestionUserForm();
		mav.addObject("questionModelForm", quesUserForm);
		mav.addObject("courId", assignObj.getCourse().getCourseId());
		mav.setViewName("assignmentPage");
		return mav;
	}

	@RequestMapping(value="/findQues", method=RequestMethod.GET)
	public ModelAndView findQuestionByAssignment(@ModelAttribute("questionModelForm") QuestionUserForm objQuesUserForm,HttpServletRequest request){
		System.out.println(objQuesUserForm.getQuestion().getAssign().getAssignmentId());
		long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		objQuesUserForm.getUser().setUserId(userId);
		JSONObject questionJsonObj = quesDAO.getQuestionByAssignmentId(objQuesUserForm);
		System.out.println(questionJsonObj.toString());
		ModelAndView mav = new ModelAndView("question");
		mav.addObject("questionList", questionJsonObj);
		mav.addObject("answerModelForm", new Answer());
		return mav;
	}

	@RequestMapping(value="/findstudentQues", method=RequestMethod.GET)
	public ModelAndView findStudentAssignmentQuestion(@ModelAttribute("questionModelForm") QuestionUserForm objQuesUserForm,HttpServletRequest request){
		System.out.println(objQuesUserForm.getQuestion().getAssign().getAssignmentId());
	/*	long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		objQuesUserForm.getUser().setUserId(userId);*/
		JSONObject questionJsonObj = quesDAO.getQuestionByAssignmentId(objQuesUserForm);
		System.out.println(questionJsonObj.toString());
		ModelAndView mav = new ModelAndView("question");
		mav.addObject("questionList", questionJsonObj);
		mav.addObject("answerModelForm", new Answer());
		return mav;
	}
	
	@RequestMapping(value="/saveAnswer", method=RequestMethod.POST)
	@ResponseBody
	public String submitAnswer(@Valid @RequestBody String ansJson, HttpServletRequest request)
	{
		JSONObject res = new JSONObject();
		try 
		{	
			JSONObject ansJsonObj = new JSONObject(ansJson);
			long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
			int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
			System.out.println(ansJsonObj.get("code"));
			System.out.println(ansJsonObj.get("assignment.assignmentId"));
			System.out.println("binary -->"+ansJsonObj.get("binary"));
			String output = "";
			long ansIdVal = 0;
			if(Long.valueOf(ansJsonObj.get("answerId").toString())==0)
				ansIdVal = ansDAO.saveAnswer(ansJsonObj,userId,semId);
			else
				ansIdVal = ansDAO.execute(ansJsonObj, userId, semId);
			if(ansIdVal>0)
				output = ansDAO.executeCode(ansJsonObj, userId, semId);
			System.out.println(output);
			res.put("output", output);
			res.put("id", ansIdVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}
	
	@RequestMapping(value="/saveComment", method=RequestMethod.POST)
	@ResponseBody
	public int saveCommentForAnswer(@Valid @RequestBody String ansComment, HttpServletRequest request){
		int result =0;
		JSONObject commentJsonObj;
		try {
			commentJsonObj = new JSONObject(ansComment);
			result = ansDAO.saveComment(commentJsonObj.get("comment").toString(), Long.valueOf(commentJsonObj.get("answerId").toString()));
			System.out.println("comment result-->"+result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/searchAssignment")
	public String searchAssignment(Model model){
		UserAssignmentMapping userAssignModel = new UserAssignmentMapping();
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("semesterList", miscDAO.getSemesterMap());
		model.addAttribute("userAssignModel", userAssignModel);
		return "searchStudentAssignment";
	}
	
	@RequestMapping(value="/studentAssignment",method=RequestMethod.GET)
	public String ownAssignment(@ModelAttribute("userAssignModel") UserAssignmentMapping objUserAssign,HttpServletRequest request, Model model){
		System.out.println(objUserAssign.getCourse().getCourseId());
		System.out.println(objUserAssign.getSem().getSemId());
		System.out.println(objUserAssign.getYear());
		long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		User user = new User();
		user.setUserId(userId);
		objUserAssign.setUser(user);
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("semesterList", miscDAO.getSemesterMap());
		/*List<Assignment> assList = assignDAO.getStudentAssingment(objAssign);
		for (Assignment assignment : assList) {
			System.out.println(assignment.getAssignmentId()+"------>s");
		}
		;*/
		List<UserAssignmentMapping> studentAssignmentList = assignDAO.getAssignmentByStudent(objUserAssign);
		UserAssignmentMapping assignModel = new UserAssignmentMapping();
		QuestionUserForm objQuesUser = new QuestionUserForm();
		Course course = new Course();
		course.setCourseId(objUserAssign.getCourse().getCourseId());
		Semester sem = new Semester();
		sem.setSemId(objUserAssign.getSem().getSemId());
		assignModel.setYear(objUserAssign.getYear());
		assignModel.setCourse(course);
		assignModel.setSem(sem);
		model.addAttribute("userAssignModel", assignModel);
		model.addAttribute("questionModelForm", objQuesUser);
		model.addAttribute("assignList", studentAssignmentList);
		return "searchStudentAssignment";
	}
	
	@RequestMapping(value="/searchSA")
	public String searchStudentAssignment(Model model){
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("semesterList", miscDAO.getSemesterMap());
		UserAssignmentMapping userAssignmentModel = new UserAssignmentMapping();
		model.addAttribute("userAssignModel",userAssignmentModel);
		return "profSearchAssignment";
	}
	
	@RequestMapping(value="/searchstudentassign")
	public String getAssignmentForProf(@ModelAttribute("userAssignModel") UserAssignmentMapping objUserAssign,HttpServletRequest request, Model model){
		System.out.println("courseId-->"+objUserAssign.getCourse().getCourseId());
		System.out.println("semId-->"+objUserAssign.getSem().getSemId());
		System.out.println("year-->"+objUserAssign.getYear());
		System.out.println("user-->"+objUserAssign.getUser().getUserId());
		
		List<UserAssignmentMapping> studentAssignmentList = assignDAO.getAssignmentByStudent(objUserAssign);
		
		/*List<Assignment> assList = assignDAO.getStudentAssingment(objAssign);
		for (int i=0;i<assList.size();i++) {
			User u = assList.get(i).getUser();
			u.setUserId(objAssign.getUser().getUserId());
			assList.get(i).setUser(u);
		}*/
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("semesterList", miscDAO.getSemesterMap());
		model.addAttribute("assignList", studentAssignmentList);
		
		Assignment assignModel = new Assignment();
		QuestionUserForm objQuesUser = new QuestionUserForm();
		Course course = new Course();
		course.setCourseId(objUserAssign.getCourse().getCourseId());
		Semester sem = new Semester();
		sem.setSemId(objUserAssign.getSem().getSemId());
		objUserAssign.setYear(objUserAssign.getYear());
		objUserAssign.setCourse(course);
		objUserAssign.setSem(sem);
		model.addAttribute("userAssignModel", objUserAssign);
		model.addAttribute("questionModelForm", objQuesUser);
		return "profSearchAssignment";
	}
	
	@RequestMapping(value="/executeforprof", method=RequestMethod.POST)
	@ResponseBody
	public String executeCodeForProf(@Valid @RequestBody String assignAnsJson, HttpServletRequest request){
		JSONArray result = new JSONArray();
		int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
		try 
		{	
			JSONObject ansJsonObj = new JSONObject(assignAnsJson);
			result = ansDAO.executeAssignmentForProf(ansJsonObj,semId);
			
		}catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	@RequestMapping(value="/savequery", method=RequestMethod.POST)
	@ResponseBody
	public String saveStudentQuery(@Valid @RequestBody String askQueryJson, HttpServletRequest request){
		String result = "";
		try 
		{	
			int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
			long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
			JSONObject queryJsonObj = new JSONObject(askQueryJson);
			long queryId = queryDAO.saveQuery(queryJsonObj,semId, userId);
			if(queryId>0)
				result = "success";
			else
				result = "fail";
			
		}catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value="/assignList", method=RequestMethod.POST)
	@ResponseBody
	public String getAssignmentList(@Valid @RequestBody String courseJson, HttpServletRequest request){
		JSONArray assignArray = new JSONArray();
		try 
		{	
			int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
			JSONObject courseJsonObj = new JSONObject(courseJson);
			assignArray = assignDAO.getAssignmentList(Integer.parseInt(courseJsonObj.get("courseVal").toString()), semId);
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return assignArray.toString();
	}

	/*@RequestMapping(value="/excute", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> excuteCode(@Valid @RequestBody String ansJson, HttpServletRequest request){
		try {
			JSONObject ansJsonObj = new JSONObject(ansJson);
			long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
			System.out.println(ansJsonObj.get("code"));
			System.out.println(ansJsonObj.get("assignment.assignmentId"));
			ansDAO.saveAnswer(ansJsonObj,userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("success");
	}*/

}
