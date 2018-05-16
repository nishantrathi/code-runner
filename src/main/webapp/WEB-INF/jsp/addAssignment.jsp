<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Assignment</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="../js/jquery-3.3.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		var assignmentId = $("#assignId").val();
		console.log("-->" + assignmentId);
		//$("#endDateId").datepick({dateFormat: 'yyyy-mm-dd HH:mm:ss'});
		$("#endDateId").datepicker({dateFormat: 'yy-mm-dd'});
	});

	function showQuestionSection() {
		var assignmentId = $("#assignId").val();
		if (assignmentId > 0) {
			$("#question").show();
		} else {
			$("#question").hide();
		}
	}
</script>
</head>
<body onload="showQuestionSection()">
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form modelAttribute="caqForm" action="../assignment/addAssign">
		<table>
			<tr>
				<td><form:label path="course.courseId">Course</form:label></td>
				<td><form:label path="assignment.assignmentNumber">Assignment Number</form:label></td>
				<td><form:label path="assignment.endDate">End Date</form:label></td>
			</tr>
			<tr>
				<td><form:select path="course.courseId">
						<form:option value="0" label="Select" />
						<form:options items="${courseList}" />
					</form:select></td>
				<td><form:input path="assignment.assignmentNumber" /></td>
				<td><form:input path="assignment.endDate" placeholder="select Date" id="endDateId"/><img id="dateImg" src="../image/calendar.png" width="10px" height="15px" alt="calendar"/></td>
			</tr>
			<tr>
				<td colspan=2>
				<td><form:hidden path="assignment.assignmentId"
						value="${assignment.assignmentId}" id="assignId" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</form:form>
	<form:form id="questionFormId" modelAttribute="caqForm"
		action="../assignment/addQuestion">
		<div id="question">
			<table>
				<tr>
					<td><form:label path="ques.questionNumber">Question Number</form:label></td>
					<td><form:input path="ques.questionNumber" /></td>
				</tr>
				<tr>
					<td><form:label path="ques.questionDetail">Question Info</form:label></td>
					<td><form:input path="ques.questionDetail" /></td>
				</tr>
				<tr>
					<td><form:hidden path="assignment.assignmentNumber"
							value="${assignment.assignmentNumber}" id="aNumId" /></td>
					<td><form:hidden path="assignment.assignmentId"
							value="${assignment.assignmentId}" id="aId" /></td>
					<td><form:hidden path="course.courseId"
							value="${course.courseId}" id="couId" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Save" /></td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
</html>