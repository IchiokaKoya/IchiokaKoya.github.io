<%@page import="bean.MeetingRoom"%>
<%@page import="bean.ReservationBean"%>
<%@page import="bean.RoomBean"%>
<%@ page language="java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	if (session == null || session.getAttribute("meetingRoom") == null) {
		response.sendRedirect("login.jsp");
		return;
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<title>マイページ</title>
</head>
<body>
	<h1 class="title fontTitle">マイページ</h1>

	<h2 class="subtitle fontSubtitle">表示名の変更</h2>
	<!-- パスワードボタンを押したとき -->
	<p class="letter">${message }</p>

	<!-- 今の名前を表示 -->
		<p class="letter"><c:out value="${meetingRoom.user.name}"/></p>

	<!-- 表示名を変更する入力欄 -->
	<form action="myPageServlet" method="post">
		<input type="text" name="displayname" class="text" value=""
				maxlength="10" pattern=".{1,10}" required>
		<input type="submit" class="button underButton paddingTop" value="名前変更">
	</form>

	<!-- パスワード変更 -->
	<h2 class="subtitle fontSubtitle">パスワードの変更</h2>
	<!-- パスワードボタンを押したとき -->
	<p class="letter">${PasswordMessage }</p>
	<%-- <p class="letter"><c:out value="${meetingRoom.user.password}"/></p> --%>
	<form action="ChangePasswordServlet" method="post">
		<input type="text" name="changePassword" class="text" maxlength="10" pattern=".{6,10}" required>
		<input type="submit" class="button underButton paddingTop" value="パスワード変更">
	</form>

	<!-- 戻るボタン -->
	<form action="menu.jsp" method="post">
		<input type="submit" class="button" value="戻る">
	</form>

</body>
</html>