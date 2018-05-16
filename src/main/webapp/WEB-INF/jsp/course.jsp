<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/allTabButton.jsp" %>
	<form:form method="post" modelAttribute="course" action="../misc/iCourse">
		<table>
			<tr>
				<td><form:label path="courseName">Course Name</form:label></td>
				<td><form:input path="courseName"/></td>
			</tr>
			<tr>
				<td><form:label path="courseCode">Course Code</form:label></td>
				<td><form:input path="courseCode" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Course"/></td>
			</tr>
		</table>
	</form:form>
</body>
</html>