<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF8");
	String errorReason = (String) request.getAttribute("errorReason");
	String adminFlg = (String) session.getAttribute("adminFlg");
	UserBean user = (UserBean) session.getAttribute("user");
	if (session == null || session.getAttribute("user") == null) {
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
<title>利用者登録確認</title>
</head>
<body>
	<h1 class="title">利用者登録確認</h1>
	<h2 class="subtitle">以下の内容で登録します</h2>
	<div class="form">
		<div class="inner">
			<dl class="letter">
				<dt>利用者ＩＤ:</dt>
				<dd>
					<c:out value="${user.id}" />
				</dd>
			</dl>
			<dl class="letter">
				<dt>パスワード:</dt>
				<dd>
					<c:out value="${user.password}" />
				</dd>
			</dl>
			<dl class="letter">
				<dt>表示名:</dt>
				<dd>
					<c:out value="${user.name}" />
				</dd>
			</dl>
			<dl class="letter">
				<dt>住所:</dt>
				<dd>
					<c:out value="${user.address}" />
				</dd>
			</dl>
		</div>
	</div>
	<%
		if (adminFlg != null && user.getAdminFlg().equals("1")) {
			out.print("<p class='letter'>");
			out.print("管理者権限付与");
			out.print("</p>");
		}
	%>

	<!-- 線を出すdiv -->
	<div class="title under"></div>
	<div class="reschedule">
		<form action="addUserInput.jsp" method="post">
			<input type="submit" class="button return" value="戻る">
		</form>
		<form action="AddUserServlet" method="post">
			<input type="submit" class="button return" value="決定">
		</form>

	</div>
</body>
</html>
