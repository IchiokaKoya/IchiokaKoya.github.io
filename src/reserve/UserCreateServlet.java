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
import bean.UserBean;

@WebServlet("/UserCreateServlet")
public class UserCreateServlet extends HttpServlet {
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
		MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		if(meetingRoom == null) {
			meetingRoom =new MeetingRoom();
		}
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String adminFlg = request.getParameter("adminFlg");
		String errorReason;
		if (userId.length() > 7 || userPw.length() > 10 || userPw.length() < 6 || name.length() > 10
				|| address.length() > 30) {
			// エラーメッセージを設定して、再度登録ページへリダイレクト
			errorReason = "入力文字数がオーバーしています。";
			request.setAttribute("errorReason", errorReason);
			request.getRequestDispatcher("addUserInput.jsp").forward(request, response);
			return;
		}
		UserBean user;
		try {
			if (adminFlg != null && adminFlg.equals("1")) {
				user = meetingRoom.newUser(userId, userPw, name, address, adminFlg);
			} else {
				user = meetingRoom.newUser(userId, userPw, name, address);
			}
			session.setAttribute("user", user);
			session.setAttribute("meetingRoom", meetingRoom);
			RequestDispatcher rd = request.getRequestDispatcher("addUserConfirm.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			errorReason = e.getMessage();
			request.setAttribute("errorReason", errorReason);
			RequestDispatcher rd = request.getRequestDispatcher("addUserInput.jsp");
			rd.forward(request, response);
		}
	}

}
