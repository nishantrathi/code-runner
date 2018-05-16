package com.edu.csuf.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edu.csuf.app.daoService.CourseDataAccess;
import com.edu.csuf.app.daoService.LoginDataAccess;
import com.edu.csuf.app.daoService.MiscellaneousDataAccess;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Login;
import com.edu.csuf.app.model.LoginUserForm;
import com.edu.csuf.app.model.StudentCourseSemMap;
import com.edu.csuf.app.model.User;

@Controller
@RequestMapping("/uCheck")
public class LoginRedirectController {


	/*@RequestMapping("/")
	public ModelAndView home(){
		return new ModelAndView("home","command", new MyForm());
	}*/
	@Autowired
	private LoginDataAccess loginDAO;

	@Autowired
	private MiscellaneousDataAccess miscDAO;

	@Autowired
	private CourseDataAccess courseDAO;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home(Model model){
		System.out.println("inside home");
		model.addAttribute("myForm", new LoginUserForm());
		return "home";
		//return "mainTab";
	}

	@RequestMapping(value="/login")
	public ModelAndView loginCheck(@Valid @ModelAttribute("myForm") LoginUserForm myForm, BindingResult bindingResult, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		try {
			System.out.println(myForm.getLogin().getUserName()+" "+myForm.getLogin().getPassword());
			Login objLogin = loginDAO.checkUserCredentials(myForm.getLogin().getUserName(), myForm.getLogin().getPassword());
			if(objLogin==null){
				myForm.setUser(new User());
				mav.addObject("myForm", myForm);
				mav.setViewName("home");
				mav.addObject("msg", "Please create Account");
			}
			else{
				long userId = objLogin.getUser().getUserId();
				int userType = objLogin.getUserType();
				int semId = miscDAO.getActiveSemester(1).getSemId();
				if(objLogin.getStatus().equals("Activated") || objLogin.getStatus().equals("Admin")){
					request.getSession().setAttribute("uId", userId);
					request.getSession().setAttribute("type", objLogin.getUserType());
					request.getSession().setAttribute("semId", semId);
					request.getSession().setAttribute("cwid", objLogin.getUser().getCwid());
					if(userType==4)
					{
						/*StudentCourseSemMap objSCM = new StudentCourseSemMap();
						mav.addObject("courseList", courseDAO.getAllCourseMap());
						mav.addObject("semesterList", miscDAO.getSemesterMap());
						mav.addObject("scmaping",objSCM);
						mav.setViewName("userCourseMapping");*/
						System.out.println("inside admin if");
						List<String> statusList = new ArrayList<String>();
						statusList.add("New");
						statusList.add("Activated");
						statusList.add("Deactivated");
						User user = new User();
						mav.addObject("statusList", statusList);
						mav.addObject("userForm", user);
						mav.setViewName("activateAccount");
					}else
					{
						mav.addObject("studentCourseList",miscDAO.getRegisteredCourseForStudent(userId,semId));
						Assignment assignForm = new Assignment();
						mav.addObject("assignModelForm", assignForm);
						mav.setViewName("studentHomePage");
					}
				}else if(objLogin.getStatus().equals("Deactivated")){
					myForm.setUser(new User());
					mav.addObject("myForm", myForm);
					mav.setViewName("home");
					mav.addObject("msg", "Contact Admin to activate your account");
				}else{
					myForm.setUser(new User());
					mav.addObject("myForm", myForm);
					mav.setViewName("home");
					mav.addObject("msg", "Account is created. It will take 24hrs to activate your account");
				}
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/logout")
	public String closeSession(HttpServletRequest request){
	    HttpSession session=request.getSession();  
	    session.invalidate();  
	    return "redirect:../uCheck/";
	}



}
