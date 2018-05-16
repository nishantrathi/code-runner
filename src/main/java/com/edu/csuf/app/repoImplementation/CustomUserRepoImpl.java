package com.edu.csuf.app.repoImplementation;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.repository.CustomUserRepository;

@Service
@Transactional
public class CustomUserRepoImpl implements CustomUserRepository{

	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByFirstNameLastNameCwid(User user) {
		
	/*	StringBuilder sb = new StringBuilder();
		sb.append("select u.uId, u.firstName, u.lastName, u.cwid from User u where u.type=1");
		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty())
			sb.append(" and u.firstName= :fName");
		if(user.getLastName()!=null && !user.getLastName().trim().isEmpty())
			sb.append(" and u.lastName= :lName");
		if(user.getCwid()!=0 && user.getCwid()>0)
			sb.append(" and u.cwid= :cwid");
		Query query = em.createNativeQuery(sb.toString());
		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty())
			query.setParameter("fName", user.getFirstName());
		if(user.getLastName()!=null && !user.getLastName().trim().isEmpty())
			query.setParameter("lName", user.getLastName());
		if(user.getCwid()!=0 && user.getCwid()>0)
			query.setParameter("cwid", user.getCwid());
		List<User> userList = query.getResultList();
		return userList;*/
		
		StringBuilder sb = new StringBuilder();
		sb.append("select u.userId, u.firstName, u.lastName, u.cwid from User u where u.type=1");
		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty())
			sb.append(" and u.firstName= :fName");
		if(user.getLastName()!=null && !user.getLastName().trim().isEmpty())
			sb.append(" and u.lastName= :lName");
		if(user.getCwid()!=0 && user.getCwid()>0)
			sb.append(" and u.cwid= :cwid");
		Query query = em.createQuery(sb.toString());
		if(user.getFirstName()!=null && !user.getFirstName().trim().isEmpty())
			query.setParameter("fName", user.getFirstName());
		if(user.getLastName()!=null && !user.getLastName().trim().isEmpty())
			query.setParameter("lName", user.getLastName());
		if(user.getCwid()!=0 && user.getCwid()>0)
			query.setParameter("cwid", user.getCwid());
		List<User> userList = query.getResultList();
		return userList;
	}


	@Override
	public List<Integer> getRegisteredCoursesForStudent(long studentId, long semId, int year) {
		StringBuilder sb = new StringBuilder();
		sb.append("select scs.courseId from studentCourseSemMapping scs where scs.userId=:uId and scs.semesterId=:semId and scs.year=:year");
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("uId", studentId);
		query.setParameter("semId", semId);
		query.setParameter("year", year);
		@SuppressWarnings("unchecked")
		List<Integer> cList = query.getResultList();
		return cList;
	}
	
}
