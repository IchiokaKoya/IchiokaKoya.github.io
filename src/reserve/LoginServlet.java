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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String errorReason;
		String adminFlg;
		//セッションの開始
		HttpSession session = request.getSession();

		//画面遷移のURL
		String nextPage = "login.jsp";
		//コンストラクタの生成
		MeetingRoom meetingRoom = new MeetingRoom();
		//ログインの判定を行う
		boolean flag = meetingRoom.login​(userId, userPw);
		//ログイン成功の場合
		if (flag == true) {
			nextPage = "menu.jsp";
			adminFlg = meetingRoom.getUser().getAdminFlg();
			//セッションの登録
			session.setAttribute("meetingRoom", meetingRoom);
			session.setAttribute("adminFlg", adminFlg);
		}
		errorReason = "入力に誤りがあります。";
		session.setAttribute("errorReason", errorReason);
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

}
