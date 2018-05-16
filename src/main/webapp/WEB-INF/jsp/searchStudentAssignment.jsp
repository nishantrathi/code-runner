<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Assignment</title>
<script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form modelAttribute="userAssignModel" id="serachForm"
		action="../assignment/studentAssignment" method="GET">
		<div id="searchDiv">
			<table width="100%">
				<tr>
					<td><form:label path="course.courseId">Course</form:label></td>
					<td><form:select path="course.courseId">
							<form:option value="0" label="Select" />
							<form:options items="${courseList}" />
						</form:select></td>
					<td><form:label path="sem.semId">Semester</form:label></td>
					<td><form:select path="sem.semId">
							<form:option value="0" label="Select" />
							<form:options items="${semesterList}" />
						</form:select></td>
					<td><form:label path="year">Year</form:label></td>
					<td><form:select id="yearId" path="year">
							<form:option value="0" label="Select" />
						</form:select></td>
				</tr>
				<tr>
					<td colspan=3><form:button type="button"
							id="searchAssignmentId">Search</form:button></td>
				</tr>
			</table>
		</div>
	</form:form>
	<div id="assignmentDiv">
		<c:if test="${not empty assignList}">
			<c:forEach var="userAssignmentMapping" items="${assignList}">
				<form:form modelAttribute="questionModelForm"
					id="assignForm_${userAssignmentMapping.assignment.assignmentId}"
					method="GET">
					<form:button type="button"
						id="${userAssignmentMapping.assignment.assignmentId}"
						class="assignButton"
						value="${userAssignmentMapping.assignment.assignmentNumber}">Assignment No. ${userAssignmentMapping.assignment.assignmentNumber}</form:button>
					<form:hidden path="question.assign.assignmentId"
						value="${userAssignmentMapping.assignment.assignmentId}" />
					<form:hidden path="user.userId"
						value="${userAssignmentMapping.user.userId}" />
					<form:hidden path="question.assign.course.courseId"
						value="${userAssignmentMapping.assignment.course.courseId}" />
				</form:form>
			</c:forEach>
		</c:if>
	</div>

</body>
<script>
	$(document).ready(function() {
		$("#searchAssignmentId").on('click', function() {
			$("#serachForm").submit();
		});

		var min = new Date().getFullYear() - 6,
			max = min + 30,
			select = document.getElementById('yearId');

		for (var i = min; i <= max; i++) {
			var opt = document.createElement('option');
			opt.value = i;
			opt.innerHTML = i;
			select.appendChild(opt);
		}

		$(".assignButton").on('click', function() {
			var buttonVal = $('#' + $(this).attr("id")).val();
			var buttonId = $(this).attr("id");
			$('#assignForm_' + buttonId).attr("action", "../assignment/findQues");
			$('#assignForm_' + buttonId).submit();
		});
	});
</script>
</html>