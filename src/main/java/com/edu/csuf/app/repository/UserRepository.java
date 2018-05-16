package com.edu.csuf.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edu.csuf.app.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	/*//@Query("Select u from User u where u.firstName like :fName%")
	public List<User> findByFirstNameStartingWith(String fName);
	
	public List<User> findByLastNameStartingWith(String lName);
	public List<User> findByCwid(long cwid);
	public List<User> findByFirstNameStartingWithAndLastNameStartingWith(String fName, String lName);
	public List<User> findByFirstNameStartingWithAndCwid(String fName, long cwid);
	public List<User> findByLastNameStartingWithAndCwid(String lName, long cwid);
	public List<User> findByFirstNameStartingWithAndLastNameStartingWithAndCwid(String fName, String lName, long cwid);*/
	
	public List<User> findByFirstNameStartingWith(String fName, Pageable pa);
	public Page<User> findByFirstNameStartingWithAndLastNameStartingWithAndCwidAndType(String fName, String lName, long cwid,int type, Pageable p);
	public Page<User> findByFirstNameStartingWithAndLastNameStartingWithAndType(String fName, String lName,int type, Pageable p);
	public Page<User> findByFirstNameStartingWithAndLastNameStartingWithAndCwidAndStatus(String fName, String lName, long cwid,String status, Pageable p);
	public Page<User> findByFirstNameStartingWithAndLastNameStartingWithAndStatus(String fName, String lName,String status, Pageable p);
	public Page<User> findByFirstNameStartingWithAndLastNameStartingWithAndCwid(String fName, String lName, long cwid,Pageable p);
	public Page<User> findByFirstNameStartingWithAndLastNameStartingWith(String fName, String lName, Pageable p);
	
	@Modifying
	@Query("update User u set u.status = ?1 where u.userId = ?2")
	public int updateAccountStatus(String status, long userId);
}
