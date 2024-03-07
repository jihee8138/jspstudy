<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--welcome 페이지 --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>

  <%-- form 태그 없으니 submit 안됨. a 태그 없으니 링크도 없음 --%>
  <div class="wrap">
    <select id="type">
      <option value="date">현재날짜</option>
      <option value="time">현재시간</option>
      <option value="datetime">현재날짜와시간</option>
    </select>
    <button id="btn" type="button">요청</button>
  </div>
  
  <%-- script 포함 시킬 땐 바디 태그 끝날 때 해주면 좋음 --%>
  <script src="${contextPath}/assets/js/index.js"></script>
  
  <%-- 실행할 땐 jsp 파일을 열고 실행하는 게 아닌 jsp 파일 닫고 프로젝트를 실행 --%>

</body>
</html>