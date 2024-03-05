<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <% String contextPath = request.getContextPath(); %>

  <form id="frm" method="POST"
        action="<%=contextPath%>/test4/save.jsp">
    <div>
      <label for="author">작성자</label>
      <input type="text" id="author" name="author">  
    </div>
    <div>
      <label for="title">제목</label>
      <input type="text" id="title" name="title">
    </div>
    <div>
      <textarea rows="5" cols="50" name="contents" placeholder="내용"></textarea>
    </div>
    <div>
      <button type="submit">작성완료</button>
      <button type="reset">작성초기화</button>
    </div>
  </form>
  
 
  
  <script>
  	function getNow() {
      var now = new Date();
      var year = now.getFullYear();
      var month = now.getMonth();
      var day = now.getDate();
      return year + '-' + month + '-' + day;
    }
  	document.getElementById("frm").onsubmit = function() {
      var author = document.getElementById("author").value;
      var title = document.getElementById("title").value;
      alert(getNow() + author + '-' + title + '.txt' + ' 파일이 생성되었습니다');
  };
  </script>


</body>
</html>