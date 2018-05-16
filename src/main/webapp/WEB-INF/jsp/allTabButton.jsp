<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Tabs</title>

</head>
<body>
	<table>
		<tr>
			<c:if test="${type==4}">
				<td><form:form method="post" action="../misc/scm">
						<input type="submit" value="User Course Mapping" />
					</form:form></td>
				<td><form:form method="post" action="../misc/coursesemmap">
						<input type="submit" value="Course Semester Mapping" />
					</form:form></td>
				<td><form:form method="post" action="../misc/course">
						<input type="submit" value="Add Course" />
					</form:form></td>
				<td><form:form method="post" action="../user/accountStatus">
						<input type="submit" value="Activate Account" />
					</form:form></td>
			</c:if>
			<c:if test="${type==1}">
				<td><form:form method="post" action="../misc/home">
						<input type="submit" value="Home" />
					</form:form></td>
				<td><form:form method="post"
						action="../assignment/searchAssignment">
						<input type="submit" value="Search Assignment" />
					</form:form></td>
			</c:if>
			<c:if test="${type==2 || type ==3}">
				<td><form:form method="post" action="../misc/home">
						<input type="submit" value="Home" />
					</form:form></td>
				<td><form:form method="post" action="../assignment/add">
						<input type="submit" value="Add Assignment" />
					</form:form></td>
				<td><form:form method="post" action="../assignment/searchSA">
						<input type="submit" value="Student Assignment" />
					</form:form></td>
			</c:if>
			<td><form:form method="post" action="../forum/fpage">
					<input type="submit" value="Discusson Forum" />
				</form:form></td>
			<td><form:form method="post" action="../uCheck/logout">
					<input type="submit" value="Logout" />
				</form:form></td>
		</tr>
	</table>
</body>
</html>