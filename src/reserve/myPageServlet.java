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

@WebServlet("/myPageServlet")
public class myPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public myPageServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			session.invalidate();
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
		} else {
			//もしsessionを持っていてもGetで来た場合はとりあえずlogin.jspに戻す。
			session.invalidate();
			response.sendRedirect("login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//reserveInput.jspから値を受け取る
		String displayname = request.getParameter("displayname");

		//セッションの開始
		HttpSession session = request.getSession();
		//セッションが切れたらログイン画面に遷移する
		if (session == null || session.getAttribute("meetingRoom") == null) {
			response.sendRedirect("login.jsp"); // ユーザーがログインしていない場合、login.jspにリダイレクト
			return;
		}
		MeetingRoom meetingRoom;
		meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		boolean ssuccess = meetingRoom.RegistrationDisplayName(displayname, meetingRoom.getUser().getId());
		String message = "登録失敗";
		if (ssuccess) {
			meetingRoom.login​(meetingRoom.getUser().getId(), meetingRoom.getUser().getPassword());
			message = "登録成功";
		}
		request.setAttribute("meetingRoom", meetingRoom);
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("myPage.jsp");
		rd.forward(request, response);
	}
}
