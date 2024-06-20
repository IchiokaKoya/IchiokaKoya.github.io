<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<title>Insert title here</title>
</head>
<body>
	<h1 class="title">新規登録</h1>
	<h2 class="subtitle">ユーザの登録</h2>
	<%	String message =(String)request.getAttribute("message");
		if( message  != null){%>
			<p class="letter"><%=( message)%></p>
	<% }%>
	<form action="SignUpServlet" method="post">
		<p class="letter">利用者ＩＤ:<input type="text" name="userId" class="text"></p>
		<p class="letter">パスワード:<input type="password" name="passPw" class="text"></p>
		<p class="letter">表示名:<input type="text" name="displayName" class="text"></p>
		<p class="letter">住所:<input type="text" name="address" class="text"></p>
		<input type="submit" class="button" value="登録">
	</form>

	<form action="login.jsp" method="post">
		<input type="submit" class="button" value="戻る">
	</form>

</body>
</html>