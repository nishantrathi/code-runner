package com.edu.csuf.app.daoService;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Login;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.repository.LoginRepository;

@Service
public class LoginDataAccess {
	
	@Autowired
	private LoginRepository loginRepo;
	

	
	@Transactional
	public long insertLoginDetails(Login login){
		loginRepo.save(login);
		return login.getUser().getUserId();
	}
	
	@Transactional
	public Login checkUserCredentials(String userName, String pwd){
		Login login=loginRepo.findByUserName(userName);
		if(login.getPassword().equals(pwd)){
			System.out.println(login.getUser().getUserId());
			return login;
		}
		else
			return null;
	}
	
	@Transactional
	public int updateLoginStatus(String status, long uId){
		User user = new User();
		user.setUserId(uId);
		int val = loginRepo.updateAccountStatus(status, user);
		return val;
	}
}
