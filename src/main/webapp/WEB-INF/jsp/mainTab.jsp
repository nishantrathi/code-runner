<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="../js/jquery-3.3.1.js"></script>
<meta http-equiv="Content-Type" name="viewport"
	content="width=device-width, initial-scale=1;text/html; charset=ISO-8859-1">
<title>Tab</title>
<style>
body {
	font-family: Arial;
}

/* Style the tab */
.tab {
	overflow: hidden;
	border: 1px solid #ccc;
	background-color: #f1f1f1;
}

/* Style the buttons inside the tab */
.tab button {
	background-color: inherit;
	float: left;
	border: none;
	outline: none;
	cursor: pointer;
	padding: 14px 16px;
	transition: 0.3s;
	font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
	background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
	display: none;
	padding: 6px 12px;
	border: 1px solid #ccc;
	border-top: none;
}
</style>
</head>
<body>

	<p>Code Runner</p>

	<div class="tab">
		<button class="tablinks" id="defaultOpen" onclick="loadStudentReg()">Student
			Registration</button>
		<button class="tablinks" onclick="courseAdd()">Add Course</button>
		<button class="tablinks" onclick="studentCourseMap()">Student
			Course Mapping</button>
	</div>

	<div id="studentRegistrationId" class="tabcontent">
		<form:form method="post" modelAttribute="myForm"
			action="../user/insert">
			<table>
				<tr>
					<td><form:label path="user.firstName">First Name</form:label></td>
					<td><form:input path="user.firstName" /></td>
				</tr>
				<tr>
					<td><form:label path="user.lastName">Last Name</form:label></td>
					<td><form:input path="user.lastName" /></td>
				</tr>
				<tr>
					<td><form:label path="user.emailId">Email ID</form:label></td>
					<td><form:input path="user.emailId" /></td>
				</tr>
				<tr>
					<td><form:label path="user.cwid">CWID</form:label></td>
					<td><form:input path="user.cwid" /></td>
				</tr>
				<tr>
					<td><form:label path="login.userName">Username</form:label></td>
					<td><form:input path="login.userName" /></td>
				</tr>
				<tr>
					<td><form:label path="login.password">Password</form:label></td>
					<td><form:input path="login.password" /></td>
				</tr>
				<tr>
					<td><form:radiobutton path="user.type" value="1" />Student</td>
					<td><form:radiobutton path="user.type" value="2" />Instructor</td>
					<td><form:radiobutton path="user.type" value="3" />Tutor</td>
				</tr>
				<tr>
					<td><input type="submit" value="Sign Up" /></td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="addCourseId" class="tabcontent">
		<form:form method="post" modelAttribute="course"
			action="../misc/iCourse">
			<table>
				<tr>
					<td><form:label path="courseName">Course Name</form:label></td>
					<td><form:input path="courseName" /></td>
				</tr>
				<tr>
					<td><form:label path="courseCode">Course Code</form:label></td>
					<td><form:input path="courseCode" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Add Course" /></td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="studentCourseMappingId" class="tabcontent">
		<h3>Tokyo</h3>
		<p>Tokyo is the capital of Japan.</p>
	</div>

	<script>
	
		function loadStudentReg() {
			jQuery('#studentRegistrationId').show()
		}
	
		function courseAdd() {
			jQuery('#addCourseId').show()
		}
	
		function studentCourseMap() {
			alert("hi map");
		}
	
		document.getElementById("defaultOpen").click();
	</script>

</body>
</html>
