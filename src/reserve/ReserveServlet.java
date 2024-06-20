package reserve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;
import bean.ReservationBean;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("user") == null) {
	            response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
	        } else {
	        	//もしsessionを持っていてもGetで来た場合はとりあえずlogin.jspに戻す。
	            response.sendRedirect("login.jsp");
	        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//セッションが切れたらログイン画面に遷移する
		if (session == null || session.getAttribute("meetingRoom") == null) {
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
			return;
		}
		request.setCharacterEncoding("UTF-8");
		String errorReason;
		try {
			MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
			ReservationBean reservation = (ReservationBean) session.getAttribute("reservation");
			meetingRoom.reserve(reservation);
			ReservationBean[][] reservations = meetingRoom.getReservations();

			searchReservation:
			for(ReservationBean[] rbList: reservations) {
				for(ReservationBean reservationBean : rbList) {
					if(reservationBean != null && reservationBean.getRoomId().equals(reservation.getRoomId()) && reservationBean.getStart().equals(reservation.getStart())) {
						session.setAttribute("reservation", reservationBean);
						break searchReservation;
					}
				}
			}
			request.getRequestDispatcher("reserved.jsp").forward(request, response);
		}catch(Exception e) {
			errorReason = e.getMessage();
			request.setAttribute("errorReason", errorReason);
			request.getRequestDispatcher("reserveError.jsp").forward(request, response);
		}
	}
}
