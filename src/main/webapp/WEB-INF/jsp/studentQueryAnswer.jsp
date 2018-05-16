<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Answer</title>
<link href="<c:url value="/css/forumAnswer.css" />" rel="stylesheet">
<script src="../js/jquery-3.3.1.js"></script>

</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>

	<form:form modelAttribute="ansForm">
		<div id="parent">
			<div id="questionDiv">
				<div id="questionInfo">${query.query}</div>
				<div class="codeButton" id="answerId">Answer</div>
				<input type=hidden id="queryId" value="${query.queryId}" />
			</div>
			<div id="answerDiv">
				<c:if test="${not empty answerList}">
					<c:forEach var="queryAnswer" items="${answerList}">
						<div id="${queryAnswer.queryAnsId}" class="individual">
							<div id="ans_${queryAnswer.queryAnsId}" class="left answerDiv">${queryAnswer.queryAnswer}</div>
							<div id="info_${queryAnswer.queryAnsId}" class="right infoDiv">
								<div class="userInfo">Answered by:</div>
								<div class="userInfo">${queryAnswer.user.firstName}
									&nbsp;${queryAnswer.user.lastName}</div>
								<div class="userInfo">${queryAnswer.user.cwid}</div>
								<div class="userInfo">${queryAnswer.submittedDate}</div>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty answerList}">
					<div class="emptyList left">You are the first person to write
						the answer.</div>
				</c:if>
			</div>

			<div id="myModal" class="ansQuery">

				<!-- Modal content -->
				<div class="modal-content">
					<span class="close">&times;</span>
					<div class="queryDiv">
						<textarea id="answerTextAreaId" class="queryArea"></textarea>
					</div>
					<div class="popupAnswerButton" id="saveAnswerId">Save Answer</div>
				</div>

			</div>
		</div>
	</form:form>
</body>
<script>
	$(document).ready(function() {
		jQuery('#saveAnswerId').on('click', function() {
			saveQueryAnswer();
		});
	});

	//Get the modal
	var modal = document.getElementById('myModal');

	// Get the button that opens the modal
	var btn = document.getElementById("answerId");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks the button, open the modal 
	jQuery("#answerId").on("click", function() {
		modal.style.display = "block";
	});

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

	function saveQueryAnswer() {
		var ansQuery = {};
		ansQuery['queryId'] = jQuery('#queryId').val();
		ansQuery['queryAnswer'] = jQuery('#answerTextAreaId').val();
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '../forum/savequeryanswer',
			data : JSON.stringify(ansQuery),
			datatype : 'json',
			success : function(responseText) {
				console.log(responseText);
				var outputJson = JSON.parse(responseText);
				if ('success' == outputJson["result"]) {
					alert("Answer saved successfully.")
					var output = outputJson["output"];
					createDynamicHtml(output);
					jQuery('#answerTextAreaId').val('');
					$("#myModal").css("display", "none");
					$(".emptyList").css("display", "none");
				} else {
					alert("Save your query again");
					$("#myModal").css("display", "none");
				}

			}
		});
	}

	function createDynamicHtml(ansJson) {
		var individualDiv = document.createElement('div');
		$(individualDiv).addClass("individual");
		$(individualDiv).attr("id", ansJson["queryAnsId"]);

		var leftDiv = document.createElement('div');
		$(leftDiv).addClass("left answerDiv");
		$(leftDiv).attr("id", 'ans_' + ansJson["queryAnsId"]);
		$(leftDiv).html(ansJson["answer"]);

		var rightDiv = document.createElement('div');
		$(rightDiv).addClass("right infoDiv");
		$(rightDiv).attr("id", 'info_' + ansJson["queryAnsId"]);

		var answeredByDiv = document.createElement('div');
		$(answeredByDiv).addClass("userInfo");
		$(answeredByDiv).html("Answered by:");
		$(rightDiv).append(answeredByDiv);

		var nameDiv = document.createElement('div');
		$(nameDiv).addClass("userInfo");
		$(nameDiv).html(ansJson["name"]);
		$(rightDiv).append(nameDiv);

		var cwidDiv = document.createElement('div');
		$(cwidDiv).addClass("userInfo");
		$(cwidDiv).html(ansJson["cwid"]);
		$(rightDiv).append(cwidDiv);

		var dateDiv = document.createElement('div');
		$(dateDiv).addClass("userInfo");
		$(dateDiv).html(ansJson["date"]);
		$(rightDiv).append(dateDiv);

		$(individualDiv).append(leftDiv);
		$(individualDiv).append(rightDiv);

		$("#answerDiv").append(individualDiv);
	}
</script>
</html>