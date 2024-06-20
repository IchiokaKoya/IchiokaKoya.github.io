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


@WebServlet("/RoomDeletionCreateServlet")
public class RoomDeletionCreateServlet extends HttpServlet {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		//セッションが切れたらログイン画面に遷移する
		if (session == null || session.getAttribute("meetingRoom") == null) {
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
			return;
		}
		String id = request.getParameter("id");
		String errorReason;
		RoomBean room;
		if (id.length() > 4) {
			// エラーメッセージを設定して、再度登録ページへリダイレクト
			errorReason = "入力文字数がオーバーしています。";
			request.setAttribute("errorReason", errorReason);
			request.getRequestDispatcher("roomDeletionInput.jsp").forward(request, response);
			return;
		}
		MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		try {
			room = meetingRoom.existingRoom(id);
			session.setAttribute("room", room);
			RequestDispatcher rd = request.getRequestDispatcher("roomDeletionConfirm.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			errorReason = e.getMessage();
			request.setAttribute("errorReason", errorReason);
			RequestDispatcher rd = request.getRequestDispatcher("roomDeletionInput.jsp");
			rd.forward(request, response);
		}
	}

}