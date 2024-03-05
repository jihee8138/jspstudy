package pkg09_bind;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DataBind3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
	  /*
     * 데이터 저장 영역
     * 
     * 1. ServletContext     : 컨텍스트 종료(애플리케이션 실행 종료) 전까지 데이터를 유지한다.
     * 2. HttpSession        : 세션 종료(웹 브라우저 종료) 전까지 데이터를 유지한다.
     * 3. HttpServletRequest : 요청 종료(응답) 전까지 데이터를 유지한다.
     */
    
    /*
     * 데이터 처리 메소드
     * 1. setAttribut(속성, 값) : Object 타입의 값을 저장한다.
     * 2. getAttribute(속성)    : Object 타입의 값을 반환한다. (캐스팅이 필요할 수 있다.)
     * 3. removeAttribute(속성) : 제거한다.
     */
    
    // HttpServletRequest에 데이터 저장하기
    request.setAttribute("c", "일반데이터");
    
    // 데이터 확인 페이지로 이동하기
    // response.sendRedirect("/servlet/dataConfirm");
    // response를 이용하면 안된다 (전달 하나도 안 됨)
    // request 데이터를 활용해서 주로 전달할 게 저장장소는 리퀘스트 이동방법은 포워드 주로 셀렉트처리
    // 포워드는 쿼리 셀렉트할 때 쓴다 기본개념 : 데이터를 리퀘스트에 저장해서 
    // 저장할 게 대부분 디비에서 셀렉트(목록, 상세보기 등) 결과를 가지고 다른 페이지로 전달해줄 때 그 때 리퀘스트에 에트리부트 형태로 전달함
    // 포워드 데이터는 포워드할 때 셀렉트 결과를 리퀘스트에 담아서 셋 에트리부트 형태로 담아서 전달
    // 데이터 저장할 때 제일 많이 쓰는 방법 리퀘스트에 저장시키고 포워드
    // 리다이렉트로 전달하면 전달이 안 됨 페이지 이동은 되는데 전달이 안됨 
    // 리퀘스트에 데이터를 저장하고 포워드할 것 (리퀘스트에 들어간 애트리부트를 확인할 수 있음)
    // 데이터를 저장할 때도 리퀘스트를 쓴다는 것.. 웹에서 80프로 정도는 리퀘스트 이용 
    request.getRequestDispatcher("/dataConfirm").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
