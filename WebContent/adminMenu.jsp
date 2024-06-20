<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
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
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/menu.css">
<title>管理者メニュー</title>
</head>
<body>
	<h1 class="title">管理者メニュー</h1>
			<p class="letter"><c:out value="${meetingRoom.user.name}"/></p>
	<form action="addUserInput.jsp" method="post">
		<input type="submit" class='button underButton fontButton' value="利用者登録">
	</form>
	<form action="userReleaseInput.jsp" method="post">
		<input type="submit" class='button underButton fontButton' value="利用者解除">
	</form>
	<form action="newRoomInput.jsp" method="post">
		<input type="submit" class='button underButton fontButton' value="会議室登録">
	</form>
	<form action="roomDeletionInput.jsp" method="post">
		<input type="submit" class='button underButton fontButton' value="会議室削除">
	</form>
	<div class="back-button">
		<form action="menu.jsp" method="post">
			<input type="submit" class='button underButton fontButton' value="戻る">
		</form>
	</div>
	<form action='LogoutServlet' method='post'>
		<input type='submit' class='button logout' value='ログアウト'>
	</form>

</body>
</html>