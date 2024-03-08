<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>편집화면</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

 <%-- 입력화면과 비슷하지만 원래 게시글의 제목과 내용이 화면에 나와있어야한다. --%> 

<div>
  <form id="frm-edit"
        method="POST"
        action="${contextPath}/board/modify.brd">
    <div>
      <%-- 게시글 번호는 사용자가 편집하지 못 하게 hidden 혹은 readonly 처리 --%>
      <label for="board_no">게시글번호</label>
      <input type="text" id="board_no" name="board_no" value="${board.board_no}" readonly>
    </div>
    <div>
      <label for="title">제목</label>
      <input type="text" id="title" name="title" value="${board.title}">
    </div>
    
    <div>
      <textarea rows="5" cols="50" name="contents">${board.contents}</textarea>
    </div>
    <div>
      <button type="submit">수정완료</button>
      <button type="reset">초기화</button>
      <button type="button" id="btn-list">목록보기</button>
    </div>
  </form>
</div>

<script>

	// 제목을 공백(아무것도 적지 않았을 때, 띄어쓰기만 했을 때)으로 뒀을 때 경고창 띄우기
	// 제목이 비워있을 때 submit 막아두지 않으면 예외 발생
	document.getElementById('frm-edit').addEventListener('submit', (evt)=>{
		const title = document.getElementById('title');
		if(title.value.trim === '') {
			alert('제목은 필수입니다.');
			title.focus();
			evt.preventDefault();
			return;
		}
	})
	
</script>

</body>
</html>