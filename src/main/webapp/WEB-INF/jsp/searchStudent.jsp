<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Student</title>
<script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
	<form:form method="GET" action="../user/search" modelAttribute="scmaping" id="scmFormId">
		<div id="searchDivId">
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
					<td><input name="page" type="hidden" id="pageId" value="0"/></td>
					<td><form:button type="button"
							id="searchId">Search Student</form:button></td>
				</tr>
			</table>
		</div>
		<div id="studentDiv">
			<table>
				<c:if test="${not empty studentList}">
					<c:forEach var="user" items="${studentList}">
						<tr id="${user.userId}">
							<td><form:radiobutton path="user.userId" id="${user.userId}"
									value="${user.userId}" /></td>
							<td><form:label path="user.firstName" id="fNameId">${user.firstName}</form:label></td>
							<td><form:label path="user.lastName" id="NameId">${user.lastName}</form:label></td>
							<td><form:label path="user.cwid" id="cwid">${user.cwid}</form:label></td>
						</tr>
					</c:forEach>
					<tr id="prevNext">
						<td><form:button type="button" value ="${current}" id="prevId">Previous</form:button></td>
						<td><c:out value="${current}"/> of <c:out value="${totalPages}"/></td>
						<td><form:button type="button" value ="${totalPages}" id="nextId">Next</form:button></td>
						<td></td>
					</tr>
				</c:if>
			</table>
		</div>
	</form:form>
</body>
<script>
	$(document).ready(function() {
		$("#searchId").on('click', function() {
			//$('#scmFormId').attr("action", "../user/search");
			//$('#scmFormId').submit();
			$("#pageId").val(0);
			submitAction();
		});
		
		$("#prevId").on('click', function() {
			var val=$("#prevId").val();
			if(val==0)
				alert("You are on first page");
			else{
				$("#pageId").val(Number(val)-1);
				submitAction();
			}
		});
		
		$("#nextId").on('click', function() {
			var total= $("#nextId").val()
			var current = $("#prevId").val();
			if(current==total)
				alert("You are on last page");
			else{
				$("#pageId").val(Number(current)+1);
				submitAction();
			}
				
		});
	});
	
	function submitAction(){
		//console.log(pageNumber);
		//var url = $('#scmFormId').attr("action")+pageNumber;
		//$('#scmFormId').attr("action",url );
		$('#scmFormId').submit();
	}
</script>
</html>