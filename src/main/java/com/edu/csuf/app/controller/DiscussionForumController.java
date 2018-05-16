package com.edu.csuf.app.controller;

import java.util.List;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.result.Output;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.csuf.app.daoService.QueryDataAccess;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.CourseAssignmentQuestionForm;
import com.edu.csuf.app.model.QueryAnswer;
import com.edu.csuf.app.model.StudentQuery;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.model.UserAssignmentMapping;

@Controller
@RequestMapping(value = "/forum")
public class DiscussionForumController {

	@Autowired
	private QueryDataAccess queryDAO;

	@RequestMapping(value="/fpage")
	public String redirectForumPage(Model model, HttpServletRequest request){
		int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
		long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		StudentQuery sq = new StudentQuery();
		model.addAttribute("courseList", queryDAO.getStudentEnrolledClasses(userId, semId));
		model.addAttribute("queryForm", sq);
		return "discussionForum";
	}

	@RequestMapping(value = "/query", method=RequestMethod.GET)
	public ModelAndView getQueries(@ModelAttribute("queryForm") StudentQuery objQuery,HttpServletRequest request){
		System.out.println("course id-->"+objQuery.getCourse().getCourseId());
		System.out.println("assignmetId -->"+ objQuery.getAssignment().getAssignmentId());
		int semId = Integer.valueOf(request.getSession().getAttribute("semId").toString());
		long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		List<StudentQuery> queryList = queryDAO.getQueries(semId, objQuery.getCourse().getCourseId(), objQuery.getAssignment().getAssignmentId());
		ModelAndView mav = new ModelAndView("discussionForum");
		mav.addObject("courseList", queryDAO.getStudentEnrolledClasses(userId, semId));
		mav.addObject("queryList", queryList);
		QueryAnswer qa = new QueryAnswer();
		StudentQuery sq = new StudentQuery();
		Course c = new Course();
		c.setCourseId(objQuery.getCourse().getCourseId());

		Assignment assign = new Assignment();
		assign.setAssignmentId(objQuery.getAssignment().getAssignmentId());

		sq.setAssignment(assign);
		sq.setCourse(c);

		mav.addObject("queryForm", sq);
		mav.addObject("queryAnswer", qa);
		return mav;
	}

	@RequestMapping(value="/queryanswers", method=RequestMethod.GET)
	public ModelAndView getQueryAnswer(@ModelAttribute("queryAnswer") QueryAnswer objQueryAns, HttpServletRequest request){
		System.out.println("query Id-->"+objQueryAns.getStudentQuery().getQueryId());
		JSONArray ansJsonArr = queryDAO.getQueryAnswerforStudentQuery(objQueryAns.getStudentQuery());
		ModelAndView mav = new ModelAndView("studentQueryAnswer");
		QueryAnswer queryAns = new QueryAnswer();
		try {
			mav.addObject("answerList", ansJsonArr.get(0));
			mav.addObject("query", ansJsonArr.get(1));
			mav.addObject("ansForm", queryAns);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value="/savequeryanswer", method=RequestMethod.POST)
	@ResponseBody
	public String saveQueryAnswer(@Valid @RequestBody String ansJson, HttpServletRequest request){
		JSONObject finalJsonOutput = new JSONObject();
		try 
		{	
			long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
			JSONObject queryAnsJsonObj = new JSONObject(ansJson);
			JSONObject outputJson = new JSONObject();
			QueryAnswer qa = queryDAO.saveQueryAnswer(queryAnsJsonObj,userId);
			if(qa.getQueryAnsId()>0){
				outputJson.put("name", qa.getUser().getFirstName()+" "+qa.getUser().getLastName());
				outputJson.put("cwid", qa.getUser().getCwid());
				outputJson.put("date", qa.getSubmittedDate());
				outputJson.put("answer", qa.getQueryAnswer());
				outputJson.put("queryAnsId", qa.getQueryAnsId());
				finalJsonOutput.put("output",outputJson);
				finalJsonOutput.put("result","success");
			}else{
				finalJsonOutput.put("result","fail");
			}
			
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return finalJsonOutput.toString();
	}
}
