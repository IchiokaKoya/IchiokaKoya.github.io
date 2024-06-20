<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// Servletのデータ受け取り
	request.setCharacterEncoding("UTF8");
	String errorReason = (String) request.getAttribute("errorReason");
	if (session == null || session.getAttribute("meetingRoom") == null) {
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
<title>会議室予約</title>
</head>
<body>
	<h1 class="title">会議室予約</h1>
	<h2 class="subtitle">予約エラー</h2>
	<p class="letter errorword"><%=errorReason%></p>
	<div class="form">
		<div class="inner">
			<dl class="letter">
				<dt>予約日</dt>
				<dd>${reservation.date}</dd>
			</dl>
			<dl class="letter">
				<dt>会議室</dt>
				<dd>
					<c:out value="${room.name}" />
				</dd>
			</dl>
			<dl class="letter">
				<dt>予約時刻</dt>
				<dd>${reservation.start}～${reservation.end}</dd>
			</dl>
			<dl class="letter">
				<dt>予約者</dt>
				<dd>
					<c:out value="${meetingRoom.user.name}" />
				</dd>
			</dl>
		</div>
	</div>
	<!-- 線を出すdiv -->
	<div class="title under"></div>
	<form action="menu.jsp" method="Post">
		<input type="submit" class="button" value="戻る">
	</form>

</body>
</html>