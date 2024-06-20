<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="bean.*"%>
<%
	request.setCharacterEncoding("UTF8");
	String errorReason = (String) request.getAttribute("errorReason");
	String adminFlg = (String) session.getAttribute("adminFlg");
	RoomBean room = (RoomBean) session.getAttribute("room");
	if (session == null || session.getAttribute("meetingRoom") == null || adminFlg.equals("0")) {
		response.sendRedirect("login.jsp");
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">
<title>会議室登録確認</title>
</head>
<body>
	<h1 class="title">会議室登録</h1>
	<h2 class="subtitle">以下の内容で会議室を登録します</h2>
	<div class="form">
		<div class="inner">
			<dl class="letter">
				<dt>会議室ＩＤ:</dt>
				<dd>
					<c:out value="${room.id}" />
				</dd>
			</dl>
			<dl class="letter">
				<dt>会議室名:</dt>
				<dd>
					<c:out value="${room.name}" />
				</dd>
			</dl>
		</div>
	</div>
	<div class="title under"></div>
	<div class="reschedule">
		<form action="newRoomInput.jsp" method="post">
			<input type="submit" class="button return" value="戻る">
		</form>

		<form action="AddNewRoomServlet" method="post">
			<input type="submit" class="button" value="決定">
		</form>

	</div>
</body>
</html>