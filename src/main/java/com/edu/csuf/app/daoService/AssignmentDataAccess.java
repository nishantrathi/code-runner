package com.edu.csuf.app.daoService;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.csuf.app.model.Assignment;
import com.edu.csuf.app.model.Course;
import com.edu.csuf.app.model.Semester;
import com.edu.csuf.app.model.UserAssignmentMapping;
import com.edu.csuf.app.repository.AssignmentRepository;
import com.edu.csuf.app.repository.UserAssignmentRepository;
import com.edu.csuf.app.utility.Utilities;

@Service
public class AssignmentDataAccess {
	@Autowired
	private AssignmentRepository assignRepo;
	@Autowired
	private Utilities utilities;

	@Autowired
	private UserAssignmentRepository uaRepo;

	@Transactional
	public int insertAssignment(Assignment assign){
		System.out.println("----1");
		assign.setCreatedOn(utilities.currentDateTime());
		String endDate = utilities.getEnd(assign.getEndDate());
		assign.setEndDate(endDate);
		assignRepo.save(assign);
		return assign.getAssignmentId();
	}

	@Transactional
	public List<Assignment> getAssignmentByCourse(Assignment assign){
		return assignRepo.findByCourse(assign.getCourse());
	}

	@Transactional()
	public List<Assignment> getStudentAssingment(Assignment assign){
		return assignRepo.findByCourseAndSemAndYear(assign.getCourse(), assign.getSem(), assign.getYear());
	}

	@Transactional
	public Assignment getSpecificAssignment(int assignId){
		return assignRepo.findByAssignmentId(assignId);
	}

	@Transactional
	public List<UserAssignmentMapping>  getAssignmentByStudent(UserAssignmentMapping uam){
		List<UserAssignmentMapping> userAssginList = uaRepo.findByUserAndCourseAndSemAndYear(uam.getUser(), uam.getCourse(), uam.getSem(), uam.getYear());
		for (UserAssignmentMapping ua : userAssginList) {
			System.out.println(ua.getAssignment().getAssignmentId());
			System.out.println(ua.getUser().getUserId());
			System.out.println(ua.getAssignment().getAssignmentNumber());
			System.out.println(ua.getCourse().getCourseCode());
		}

		return userAssginList;
	}

	@Transactional
	public JSONArray getAssignmentList(int courseId, int semId){
		List<Assignment> assignmentList = null;
		Course course =  new Course();
		course.setCourseId(courseId);

		Semester sem = new Semester();
		sem.setSemId(semId);
		JSONArray assignArray = new JSONArray();
		try {
			assignmentList = assignRepo.findByCourseAndSemAndYear(course, sem, utilities.getCurrentYear());
			if(assignmentList.size()>0)
			{
				for(int i=0;i<assignmentList.size();i++){
					JSONObject assignObj = new JSONObject(); 
					assignObj.put(String.valueOf(assignmentList.get(i).getAssignmentId()), assignmentList.get(i).getAssignmentNumber());
					assignArray.put(assignObj);
				} 
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return assignArray;
	}
}
