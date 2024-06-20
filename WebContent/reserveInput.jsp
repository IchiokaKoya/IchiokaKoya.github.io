<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="bean.*"%>
<%
    // セッションチェック
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
<title>会議室予約</title>
</head>
<body>

        <h1>会議室予約</h1>
        <div class="content">
        <div class="date-section">
            <p>利用日</p>
            <form action="ChangeDateServlet" method="post">
                <input type="date" name="date" class="date" value="<%= meetingRoom.getDate() %>">
                <input type="hidden" name="page" value="reserveInput.jsp">
                <input type="submit" class="button" value="日付変更">
            </form>
            <p>予約可能時間帯(<c:out value="${meetingRoom.user.name}"/>)</p>
        </div>
        <table>
            <thead>
                <tr>
                    <th>会議室名＼時間帯</th>
                    <% for (String period : periods) { %>
                        <th><%= period %></th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <% for (int i = 0; i < reservations.length; i++) { %>
                    <tr>
                        <td class="room-name"><%= MeetingRoom.shorteningAndEscape(rooms[i].getName(), 8) %></td>
                        <% for (int j = 0; j < periods.length; j++) { %>
                            <td>
                                <% if (reservations[i][j] == null
                                      && (meetingRoom.getDate() + "-" + periods[j]).compareTo(currentTime) > 0) { %>
                                    <div class="available">
                                        <form action="ReserveCreateServlet" method="post">
                                            <input type="hidden" name="roomId" value="<%= rooms[i].getId() %>">
                                            <input type="hidden" name="time" value="<%= periods[j] %>">
                                            <input type="submit" value="予約可">
                                        </form>
                                    </div>
                                <% } else if (reservations[i][j] != null
                                            && reservations[i][j].getUserId().equals(userId)) { %>
                                    <div class="self-reserved">自予約</div>
                                <% } else { %>
                                    <div class="reserved">不可</div>
                                <% } %>
                            </td>
                        <% } %>
                    </tr>
                <% } %>
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

