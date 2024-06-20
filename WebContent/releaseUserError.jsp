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
<title>利用者解除エラー</title>
</head>
<body>
	<h1 class="title">利用者解除エラー</h1>
	<h2 class="subtitle">以下の利用者の解除に失敗しました</h2>
	<p class="letter errorword"><%=errorReason%></p>
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
	<!-- 線を出すdiv -->
	<div class="title under"></div>
	<form action="userReleaseInput.jsp" method="post">
		<input type="submit" class="button" value="戻る">
	</form>
</body>
</html>