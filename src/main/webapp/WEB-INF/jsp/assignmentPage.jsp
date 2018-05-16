<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Assignments</title>
<script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<div id="assignmentDiv">
		<c:if test="${not empty assignmentList}">
			<c:forEach var="assignment" items="${assignmentList}">
				<form:form modelAttribute="questionModelForm"
					id="assignForm_${assignment.assignmentId}" method="GET">
					<form:button type="button" id="${assignment.assignmentId}"
						class="assignButton" value="${assignment.assignmentNumber}">Assignment No. ${assignment.assignmentNumber}</form:button>
					<form:hidden path="question.assign.assignmentId"
						value="${assignment.assignmentId}" />
					<form:hidden path="user.userId" value="${uId}" />
					<form:hidden path="question.assign.course.courseId" value="${assignment.course.courseId}" />
				</form:form>
			</c:forEach>
		</c:if>
	</div>

</body>
<script>
$(document).ready(function(){
    $(".assignButton").on('click',function(){
        var buttonVal = $('#'+$(this).attr("id")).val();
        var buttonId = $(this).attr("id");
        $('#assignForm_'+buttonId).attr("action", "../assignment/findQues");
        $('#assignForm_'+buttonId).submit();
    });
});
</script>
</html>