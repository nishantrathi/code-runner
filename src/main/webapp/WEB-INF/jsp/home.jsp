<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<style>
#msgId{
background-color:lightblue;
}
</style>
<script src="../js/jquery-3.3.1.js"></script>
<script>
setTimeout(function() {
	  $("#msgId").fadeOut().empty();
	}, 5000);
</script>
</head>
<body>
	<Div>
		<h1>Code Runner</h1>
	</Div>

	<div id="msgId">${msg}</div>
	<div id="login">
		<form:form method="post" modelAttribute="myForm" id="loginForm"
			action="../uCheck/login">
			<table>
				<tr>
					<td><form:label path="login.userName">Username</form:label></td>
					<td><form:input id="userName" path="login.userName" /></td>
				</tr>
				<tr>
					<td><form:label path="login.password">Password</form:label></td>
					<td><form:input id="pwd" type="password" path="login.password" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Login" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	<div id="signUp">
		<form:form method="post" modelAttribute="myForm" id="signupForm"
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
					<td><form:input id="newUser" path="login.userName" /></td>
				</tr>
				<tr>
					<td><form:label path="login.password">Password</form:label></td>
					<td><form:input id="newPwd" type="password" path="login.password" /></td>
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

</body>
</html>