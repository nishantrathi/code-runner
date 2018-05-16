<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Discussion Forum</title>
<link href="<c:url value="/css/forum.css" />" rel="stylesheet">
<script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form method="GET" modelAttribute="queryForm" id="queryFormId"
		action="../forum/query">
		<div id="searchDivId">
			<table width=100%>
				<tr>
					<td><form:label path="course.courseId">Course</form:label></td>
					<td><form:select class="courseClass" path="course.courseId">
							<form:option value="0" label="Select" />
							<form:options items="${courseList}" />
						</form:select></td>
					<td><form:label path="assignment.assignmentId">Assignment No.</form:label></td>
					<td><form:select id="assignmentId" path="assignment.assignmentId">
							<option value="">-- Select --</option>
						</form:select></td>
				</tr>
				<tr>
					<td colspan=2><form:button type="button" id="searchQueryId">Search Queries</form:button></td>
				</tr>
			</table>
		</div>
	</form:form>
	<div id="queryDivId">
		<c:if test="${not empty queryList}">
			<c:forEach var="studentQuery" items="${queryList}">
				<div id="${studentQuery.queryId}" class="individual">
					<form:form modelAttribute="queryAnswer" id="form_${studentQuery.queryId}" method="GET" action="../forum/queryanswers">
						<div id="${studentQuery.question.questionId}" class="question">${studentQuery.question.questionDetail}</div>
						<div id="queryId_${studentQuery.queryId}" class="query">${studentQuery.query}</div>
						<div id="user_${studentQuery.user.userId}" class="info">${studentQuery.user.firstName}
							${studentQuery.user.lastName} | ${studentQuery.user.cwid} |
							${studentQuery.submittedDate}</div>
						<div id="hiddenDiv">
							<form:hidden path="studentQuery.queryId" value="${studentQuery.queryId}"></form:hidden>
						</div>
					</form:form>
				</div>
			</c:forEach>
		</c:if>
	</div>
</body>
<script>
	$(document).ready(function() {
		$(".courseClass").on("change", function() {
			assignmentAjax();
		});

		$("#searchQueryId").on('click', function() {
			$('#queryFormId').submit();
		});

		$(".individual").on('click', function() {
			var formId = $(this).attr("id");
			$('#form_' + formId).attr("action", "../forum/queryanswers");
			//window.open('../forum/queryanswers','_blank');
			$('#form_' + formId).submit();
		});
	});
	
	function assignmentAjax(){
		var value = $(".courseClass option:selected").val();
		if (value == 0)
			alert("Select course from the list");
		else {
			var courseJson = {};
			courseJson['courseVal'] = value;
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : '../assignment/assignList',
				data : JSON.stringify(courseJson),
				datatype : 'json',
				success : function(responseText) {
					console.log(responseText);
					var assignJson = JSON.parse(responseText);
					loadAssignmentDropdownBox(assignJson);
				}
			});
		}
	}
	
	function loadAssignmentDropdownBox(assignJson){
		var html = '<option value="">-- Select --</option>';
		var len = assignJson.length;
		for (var i = 0; i < len; i++) {
			var key = Object.keys(assignJson[i])[0];
			html += '<option value="' + key + '">' + assignJson[i][key] + '</option>';
		}
		html += '</option>';
		$('#assignmentId').html(html);
	}
</script>
</html>