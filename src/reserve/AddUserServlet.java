package reserve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;
import bean.UserBean;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
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
		try {
			MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
			UserBean user = (UserBean) session.getAttribute("user");
			meetingRoom.newUserRegistration(user);
			if (meetingRoom.getUser() != null) {
				session.setAttribute("AdminFlg", meetingRoom.getUser().getAdminFlg());
			}
			request.getRequestDispatcher("registeredUser.jsp").forward(request, response);
		} catch (Exception e) {
			errorReason = e.getMessage();
			request.setAttribute("errorReason", errorReason);
			request.getRequestDispatcher("registeredUserError.jsp").forward(request, response);
		}
	}
}