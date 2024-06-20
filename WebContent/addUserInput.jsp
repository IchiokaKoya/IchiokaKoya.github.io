<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF8");
	String errorReason = (String) request.getAttribute("errorReason");
	String adminFlg = (String) session.getAttribute("adminFlg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">
<title>利用者登録</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<h1 class="title">利用者登録</h1>

		<% if (errorReason != null) { %>
	<p class="letter errorword"><%=errorReason%></p>
	<%
		}
	%>
	<form class="form" action="UserCreateServlet" method="post">
		<div class="inner">
			<dl class="letter">
				<dt>
					<label for="userId">利用者ＩＤ:</label>
				</dt>
				<dd>
					<input type="text" id="userId" name="userId" class="text"
						maxlength="7" pattern=".{1,7}" required>
				</dd>
			</dl>
			<dl class="letter">
				<dt>
					<label for="userPw">パスワード:</label>
				</dt>
				<dd>
					<input type="password" id="userPw" name="userPw" class="text"
						maxlength="10" pattern=".{6,10}" required>
				</dd>
			</dl>
			<dl class="letter">
				<dt>
					<label for="name">表示名:</label>
				</dt>
				<dd>
					<input type="text" id="name" name="name" class="text"
						maxlength="10" pattern=".{1,10}" required>
				</dd>
			</dl>
			<dl class="letter">
				<dt>
					<label for="address">住所:</label>
				</dt>
				<dd>
					<input type="text" id="address" name="address" class="text"
						maxlength="30" pattern=".{1,30}" required>
				</dd>
			</dl>
		</div>
		<%
			if (adminFlg != null && adminFlg.equals("1")) {
				out.print("<p class='letter'>");
				out.print("管理者権限付与:<input type='checkbox' id='adminCheckbox' name='adminFlg' value='1'>");
				out.print("</p>");
			}
		%>
		<input type="submit" class='button underButton fontButton' value="登録">
	</form>
	<form action="addUserInput.jsp" method="post">
		<input type="submit" class='button underButton fontButton'
			value="リセット">
	</form>
	<div class="back-button">
		<%
			if (adminFlg != null && adminFlg.equals("1")) {
				out.print("<form action='adminMenu.jsp' method='post'>");
				out.print("<input type='submit' class='button underButton fontButton'value='戻る'>");
				out.print("</form>");
			} else {
				out.print("<form action='login.jsp' method='post'>");
				out.print("<input type='submit' class='button underButton fontButton' value='戻る'>");
				out.print("</form>");
			}
		%>
	</div>
</body>
</html>
