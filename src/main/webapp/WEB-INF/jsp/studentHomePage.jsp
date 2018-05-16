<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course</title>
<script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<div id="courseDiv">
		<c:if test="${not empty studentCourseList}">
			<c:forEach var="course" items="${studentCourseList}">
				<form:form modelAttribute="assignModelForm" id="course_${course.courseId}" class="courseForm"
					method="POST">
					<form:button type="button" id="${course.courseId}"
						class="courseButton" value="${course.courseId}">${course.courseCode}</form:button>
					<form:hidden path="course.courseId" value="${course.courseId}" />
				</form:form>
			</c:forEach>
		</c:if>
	</div>

</body>
<script>
$(document).ready(function(){
    $(".courseButton").on('click',function(){
        var buttonVal = $('#'+$(this).attr("id")).val();
        var buttonId = $(this).attr("id");
        $('#course_'+buttonId).attr("action", "../assignment/findAssign");
        $('#course_'+buttonId).submit();
    });
});
</script>
</html>