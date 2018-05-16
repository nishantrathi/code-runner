package com.edu.csuf.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edu.csuf.app.daoService.CourseDataAccess;
import com.edu.csuf.app.daoService.MiscellaneousDataAccess;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.CourseSemesterMapping;
import com.edu.csuf.app.model.LoginUserForm;
import com.edu.csuf.app.model.StudentCourseSemMap;

@Controller
@RequestMapping("/misc")
public class MiscController {
	@Autowired
	private CourseDataAccess courseDAO;

	@Autowired
	private MiscellaneousDataAccess miscDAO;

	@RequestMapping("/iCourse")
	public ModelAndView insertCourseInfo(Course course){
		courseDAO.insertCourseDetails(course);
		return new ModelAndView("course","course",new Course());
	}

	@RequestMapping("/course")
	public String redirectCourse(Model model){
		model.addAttribute("course",new Course());
		return "course";
	}

	@RequestMapping("/scm")
	public String redirectStudentCourseMap(Model model){
		StudentCourseSemMap objSCM = new StudentCourseSemMap();
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("semesterList", miscDAO.getSemesterMap());
		model.addAttribute("scmaping",objSCM);
		return "userCourseMapping";
	}

	@RequestMapping("/coursesemmap")
	public String redirectCourseSemesterMap(Model model){
		CourseSemesterMapping objCSM = new CourseSemesterMapping();
		model.addAttribute("courseList", courseDAO.getAllCourseMap());
		model.addAttribute("semesterList", miscDAO.getSemesterMap());
		model.addAttribute("csmap",objCSM);
		return "courseSemesterMapping";
	}

	@RequestMapping(value="/insertSCM")
	public ModelAndView insertStudentCourseMapping(@Valid @ModelAttribute("scmaping") StudentCourseSemMap objSCSMapping, BindingResult bindingResult, HttpServletRequest request){
		System.out.println(objSCSMapping.getUser().getUserId());
		System.out.println(objSCSMapping.getCourse().getCourseId());
		System.out.println(objSCSMapping.getSem().getSemId());
		miscDAO.insertStudentCourseMapping(objSCSMapping);
		StudentCourseSemMap objSCM = new StudentCourseSemMap();
		ModelAndView mav = new ModelAndView();
		mav.addObject("courseList", courseDAO.getAllCourseMap());
		mav.addObject("semesterList", miscDAO.getSemesterMap());
		mav.addObject("scmaping",objSCM);
		mav.setViewName("userCourseMapping");
		return mav;
	}
	
	@RequestMapping(value="/insertCourseSem", method=RequestMethod.POST)
	public ModelAndView insertCourseSemesterMapping(@Valid @ModelAttribute("csmap") CourseSemesterMapping objCSM, BindingResult br, HttpServletRequest request){
		System.out.println("insert data");
		miscDAO.insertCourseSemesterMapping(objCSM);
		ModelAndView mav = new ModelAndView();
		CourseSemesterMapping csm = new CourseSemesterMapping();
		mav.addObject("courseList", courseDAO.getAllCourseMap());
		mav.addObject("semesterList", miscDAO.getSemesterMap());
		mav.addObject("csmap",csm);
		mav.setViewName("courseSemesterMapping");
		return mav;
	}
	
	@RequestMapping(value="/home")
	public ModelAndView redirectToHomePage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		long userId = Long.valueOf(request.getSession().getAttribute("uId").toString());
		mav.addObject("studentCourseList",miscDAO.getRegisteredCourseForStudent(userId,3));
		Assignment assignForm = new Assignment();
		mav.addObject("assignModelForm", assignForm);
		mav.setViewName("studentHomePage");
		return mav;
	}




}
