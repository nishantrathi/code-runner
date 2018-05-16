package com.edu.csuf.app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.edu.csuf.app.model.Login;
import com.edu.csuf.app.model.User;

public interface LoginRepository extends CrudRepository<Login, Long> {
	
	@Query("select l from Login l where l.userName = ?1")
	public Login findByUserName(String userName);
	
	@Modifying
	@Query("update Login l set l.status = ?1 where l.user = ?2")
	public int updateAccountStatus(String status, User user);
}
