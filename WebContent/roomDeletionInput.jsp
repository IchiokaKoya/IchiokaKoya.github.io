<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF8");
	String errorReason = (String) request.getAttribute("errorReason");
	String adminFlg = (String) session.getAttribute("adminFlg");
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
<title>会議室削除</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<h1 class="title">会議室削除</h1>
	<%
		if (errorReason != null) {
	%>
	<p class="letter errorword"><%=errorReason%></p>
	<%
		}
	%>
	<form action="RoomDeletionCreateServlet" method="post">
		<p class="letter">
			会議室ＩＤ:<input type="text" name="id" class="text" value=""
				maxlength="4" required>
		</p>
		<input type="submit" class='button underButton fontButton' value="決定">
	</form>
	<form action="roomDeletionInput.jsp" method="post">
		<input type="submit" class='button underButton fontButton' value="リセット">
	</form>
	<div class="back-button">
		<form action='adminMenu.jsp' method='post'>
			<input type='submit' class='button underButton fontButton' value='戻る'>
		</form>
	</div>
</body>
</html>