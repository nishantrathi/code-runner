<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activate Account</title>
<script src="../js/jquery-3.3.1.js"></script>
<style>
.tdClass {
	padding: 15px;
	text-align: left;
}

.tableClass {
	border-collapse: collapse;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<div>
		<form:form id="userFormId" modelAttribute="userForm"
			action="../user/searchAccount" method="GET">
			<div>
				<table width="100%">
					<tr>
						<td><form:label path="firstName">First Name</form:label></td>
						<td><form:label path="lastName">Last Name</form:label></td>
						<td><form:label path="cwid">CWID</form:label></td>
						<td><form:label path="status">Account Status</form:label></td>
					</tr>
					<tr>
						<td><form:input path="firstName"></form:input></td>
						<td><form:input path="lastName"></form:input></td>
						<td><form:input id="cwid" path="cwid"></form:input></td>
						<td><form:select path="status">
								<form:option value="0" label="Select" />
								<form:options items="${statusList}" />
							</form:select></td>
					</tr>
					<tr>
						<td colspan=1></td>
						<td><form:button type="button" id="searchId">Search</form:button></td>
						<td><input name="page" type="hidden" id="pageId" value="0" /></td>
					</tr>
				</table>
			</div>
			<div id="userDiv" class="solid">
				<c:if test="${not empty userList}">
					<table border="1px" width="100%">
						<tr>
							<th class="tdClass">First Name</th>
							<th class="tdClass">Last Name</th>
							<th class="tdClass">CWID</th>
							<th class="tdClass">Type</th>
							<th class="tdClass">Created On</th>
						</tr>

						<c:forEach var="user" items="${userList}">
							<tr id="${user.userId}">
								<td class="tdClass"><form:label path="firstName"
										id="fNameId">${user.firstName}</form:label></td>
								<td class="tdClass"><form:label path="lastName"
										id="lNameId">${user.lastName}</form:label></td>
								<td class="tdClass"><form:label path="cwid" id="cwid">${user.cwid}</form:label></td>
								<c:if test="${user.type==1}">
									<td class="tdClass"><label>Student</label></td>
								</c:if>
								<c:if test="${user.type==2}">
									<td class="tdClass"><label>Instructor</label></td>
								</c:if>
								<c:if test="${user.type==3}">
									<td class="tdClass"><label>Tutor</label></td>
								</c:if>
								<td class="tdClass"><form:label path="createdOn">${user.createdOn}</form:label></td>
								<td>
								<c:if test="${user.status=='New' || user.status=='Deactivated'}">
										<form:button id="button_${user.userId}" class="statusButton"
											value="${user.status}" type="button">Activate</form:button>
									</c:if> <c:if test="${user.status=='Activated'}">
										<form:button id="button_${user.userId}" class="statusButton"
											value="${user.status}" type="button">Deactivate</form:button>
									</c:if></td>
							</tr>
						</c:forEach>
						<tr id="prevNext">
							<td colspan=1></td>
							<td><form:button type="button" value="${current}"
									id="prevId">Previous</form:button></td>
							<td><c:out value="${current}" /> of <c:out
									value="${totalPages}" /></td>
							<td><form:button type="button" value="${totalPages}"
									id="nextId">Next</form:button></td>
							<td></td>
						</tr>

					</table>
				</c:if>
			</div>
		</form:form>
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

		$(".statusButton").on('click', function() {
			var buttonVal = $(this).val();
			var buttonId = $(this).attr('id');
			var uId = buttonId.split("_");
			var statusJson = {};
			if (buttonVal == 'New' || buttonVal == 'Deactivated') {
				statusJson["status"] = "Activated";
			} else {
				statusJson["status"] = "Deactivated";
			}
			statusJson["user"] = uId[1];
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : '../user/activate',
				data : JSON.stringify(statusJson),
				datatype : 'json',
				success : function(responseText) {
					console.log(responseText);
					if(responseText){
						alert("Action performed successfully.");
					/* 	var rowId = jQuery(this).parent().parent().attr('id');
						if(rowId==uId[1])
							jQuery('#'+rowId).remove(); */
					}
						
				}
			});
		});

	});

	function submitAction() {
		$('#userFormId').submit();
	}
</script>
</html>