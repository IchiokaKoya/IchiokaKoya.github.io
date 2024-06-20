<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>利用者解除入力</title>
</head>
<body>
	<h2 class="subtitle">利用者解除</h2>

	<%
		if (errorReason != null) {
	%>
	<p class="letter errorword"><%=errorReason%></p>
	<%
		}
	%>

	<form action="UserReleaseCreateServlet" method="post">
		<p class="letter">
			利用者ＩＤ:<input type="text" name="userId" class="text" value=""
				maxlength="7" pattern=".{1,7}" required>
		</p>
		<p class="letter">
			パスワード:<input type="password" name="userPw" class="text" value=""
				maxlength="10" pattern=".{6,10}" required>
		</p>
		<input type="submit" class='button underButton fontButton' value="決定">
	</form>
	<div class="back-button">
		<%
			if (adminFlg != null && adminFlg.equals("1")) {
				out.print("<form action='adminMenu.jsp' method='post'>");
				out.print("<input type='submit' class='button underButton fontButton' value='戻る'>");
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