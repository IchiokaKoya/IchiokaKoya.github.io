<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF8");
	String errorReason = (String) session.getAttribute("errorReason");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">
<title>ログイン</title>
</head>
<body>
	<h1 class="title">会議室予約</h1>

	<h2 class="subtitle">ログイン</h2>
	<%
		if (errorReason != null) {
	%>
	<p class="letter errorword"><%=errorReason%></p>
	<%
		}
	%>
	<form action="LoginServlet" method="post">
		<p class="letter">
			利用者ＩＤ:<input type="text" name="userId" class="text" value=""
				maxlength="7" pattern=".{1,7}" required>
		</p>
		<p class="letter">
			パスワード:<input type="password" name="userPw" class="text" value=""
				maxlength="10" pattern=".{6,10}" required>
		</p>
		<input type="submit" class='button underButton fontButton' value="ログイン">
	</form>
	<form action="addUserInput.jsp" method="get">
		<input type="submit" class='button underButton fontButton' value="新規利用者登録">
	</form>
</body>
</html>