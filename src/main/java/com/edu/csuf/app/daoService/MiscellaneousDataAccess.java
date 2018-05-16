package com.edu.csuf.app.daoService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.CourseSemesterMapping;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.StudentCourseSemMap;
import com.edu.csuf.app.model.User;
import com.edu.csuf.app.model.UserAssignmentMapping;
import com.edu.csuf.app.repoImplementation.CustomUserRepoImpl;
import com.edu.csuf.app.repository.CourseSemesterMappingRepository;
import com.edu.csuf.app.repository.SemesterRepository;
import com.edu.csuf.app.repository.StudentCourseMappingRepository;
import com.edu.csuf.app.repository.UserAssignmentRepository;
import com.edu.csuf.app.utility.Utilities;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class MiscellaneousDataAccess {
	@Autowired
	private SemesterRepository semRepo;
	
	@Autowired
	private StudentCourseMappingRepository scmRepo;
	
	@Autowired
	private CustomUserRepoImpl customRepo;
	
	@Autowired
	private CourseDataAccess cda;
	
	@Autowired
	private CourseSemesterMappingRepository csmRepo;
	
	@Autowired
	private UserAssignmentRepository uaRepo;
	
	
	
	@Autowired
	private Utilities ut;

	@Transactional
	public HashMap<Integer, String> getSemesterMap(){
		HashMap<Integer, String> semMap = new HashMap<Integer, String>();
		List<Semester> semList = semRepo.findAll();
		for (Semester semester : semList) {
			semMap.put(semester.getSemId(), semester.getSemName());
		}
		return semMap; 
	}
	
	@Transactional
	public void insertStudentCourseMapping(StudentCourseSemMap scsMap){
		scmRepo.save(scsMap);
		if(scsMap.getScsId()>0){
			boolean flag = ut.createFolder(ut.createPath(scsMap.getYear(), scsMap.getSem().getSemId(), scsMap.getUser().getUserId(), scsMap.getCourse().getCourseId()));
			if(flag)
				System.out.println("Folder created");
			else
				System.out.println("Folder not created");
			
		}
	}
	
	@Transactional
	public List<Course> getRegisteredCourseForStudent(long uId,long semId){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> courseIdList = customRepo.getRegisteredCoursesForStudent(uId,semId,year);
		List<Course> courseList = new ArrayList<>();
		if(courseIdList.size()>0)
			courseList = cda.getCourseName(courseIdList);
		//System.out.println(courseList.get(0).getCourseCode());
		return courseList;
	}
	@Transactional
	public Semester getActiveSemester(int val){
		Semester sem = semRepo.findByActive(val);
		return sem;
	}
	
	@Transactional
	public void insertCourseSemesterMapping(CourseSemesterMapping csm){
		csmRepo.save(csm);
	}
	
	@Transactional
	public void insertUserAssignmentMapping(UserAssignmentMapping uam){
		System.out.println("user assignment mapping 2");
		System.out.println(uaRepo.save(uam).getUaId());
	}
	
	@Transactional
	public List<UserAssignmentMapping> findAssignmentRecord(User user, Semester sem, Course course, Assignment assign, int year)
	{
		List<UserAssignmentMapping> uamList = uaRepo.findByUserAndCourseAndSemAndAssignmentAndYear(user, course,sem, assign, year);
		return uamList;
	}
	
	@Transactional
	public void updateFinalSubmittedDate(String date, Assignment assign){
		uaRepo.updateDateForAssignment(date, assign);
	}
}
