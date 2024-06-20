package reserve;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;
import bean.ReservationBean;
import bean.RoomBean;

@WebServlet("/ReserveCreateServlet")
public class ReserveCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
		} else {
			//もしsessionを持っていてもGetで来た場合はとりあえずlogin.jspに戻す。
			response.sendRedirect("login.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String roomId = request.getParameter("roomId");
		String time = request.getParameter("time");

		//画面遷移のURL
		String nextPage = "reserveConfirm.jsp";

		//セッションの開始
		HttpSession session = request.getSession();
		//セッションが切れたらログイン画面に遷移する
		if (session == null || session.getAttribute("meetingRoom") == null) {
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
			return;
		}
		MeetingRoom meetingRoom;
		meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		ReservationBean reservation = meetingRoom.createReservation(roomId, time);
		RoomBean room = meetingRoom.getRoom​(roomId);

		//予約IDは未定で予約情報を初期化する。登録前の新規予約IDは、0になっている。
		session.setAttribute("reservation", reservation);
		session.setAttribute("room", room);
		//利用会議室の取得
		session.setAttribute("getRoom", meetingRoom.getRoom​(roomId));
		session.setAttribute("roomId", roomId);
		session.setAttribute("time", time);

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);

	}

}
