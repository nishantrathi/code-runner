<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question</title>
<link href="<c:url value="/css/question.css" />" rel="stylesheet">
<script src="../js/jquery-3.3.1.js"></script>
<script>
	var ansFlag = {};
	/* var str = '${questionList}';
	str = str.replace(/\\n/g, "\\n")  
	.replace(/\\'/g, "\\'")
	.replace(/\\"/g, '\\\"')
	.replace(/\\&/g, "\\&")
	.replace(/\\r/g, "\\r")
	.replace(/\\t/g, "\\t")
	.replace(/\\b/g, "\\b")
	.replace(/\\f/g, "\\f");
	console.log(str); */
	var quesAnsJson = JSON.parse('${questionList}');
	$(document).ready(function() {

		console.log(quesAnsJson);
		questionSectionForFirstQues(quesAnsJson);

		jQuery(".quesChange").on("click", function() {
			var quesId = $(this).attr("id");
			var prevActiveQues = $(".active").attr("id");
			if (ansFlag[prevActiveQues] == true || jQuery("#answerTextAreaId").val() == "") {
				$('#' + prevActiveQues).removeClass("active");
				$(this).addClass("active");
				var quesArr = quesAnsJson["arr"];
				for (i = 0; i < quesArr.length; i++) {
					var quesKeys = Object.keys(quesArr[i])[0];
					if (quesKeys == quesId) {
						var answerJson = quesArr[i][quesKeys];
						$("#questionId").html(answerJson["questionInfo"]);
						//$("#answerTextAreaId").val(answerJson["ans"]);
						if (answerJson["ans"] != "") {
							var answerFromBin = binaryToString(answerJson["binary"]);
							$("#answerTextAreaId").val(answerFromBin);
						} else {
							$("#answerTextAreaId").val('');
						}
						$("#ansId").val(answerJson["ansId"]);
						$("#commentTextAreaId").val(answerJson["comment"]);
						$("#outputTextAreaId").val('');
					}
				}
			} else {
				alert("Save the code first");
			}
		});
		
		
		// Get the modal
		var modal = document.getElementById('myModal');

		// Get the button that opens the modal
		var btn = document.getElementById("queryId");

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		// When the user clicks the button, open the modal 
		jQuery("#queryId").on("click", function() {
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
	});



	function questionSectionForFirstQues(quesAnsJson) {
		var firstKey = "";
		var quesAnsArr = quesAnsJson["arr"];
		var questionDiv = document.createElement('div');
		$(questionDiv).html("Assignment No." + quesAnsJson["assignNumber"]);
		$(questionDiv).addClass("assign");
		$("#questionListId").append(questionDiv);
		questionDiv = document.createElement('div');
		$(questionDiv).html("Time Left " + timer(quesAnsJson["endDate"]));
		$(questionDiv).attr("id", "timerId");
		$(questionDiv).addClass("assign");
		$("#questionListId").append(questionDiv);
		for (i = 0; i < quesAnsArr.length; i++) {
			var quesKeys = Object.keys(quesAnsArr[i]);
			questionDiv = document.createElement('div');
			if (i == 0) {
				firstKey = quesKeys[0];
			}
			var key = quesKeys[0];
			ansFlag[quesKeys[0]] = true;
			$(questionDiv).attr('id', quesKeys[0]);
			$(questionDiv).html("Question No." + quesAnsArr[i][key]["questionNum"]);
			$(questionDiv).addClass("question quesChange");
			if (i == 0)
				$(questionDiv).addClass("active");
			$("#questionListId").append(questionDiv);
		}
		var answerJson = quesAnsArr[0][firstKey];
		$("#questionId").html(answerJson["questionInfo"]);
		if (answerJson["binary"] != "") {
			var answerFromBin = binaryToString(answerJson["binary"]);
			$("#answerTextAreaId").val(answerFromBin);
		} else {
			$("#answerTextAreaId").val('');
		}

		$("#commentTextAreaId").val(answerJson["comment"]);
		$("#ansId").val(answerJson["ansId"]);
		$("#hiddenAssignId").val(quesAnsJson["assignId"]);
		$("#hiddenCourseId").val(quesAnsJson["courseId"]);
		$("#hiddenUserId").val(quesAnsJson["userId"]);

	}


	function binaryToString(str) {
		// Removes the spaces from the binary string
		//str = str.replace(/\s+/g, '');
		// Pretty (correct) print binary (add a space every 8 characters)
		//str = str.match(/.{1,8}/g).join(" ");

		var newBinary = str.split(" ");
		var binaryCode = [];

		for (i = 0; i < newBinary.length; i++) {
			//console.log("String -->" + String.fromCharCode(parseInt(newBinary[i], 2)));
			binaryCode.push(String.fromCharCode(parseInt(newBinary[i], 2)));
		}
		return binaryCode.join("");
	}

	function timer(dt) {
		console.log(dt)
		var countDownDate = new Date(dt).getTime();

		// Update the count down every 1 second
		var x = setInterval(function() {

			// Get todays date and time
			var now = new Date().getTime();

			// Find the distance between now an the count down date
			var distance = countDownDate - now;

			// Time calculations for days, hours, minutes and seconds
			var days = Math.floor(distance / (1000 * 60 * 60 * 24));
			var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
			var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
			var seconds = Math.floor((distance % (1000 * 60)) / 1000);

			// Output the result in an element with id="demo"
			document.getElementById("timerId").innerHTML = "Time left " + days + "d " + hours + "h "
				+ minutes + "m " + seconds + "s ";

			// If the count down is over, write some text 
			if (distance < 0) {
				clearInterval(x);
				document.getElementById("timerId").innerHTML = "Time Expired";
				jQuery("#saveId").remove();
			}
		}, 1000);
	}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/allTabButton.jsp"%>
	<form:form modelAttribute="answerModelForm">
		<div id="questionAnswerId">
			<div class="leftCol solid">
				<div id="questionListId" class="questionClass solid"></div>
				<div class="commentClass solid">
					<textarea id="commentTextAreaId" class="area"></textarea>
				</div>
				<c:if test="${type==2 || type ==3}">
					<div class="commentButton" id="commentId">Save Comment</div>
				</c:if>
			</div>
			<div class="rightCol solid">
				<div id="questionId" class="rightQuestion solid">Question</div>
				<div id="answerId" class="rightAnswer solid">
					<textarea id="answerTextAreaId" class="area" rows="25" cols="50"></textarea>
					<input type="hidden" id="ansId" value="" />
				</div>
				<div class="rightButton solid">
					<c:if test="${type==1}">
						<div id="saveId" class="codeButton">Save & Execute</div>
					</c:if>
					<div class="codeButton" id="executeId">Execute</div>
					<div class="codeButton" id="queryId">Ask Query</div>
				</div>
				<div id="outputId" class="rightOutput solid">
					<textarea id="outputTextAreaId" class="area"></textarea>
				</div>
			</div>
			<div id="hiddenData">
				<input type="hidden" id="hiddenAssignId" value="" /> <input
					type="hidden" id="hiddenCourseId" value="" /> <input type="hidden"
					id="hiddenUserId" value="" />
			</div>
		</div>
		<div class="modal">
			<!-- Place at bottom of page -->
		</div>
		<div id="myModal" class="askQuery">

			<!-- Modal content -->
			<div class="modal-content">
				<span class="close">&times;</span>
				<div class="queryDiv"><textarea id="queryTextAreaId" class="queryArea"></textarea></div>
				<div class="codeButton" id="askId">Ask</div>
			</div>

		</div>
	</form:form>
</body>
<script>
	$(document).ready(function() {
		jQuery('#saveId').on('click', function() {
			saveAnswer(1);
		});

		jQuery('#commentId').on('click', function() {
			saveComment();
		});

		jQuery('#submitId').on('click', function() {
			saveAnswer(2);
		});

		$("#answerTextAreaId").keypress(function() {
			var quesId = jQuery('.active').attr("id");
			ansFlag[quesId] = false;
		});

		jQuery('#executeId').on('click', function() {
			executeCode();
		});
		
		jQuery('#askId').on('click', function() {
			saveQuery();
		});
	});

	function saveAnswer(val) {
		var arrLen = quesAnsJson["arr"].length;
		var quesId = jQuery('.active').attr("id");
		var binaryCode = text2Binary(jQuery('#answerTextAreaId').val());
		for (i = 0; i < arrLen; i++) {
			var qKeys = Object.keys(quesAnsJson["arr"][i]);
			if (qKeys[0] == quesId) {
				quesAnsJson["arr"][i][quesId]["ans"] = jQuery('#answerTextAreaId').val();
				quesAnsJson["arr"][i][quesId]["binary"] = binaryCode+' 1010';
			}
		}
		var quesAns = {}
		quesAns['question.questionId'] = quesId;
		quesAns['code'] = jQuery('#answerTextAreaId').val();
		quesAns['answerId'] = jQuery('#ansId').val();
		quesAns['assignment.assignmentId'] = $('#hiddenAssignId').val();
		quesAns['course.courseId'] = $('#hiddenCourseId').val();
		if (val == 1)
			quesAns['status'] = 1;
		else
			quesAns['status'] = 2;
		console.log(quesAns);

		//console.log("binaryCode-->" + binaryCode);
		quesAns['binary'] = binaryCode+' 1010';
		$(".modal").css("display", "block");
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '../assignment/saveAnswer',
			data : JSON.stringify(quesAns),
			datatype : 'json',
			success : function(responseText) {
				console.log(responseText);
				var result = JSON.parse(responseText);
				var outputStr ="";
				var outputArr = JSON.parse(result["output"]);
				for(i=0;i<outputArr.length;i++){
					outputStr = outputStr + outputArr[i];
					if(i!=outputArr.length-1)
						outputStr = outputStr + '\n';
				}
				jQuery('#outputTextAreaId').val(outputStr);
				jQuery('#ansId').val(result["id"]);
				$(".modal").css("display", "none");
			}
		});
		ansFlag[quesId] = true;
	}

	function saveComment() {
		$(".modal").css("display", "block");
		var ansComment = {};
		ansComment['answerId'] = jQuery('#ansId').val();
		ansComment['comment'] = jQuery('#commentTextAreaId').val();
		var arrLen = quesAnsJson["arr"].length;
		var quesId = jQuery('.active').attr("id");
		for (i = 0; i < arrLen; i++) {
			var qKeys = Object.keys(quesAnsJson["arr"][i]);
			if (qKeys[0] == quesId) {
				quesAnsJson["arr"][i][quesId]["comment"] = jQuery('#commentTextAreaId').val();
			}
		}
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '../assignment/saveComment',
			data : JSON.stringify(ansComment),
			datatype : 'json',
			success : function(responseText) {
				console.log("comment save->" + responseText);
				$(".modal").css("display", "none");
			}
		});
	}

	function text2Binary(str) {
		return str.split('').map(function(char) {
			var cc = char.charCodeAt(0);
			console.log("cc-->" + cc);
			return cc.toString(2);
		}).join(' ');
	}

	function executeCode() {
		$(".modal").css("display", "block");
		var answerId = jQuery('#ansId').val();
		if (answerId == "" || answerId == 0) {
			alert("Save your code first");
			$(".modal").css("display", "none");
		} else {
			var studentAssignAns = {};
			var quesId = jQuery('.active').attr("id");
			studentAssignAns['question.questionId'] = quesId;
			studentAssignAns['answerId'] = jQuery('#ansId').val();
			studentAssignAns['assignment.assignmentId'] = $('#hiddenAssignId').val();
			studentAssignAns['course.courseId'] = $('#hiddenCourseId').val();
			studentAssignAns['user.userId'] = $('#hiddenUserId').val();
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : '../assignment/executeforprof',
				data : JSON.stringify(studentAssignAns),
				datatype : 'json',
				success : function(responseText) {
					console.log(responseText);
					var result = JSON.parse(responseText);
					$(".modal").css("display", "none");
					jQuery('#outputTextAreaId').val(result);
				}
			});
		}
	}
	
	function saveQuery() {
		var askQuery = {};
		askQuery['query'] = jQuery('#queryTextAreaId').val();
		askQuery['courseId'] = quesAnsJson["courseId"];
		askQuery['quesId'] = jQuery('.active').attr("id");
		askQuery['assignId'] = quesAnsJson["assignId"]
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '../assignment/savequery',
			data : JSON.stringify(askQuery),
			datatype : 'json',
			success : function(responseText) {
				if(responseText=="success"){
					alert("Query saved successfully.")
					$("#myModal").css("display", "none");
					jQuery('#queryTextAreaId').val('');
				}else{
					alert("Save your query again");
				}
					
			}
		});
	}
</script>
</html>