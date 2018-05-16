package com.edu.csuf.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.edu.csuf.app.daoService.CourseDataAccess;
import com.edu.csuf.app.daoService.LoginDataAccess;
import com.edu.csuf.app.daoService.MiscellaneousDataAccess;
import com.edu.csuf.app.daoService.UserDataAccess;
import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Login;
import com.edu.csuf.app.model.LoginUserForm;
import com.edu.csuf.app.model.StudentCourseSemMap;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.model.UserAssignmentMapping;
import com.edu.csuf.app.repoImplementation.CustomUserRepoImpl;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDataAccess userDAO;

	@Autowired
	private LoginDataAccess loginDAO;

	@Autowired
	private CourseDataAccess courseDAO;

	@Autowired
	private MiscellaneousDataAccess miscDAO;


	/*@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView insertUserInfo(User user){
		userDAO.insertUserDetails(user);
		return new ModelAndView("home","command", new User()); 
	}*/

	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public ModelAndView insertUserInfo1(@Valid @ModelAttribute("myForm") LoginUserForm myForm, BindingResult bindingResult)
	{
		System.out.println("Nishant .....");
		User user = myForm.getUser();
		user.setStatus("New");
		userDAO.insertUserDetails(user);
		Login login = myForm.getLogin();
		login.setUser(user);
		login.setStatus("New");
		login.setUserType(user.getType());
		long loginId = loginDAO.insertLoginDetails(login);
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("command", new LoginUserForm());
		if(loginId>0)
			mav.addObject("msg", "Account created succesfully. It will take 24hrs to activate your account.");
		else
			mav.addObject("msg", "Account is not created. Please try again.");
		return mav; 
	}

	/*	@PostMapping(value="/searchStudent")
	@ResponseBody
	public ResponseEntity<?>  getStudentList(@Valid @RequestBody User user){
		System.out.println(user.getCwid());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		List<User> uList = userDAO.getUserListByFirstNameLastNameCwid(user);
		return ResponseEntity.ok(uList);
	}*/

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView getStudentPage(@RequestParam(name="page") String pageNumber, @RequestParam(value="user.type") int type, @RequestParam(value="user.firstName", defaultValue="", required=false) String fName, @RequestParam(value="user.lastName", defaultValue="", required=false) String lName, @RequestParam(value="user.cwid", defaultValue="", required=false) String cwid){
		System.out.println(fName+"-"+lName+"-"+cwid);
		Page<User> pu = userDAO.getStudentPageList(fName,lName,cwid, Integer.parseInt(pageNumber),type);
		for (User user : pu.getContent()) {
			System.out.println(user.getFirstName()+"-"+user.getLastName());
		}
		ModelAndView mav = new ModelAndView();
		StudentCourseSemMap objSCM = new StudentCourseSemMap();
		mav.addObject("courseList", courseDAO.getAllCourseMap());
		mav.addObject("semesterList", miscDAO.getSemesterMap());
		mav.addObject("studentList", pu.getContent());
		mav.addObject("totalPages", pu.getTotalPages());
		mav.addObject("current", pageNumber);
		mav.addObject("courseList", courseDAO.getAllCourseMap());
		mav.addObject("semesterList", miscDAO.getSemesterMap());
		mav.addObject("scmaping",objSCM);
		mav.setViewName("userCourseMapping");
		return mav;
	}

	@RequestMapping("/searchStudent")
	public String redirectStudentCourseMap(Model model){
		StudentCourseSemMap objSCM = new StudentCourseSemMap();
		model.addAttribute("scmaping",objSCM);
		return "searchStudent";
	}

	@RequestMapping(value="/accountStatus")
	public String userAccountStatus(Model model){
		User user = new User();
		List<String> statusList = new ArrayList<String>();
		statusList.add("New");
		statusList.add("Activated");
		statusList.add("Deactivated");
		model.addAttribute("statusList", statusList);
		model.addAttribute("userForm", user);
		return "activateAccount";
	}

	@RequestMapping(value="/searchAccount", method=RequestMethod.GET)
	public ModelAndView searchUserAccount(@RequestParam(name="page") String pageNumber, @RequestParam(value="status", defaultValue="New") String status, @RequestParam(value="firstName", defaultValue="", required=false) String fName, @RequestParam(value="lastName", defaultValue="", required=false) String lName, @RequestParam(value="cwid", defaultValue="", required=false) String cwid){
		System.out.println(status);
		System.out.println(fName);
		System.out.println(lName);
		System.out.println(cwid);
		User user = new User();
		ModelAndView mav = new ModelAndView();
		Page<User> pu = userDAO.getUserPageList(fName,lName,cwid, Integer.parseInt(pageNumber),status);
		mav.addObject("totalPages", pu.getTotalPages());
		mav.addObject("current", pageNumber);
		System.out.println("size-->"+pu.getContent().size());
		mav.addObject("userList", pu.getContent());
		
		List<User> uList = pu.getContent();
		for (User u : uList) {
			System.out.println(u.getCwid()+" "+u.getFirstName());
		}
		List<String> statusList = new ArrayList<String>();
		statusList.add("New");
		statusList.add("Activated");
		statusList.add("Deactivated");
		mav.addObject("statusList", statusList);
		if(!cwid.isEmpty() && cwid!=null)
			user.setCwid(Long.valueOf(cwid));
		user.setFirstName(fName);
		user.setLastName(lName);
		user.setStatus(status);
		mav.addObject("userForm", user);
		mav.setViewName("activateAccount");
		return mav;
	}
	
	@RequestMapping(value="/activate", method=RequestMethod.POST)
	@ResponseBody
	public boolean activateAccount(@Valid @RequestBody String statusJson, HttpServletRequest request){
		boolean result = false;
		try 
		{	
			JSONObject stJsonObj = new JSONObject(statusJson);
			result = userDAO.updateAccountStatus(stJsonObj.get("status").toString(), Long.valueOf(stJsonObj.get("user").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/searchstudent", method=RequestMethod.GET)
	public ModelAndView searchStudentForAssignmentByProf(@RequestParam(name="page") String pageNumber, @RequestParam(value="firstName", defaultValue="", required=false) String fName, @RequestParam(value="lastName", defaultValue="", required=false) String lName, @RequestParam(value="cwid", defaultValue="", required=false) String cwid){
		User user = new User();
		ModelAndView mav = new ModelAndView();
		
		Page<User> pu = userDAO.getStudentPageList(fName,lName,cwid, Integer.parseInt(pageNumber),1);
		mav.addObject("totalPages", pu.getTotalPages());
		mav.addObject("current", pageNumber);
		System.out.println("size-->"+pu.getContent().size());
		mav.addObject("studentList", pu.getContent());
		if(!cwid.isEmpty() && cwid!=null)
			user.setCwid(Long.valueOf(cwid));
		user.setFirstName(fName);
		user.setLastName(lName);
		UserAssignmentMapping userAssignObj = new UserAssignmentMapping();
		userAssignObj.setUser(user);
		mav.addObject("userAssignModel", userAssignObj);
		mav.addObject("courseList", courseDAO.getAllCourseMap());
		mav.addObject("semesterList", miscDAO.getSemesterMap());
		mav.setViewName("profSearchAssignment");
		return mav;
	}


}
