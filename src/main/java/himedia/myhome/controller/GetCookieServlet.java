package himedia.myhome.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cookie/get")
public class GetCookieServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; chsrset=UTF-8");
		
		// 그냥 호출 -> 쿠키 확인
		// ?a=delete -> 쿠키 지우기
		PrintWriter out = resp.getWriter();
		
		String actionName = req.getParameter("a");
		
		if("delete".equals(actionName)) {
			// 쿠키 지우기
			// 			MaxAge를 0 또는 음수 값으로 변경해야 쿠키값이 무효화된다
			Cookie[] cookies = req.getCookies();
			for (Cookie cookie: cookies) {
				if(cookie.getName().equals("testCookie")) {
					// 무효화
					cookie.setMaxAge(0);		// 지속시간 = 0으로 설정
					resp.addCookie(cookie);
				}
			}
			out.println("<p>testCookie를 삭제했습니다.</p>");
		} else {
			// 요청 정보에서 쿠키 목록 확인하여 출력하기
			out.println("<h1>쿠키 목록</h1>");
			out.println("<ul>");
			Cookie[] cookies = req.getCookies();
			for (Cookie cookie: cookies) {
				out.printf("<li>%s: %s - %d</li>",
						cookie.getName(),
						URLDecoder.decode(cookie.getValue(),"UTF-8"),
						cookie.getMaxAge());
			}
			out.println("</ul>");
		}
	}

}
