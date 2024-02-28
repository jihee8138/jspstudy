package pkg05_redirect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Destination2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	  // 요청
    request.setCharacterEncoding("UTF-8");
    
    String luggage = request.getParameter("luggage");
    
    // 응답
    response.setContentType("text/html; charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    
    out.println("<script>");
    out.println("alert('목적지에 도착했습니다. 수화물 "+ luggage + " 도착했습니다.');");
    out.println("</script>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
