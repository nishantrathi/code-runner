<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Course</title>
<script src="../js/jquery-3.3.1.js"></script>
<style>
.solid {
	border-style: solid;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form method="GET" action="../user/search"
		modelAttribute="scmaping" id="scmFormId">
		<div id="searchDivId" class="solid">
			<table>
				<tr>
					<td><form:label path="user.firstName">First Name</form:label></td>
					<td><form:label path="user.lastName">Last Name</form:label></td>
					<td><form:label path="user.cwid">CWID</form:label></td>
				</tr>
				<tr>
					<td><form:input path="user.firstName" id="firstNameId" /></td>
					<td><form:input path="user.lastName" id="lastNameId" /></td>
					<td><form:input path="user.cwid" id="cwidId" /></td>
					<td><input name="page" type="hidden" id="pageId" value="0" /></td>
				</tr>
				<tr>
					<td><form:radiobutton path="user.type" value="1" />Student</td>
					<td><form:radiobutton path="user.type" value="2" />Instructor</td>
					<td><form:radiobutton path="user.type" value="3" />Tutor</td>
				</tr>
				<tr>
					<td colspan=1></td>
					<td><form:button type="button" id="searchId">Search Student</form:button></td>
				</tr>
			</table>
		</div>
		<div id="studentDiv" class="solid">
			<table>
				<c:if test="${not empty studentList}">
					<c:forEach var="user" items="${studentList}">
						<tr id="${user.userId}">
							<td><form:radiobutton class="radioButton" path="user.userId"
									id="${user.userId}" value="${user.userId}" /></td>
							<td><form:label path="user.firstName" id="fNameId">${user.firstName}</form:label></td>
							<td><form:label path="user.lastName" id="NameId">${user.lastName}</form:label></td>
							<td><form:label path="user.cwid" id="cwid">${user.cwid}</form:label></td>
						</tr>
					</c:forEach>
					<tr id="prevNext">
						<td><form:button type="button" value="${current}" id="prevId">Previous</form:button></td>
						<td><c:out value="${current}" /> of <c:out
								value="${totalPages}" /></td>
						<td><form:button type="button" value="${totalPages}"
								id="nextId">Next</form:button></td>
						<td></td>
					</tr>
				</c:if>
			</table>
		</div>
	</form:form>
	<form:form method="POST" modelAttribute="scmaping"
		action="../misc/insertSCM" id="mapFormId">
		<div id="mappingDiv" class="solid">
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
					<td colspan=3><form:button type="button" id="mapUserId">Map User</form:button></td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
<script>
	$(document).ready(function() {
		$("#searchId").on('click', function() {
			//$('#scmFormId').attr("action", "../user/search");
			//$('#scmFormId').submit();
			$("#pageId").val(1);
			submitAction();
		});

		$("#prevId").on('click', function() {
			var val = $("#prevId").val();
			if (val == 0)
				alert("You are on first page");else {
				$("#pageId").val(Number(val) - 1);
				submitAction();
			}
		});

		$("#nextId").on('click', function() {
			var total = $("#nextId").val()
			var current = $("#prevId").val();
			if (current == total)
				alert("You are on last page");else {
				$("#pageId").val(Number(current) + 1);
				submitAction();
			}
		});

		$(".radioButton").on('click', function() {
			var userId = $(this).attr("id");
			$("#hiddenUserId").val(userId);
		});

		$("#mapUserId").on('click', function() {
			$('#mapFormId').submit();
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
	});

	function submitAction() {
		//console.log(pageNumber);
		//var url = $('#scmFormId').attr("action")+pageNumber;
		//$('#scmFormId').attr("action",url );
		$('#scmFormId').submit();
	}
</script>
</html>