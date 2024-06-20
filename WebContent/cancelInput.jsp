<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="bean.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	if (session == null || session.getAttribute("meetingRoom") == null) {
		response.sendRedirect("login.jsp");
		return;
	}
	// セッションから予約情報と会議室情報を取得
	MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
	ReservationBean[][] reservations = meetingRoom.getReservations();
	String userId = meetingRoom.getUser().getId();
	RoomBean[] rooms = meetingRoom.getRooms();

	// 予約可能な時間帯を取得
	String[] periods = MeetingRoom.getPeriod();
	int roomCount = reservations.length;

	// 現在時刻を取得
	LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));

	// フォーマッタを作成
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

	// フォーマットした日時を取得
	String currentTime = now.format(formatter);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Input.css">
<title>会議室予約キャンセル</title>
</head>
<body>

		<h1>会議室予約キャンセル</h1>
		<div class="content">
		<div class="date-section">
			<p>利用日</p>
			<form action="ChangeDateServlet" method="post">
				<input type="date" name="date" class="date"
					value="${meetingRoom.date}"> <input type="hidden"
					name="page" value="cancelInput.jsp"> <input type="submit"
					class="button" value="日付変更">
			</form>
			<p>
				キャンセル可能時間帯(
				<c:out value="${meetingRoom.user.name}" />
				)
			</p>
		</div>
		<table>
			<thead>
				<tr>
					<th>会議室名＼時間帯</th>
					<%
						// 時間帯のヘッダーを表示
						for (String period : periods) {
							out.print("<th>" + period + "</th>");
						}
					%>
				</tr>
			</thead>
			<tbody>
				<%
					// 各会議室ごとに行を作成
					for (int i = 0; i < reservations.length; i++) {
						out.print("<tr>");
						out.print("<td class='room-name'>" +  MeetingRoom.shorteningAndEscape(rooms[i].getName(), 8)  + "</td>");

						// 各時間帯ごとに列を作成
						for (int j = 0; j < periods.length; j++) {
							out.print("<td class='cancel'>");
							// ログインしているユーザーの予約が入っている場合かつ、現在時刻より後の時刻だけformで送信できるように表示を許可
							if (reservations[i][j] != null
									&& (meetingRoom.getDate() + "-" + periods[j]).compareTo(currentTime) > 0
									&& userId.equals(reservations[i][j].getUserId())) {
								out.print("<div class='cancel-available'>");
								out.print("<form action='CancelCreateServlet' method='post'>");
								out.print("<input type='hidden' name='roomId' value='" + reservations[i][j].getRoomId() + "'>");
								out.print("<input type='hidden' name='time' value='" + periods[j] + "'>");
								out.print("<input type='submit' value='自予約'>");
								out.print("</form>");
								out.print("</div>");
							} else {
								// 予約が入っていない場合や現在時刻より前の時刻の場合は予約済みスタイルを適用
								out.print("<div class='reserved'>不可</div>");
							}
							out.print("</td>");
						}
						out.print("</tr>");
					}
				%>
			</tbody>
		</table>
		<div class="back-button">
			<form action="menu.jsp" method="post">
				<input type="submit" value="戻る">
			</form>
		</div>
	</div>
</body>
</html>
