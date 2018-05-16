<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Mapping</title>
<script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form method="POST" modelAttribute="scmaping"
		action="../misc/insertSCM" id="scmFormId">
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
			</tr>
			<tr>
				<td><form:label path="user.firstName">First Name</form:label></td>
				<td><form:label path="user.lastName">Last Name</form:label></td>
				<td><form:label path="user.cwid">CWID</form:label></td>
			</tr>
			<tr>
				<td><form:input path="user.firstName" id="firstNameId" /></td>
				<td><form:input path="user.lastName" id="lastNameId" /></td>
				<td><form:input path="user.cwid" id="cwidId" /></td>
				<td rowspan="1"><form:button type="button" name="Search"
						id="searchId">Search Student</form:button></td>
			</tr>
			<tr>
				<td><form:input path="user.userId" id="studentId" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Map" /></td>
				<td colspan="2"><form:button type="button" name="submit"
						id="submitId">Submit</form:button></td>
			</tr>
		</table>
	</form:form>
</body>
<script>
	$(document).ready(function() {
		$('#searchId').on('click', function() {
			var search = {}
			search['firstName'] = $('#firstNameId').val();
			search['lastName'] = $('#lastNameId').val();
			search['cwid'] = $('#cwidId').val();
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : '../user/searchStudent',
				data : JSON.stringify(search),
				datatype : 'json',
				success : function(responseText) {
					console.log(responseText[0].userId);
					jQuery('#studentId').val(responseText[0].userId);
				}
			});
		});

		$("#submitId").click(function() {
			$("#scmFormId").submit();
		});
	});
</script>
</html>