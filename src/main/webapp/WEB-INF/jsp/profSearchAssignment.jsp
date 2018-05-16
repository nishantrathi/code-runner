<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<script src="../js/jquery-3.3.1.js"></script>
<style>
.tdClass {
	padding: 15px;
	text-align: center;
}

.tableClass {
	border-collapse: collapse;
}

.solid {
	border-style: solid;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form id="assignmentFormId" modelAttribute="userAssignModel"
		action="../user/searchstudent" method="GET">
		<div class="solid">
			<table>
				<tr>
					<td><form:label path="user.firstName">First Name</form:label></td>
					<td><form:label path="user.lastName">Last Name</form:label></td>
					<td><form:label path="user.cwid">CWID</form:label></td>
				</tr>
				<tr>
					<td><form:input path="user.firstName"></form:input></td>
					<td><form:input path="user.lastName"></form:input></td>
					<td><form:input id="cwid" path="user.cwid"></form:input></td>
				</tr>
				<tr>
					<td colspan=1></td>
					<td><form:button type="button" id="searchId">Search</form:button></td>
					<td><input name="page" type="hidden" id="pageId" value="0" /></td>
				</tr>
			</table>
		</div>
		<div id="userDiv" class="solid">
			<c:if test="${not empty studentList}">
				<table class="tableClass" width="100%">
					<tr>
						<th class="tdClass">Select</th>
						<th class="tdClass">First Name</th>
						<th class="tdClass">Last Name</th>
						<th class="tdClass">CWID</th>
						<th class="tdClass">Type</th>
					</tr>

					<c:forEach var="user" items="${studentList}">
						<tr id="${user.userId}">
							<td class="tdClass"><form:radiobutton class="radioButton" path="user.userId"
									id="${user.userId}" value="${user.userId}" /></td>
							<td class="tdClass"><form:label path="user.firstName"
									id="fNameId">${user.firstName}</form:label></td>
							<td class="tdClass"><form:label path="user.lastName"
									id="lNameId">${user.lastName}</form:label></td>
							<td class="tdClass"><form:label path="user.cwid" id="cwid">${user.cwid}</form:label></td>
							<c:if test="${user.type==1}">
								<td class="tdClass"><label>Student</label></td>
							</c:if>
						</tr>
					</c:forEach>
					<tr id="prevNext">
						<td colspan=2></td>
						<td><form:button type="button" value="${current}" id="prevId">Previous</form:button>
							&nbsp;&nbsp;<c:out value="${current}" /> of <c:out
								value="${totalPages}" />&nbsp;&nbsp; <form:button type="button"
								value="${totalPages}" id="nextId">Next</form:button></td>
						<td colspan=2></td>
					</tr>
				</table>
			</c:if>
		</div>
	</form:form>
	<form:form method="POST" modelAttribute="userAssignModel"
		action="../assignment/searchstudentassign" id="csuFormId">
		<div id="courseDiv" class="solid">
			<table>
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
					<td colspan=3><form:hidden id="hiddenUserId" value=""
							path="user.userId" /></td>
					<td colspan=3><form:button type="button" id="studentAssignId">Search Assignment</form:button></td>
				</tr>
			</table>
		</div>
	</form:form>
	<div id="assignmentDiv">
		<c:if test="${not empty assignList}">
			<c:forEach var="userAssignmentMapping" items="${assignList}">
				<form:form modelAttribute="questionModelForm"
					id="assignForm_${userAssignmentMapping.assignment.assignmentId}" method="GET">
					<form:button type="button" id="${userAssignmentMapping.assignment.assignmentId}"
						class="assignButton" value="${userAssignmentMapping.assignment.assignmentNumber}">Assignment No. ${userAssignmentMapping.assignment.assignmentNumber}</form:button>
					<form:hidden path="question.assign.assignmentId"
						value="${userAssignmentMapping.assignment.assignmentId}" />
					<form:hidden path="user.userId" value="${userAssignmentMapping.user.userId}" />
					<form:hidden path="question.assign.course.courseId"
						value="${userAssignmentMapping.assignment.course.courseId}" />
				</form:form>
			</c:forEach>
		</c:if>
	</div>

</body>
<script>
	$(document).ready(function() {
		var cwidVal = $("#cwid").val();
		if (cwidVal == 0)
			$("#cwid").val('');

		$("#searchId").on('click', function() {
			$("#pageId").val(1);
			submitAction();
		});

		$(".radioButton").on('click', function() {
			var userId = $(this).attr("id");
			$("#hiddenUserId").val(userId);
		});

		var min = new Date().getFullYear() - 6,max = min + 30,select = document.getElementById('yearId');

		for (var i = min; i <= max; i++) {
			var opt = document.createElement('option');
			opt.value = i;
			opt.innerHTML = i;
			select.appendChild(opt);
		}
		
		$("#studentAssignId").on('click', function() {
			$('#csuFormId').submit();
		});
		
		$(".assignButton").on('click', function() {
			var buttonVal = $('#' + $(this).attr("id")).val();
			var buttonId = $(this).attr("id");
			$('#assignForm_' + buttonId).attr("action", "../assignment/findstudentQues");
			$('#assignForm_' + buttonId).submit();
		});
	});

	function submitAction() {
		$('#assignmentFormId').submit();
	}
</script>
</html>