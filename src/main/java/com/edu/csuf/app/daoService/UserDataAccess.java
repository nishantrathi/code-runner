package com.edu.csuf.app.daoService;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.edu.csuf.app.model.User;
import com.edu.csuf.app.repoImplementation.CustomUserRepoImpl;
import com.edu.csuf.app.repository.UserRepository;
import com.edu.csuf.app.utility.Utilities;

@Service
public class UserDataAccess {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private Utilities utilities;
	@Autowired
	private CustomUserRepoImpl customUser;
	@Autowired
	private LoginDataAccess loginDAO;

	private final static int PAGESIZE = 3;

	@Transactional
	public User insertUserDetails(User user){
		user.setCreatedOn(utilities.generateTodaysDate());
		userRepo.save(user);
		return user;
	}

	/*	public List<User> getUserListByFirstNameLastNameCwid(User user)
	{
		List<User> userList = null;
		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty() && user.getLastName().trim().isEmpty() && user.getCwid()==0)
			userList =  userRepo.findByFirstNameStartingWith(user.getFirstName());

		if(user.getLastName()!=null && !user.getLastName().trim().isEmpty() && user.getFirstName().trim().isEmpty() && user.getCwid()==0)
			userList = userRepo.findByLastNameStartingWith(user.getLastName());

		if(user.getCwid()!=0 && user.getCwid()>0 && (user.getLastName().trim().isEmpty() && user.getFirstName().trim().isEmpty()))
			userList = userRepo.findByCwid(user.getCwid());

		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty() && user.getLastName()!=null && !user.getLastName().trim().isEmpty() && user.getCwid()==0)
			userList = userRepo.findByFirstNameStartingWithAndLastNameStartingWith(user.getFirstName(), user.getLastName());

		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty() && (user.getLastName()==null || user.getLastName().trim().isEmpty()) && user.getCwid()>0)
			userList = userRepo.findByFirstNameStartingWithAndCwid(user.getFirstName(), user.getCwid());

		if((user.getFirstName()==null || user.getFirstName().trim().isEmpty()) && user.getLastName()!=null && !user.getLastName().trim().isEmpty() && user.getCwid()>0)
			userList = userRepo.findByLastNameStartingWithAndCwid(user.getLastName(), user.getCwid());

		if((user.getFirstName()!=null && !user.getFirstName().trim().isEmpty()) && (user.getLastName()!=null && !user.getLastName().trim().isEmpty()) && user.getCwid()>0)
			userList = userRepo.findByFirstNameStartingWithAndLastNameStartingWithAndCwid(user.getFirstName(),user.getLastName(), user.getCwid());

		return userList;
		//return customUser.findByFirstNameLastNameCwid(user);
	}*/

	@Transactional
	public Page<User> getStudentPageList(String fName,String lName, String cwid, int pageNumber, int type){
		PageRequest request = new PageRequest(pageNumber-1, PAGESIZE, Sort.Direction.ASC, "firstName");
		//studentList = userRepo.findByFirstNameStartingWith(fName, request);
		Page<User> pu = null;
		if(cwid.isEmpty() || cwid==null){
			pu = userRepo.findByFirstNameStartingWithAndLastNameStartingWithAndType(fName,lName,type, request);
		}
		else
			pu = userRepo.findByFirstNameStartingWithAndLastNameStartingWithAndCwidAndType(fName,lName, Long.parseLong(cwid), type, request);
		return pu;
	}
	
	@Transactional
	public Page<User> getUserPageList(String fName, String lName, String cwid, int pageNumber, String status){
		PageRequest request = new PageRequest(pageNumber-1, PAGESIZE, Sort.Direction.ASC, "firstName");
		Page<User> pu = null;
		if(cwid.isEmpty() || cwid==null){
			pu = userRepo.findByFirstNameStartingWithAndLastNameStartingWithAndStatus(fName, lName, status, request);
		}
		else
			pu = userRepo.findByFirstNameStartingWithAndLastNameStartingWithAndCwidAndStatus(fName,lName, Long.parseLong(cwid), status, request);
		return pu;
	}

	@Transactional
	public boolean updateAccountStatus(String status, long uId){
		boolean result = false;
		int userVal = userRepo.updateAccountStatus(status, uId);
		int loginVal = loginDAO.updateLoginStatus(status, uId);
		if(userVal==1 && loginVal==1)
			result = true;
		return result;
	}
	
	@Transactional
	public Page<User>searchStudentForProfessor(String fName, String lName, String cwid, int pageNumber){
		PageRequest request = new PageRequest(pageNumber-1, PAGESIZE, Sort.Direction.ASC, "firstName");
		Page<User> pu = null;
		if(cwid.isEmpty() || cwid==null){
			pu = userRepo.findByFirstNameStartingWithAndLastNameStartingWith(fName, lName, request);
		}
		else
			pu = userRepo.findByFirstNameStartingWithAndLastNameStartingWithAndCwid(fName,lName, Long.parseLong(cwid), request);
		return pu;
	}
}
