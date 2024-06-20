package reserve;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.MeetingRoom;


@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//URLから直接アクセスはログイン画面に遷移する
		response.sendRedirect("login.jsp");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String passPw = request.getParameter("passPw");
		String displayName = request.getParameter("displayName");
		String address = request.getParameter("address");

		MeetingRoom mr = new MeetingRoom();

		//画面遷移のURL
		String nextPage = "signUp.jsp";
		String message = "登録失敗";
		//ログイン成功の場合
		if(mr.signUp(userId,passPw,displayName,address )) {
			nextPage = "login.jsp";
			message = "登録成功";
		}
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}
