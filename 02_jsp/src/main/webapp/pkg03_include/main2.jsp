<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
       <jsp:include page="header.jsp">
    <jsp:param value="메인2" name="title"/>
   </jsp:include>

    <h1>main2</h1>
    
    <%--
      <%@ include %> 지시어
      내용이 변하지 않는 정적 페이지를 포함할 때 사용한다.
     --%>
    <%@ include file="footer.jsp" %>
  


</body>
</html>