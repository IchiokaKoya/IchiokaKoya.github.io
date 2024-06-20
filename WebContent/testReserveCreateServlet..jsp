<%@ page language="java" contentType = "text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="ReserveCreateServlet" method="post">
	<p>会議室予約</p>
		ルームID:<input type="text" name="roomId" >
		時間:<input type="text" name="time" >
		<input type="submit" value="送信">
	</form>

</body>
</html>