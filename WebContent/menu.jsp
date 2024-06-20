<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session == null || session.getAttribute("meetingRoom") == null) {
		response.sendRedirect("login.jsp");
		return;
	}

	String adminFlg = (String) session.getAttribute("adminFlg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
<title>会議室予約メニュー</title>
</head>
<body>
	<h1 class="title">会議室予約</h1>

	<h2 class="subtitle">メニュー</h2>
	<%
		if (adminFlg.equals("1")) {
			out.print("<form action='adminMenu.jsp' method='post'>");
			out.print("<input type='submit' name='' class='button underButton fontButton' value='管理者メニュー'>");
			out.print("</form>");
		}
	%>
	<form action='reserveInput.jsp' method='post'>
		<input type='submit' name='' class='button underButton fontButton' value='会議室予約'>
	</form>

	<form action='cancelInput.jsp' method='post'>
		<input type='submit' name='' class='button cancel underButton fontButton' value='予約キャンセル'>
	</form>
	<form action="myPage.jsp" method="post">
		<input type="submit" class="button underButton fontButton"
			value="マイページ">
	</form>
	<form action='LogoutServlet' method='post'>
		<input type='submit' class='button logout' value='ログアウト'>
	</form>
</body>
</html>