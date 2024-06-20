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
import bean.RoomBean;

@WebServlet("/RoomCreateServlet")
public class RoomCreateServlet extends HttpServlet {
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
		HttpSession session = request.getSession();
		//セッションが切れたらログイン画面に遷移する
		if (session == null || session.getAttribute("meetingRoom") == null) {
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
			return;
		}
		String errorReason;
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		if (id.length() > 4 || name.length() > 20) {
			request.setAttribute("errorReason", "入力された文字数がオーバーしています");
			request.getRequestDispatcher("newRoomInput.jsp").forward(request, response);
			return;
		}
		RoomBean room;
		try {
			MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
			room = meetingRoom.newRoom(id, name);
			session.setAttribute("room", room);
			RequestDispatcher rd = request.getRequestDispatcher("newRoomConfirm.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			errorReason = e.getMessage();
			request.setAttribute("errorReason", errorReason);
			RequestDispatcher rd = request.getRequestDispatcher("newRoomInput.jsp");
			rd.forward(request, response);
		}
	}
}