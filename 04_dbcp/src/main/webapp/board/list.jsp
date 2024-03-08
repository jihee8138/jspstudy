<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<style>
  .row > span {
    display: inline-block;
  }
  .row > span:nth-of-type(2) {
    width: 150px;
  }
  
  .row > span:nth-of-type(3) {
    width: 100px;
  }
</style>
</head>
<body>

  <div>
    <a href="${contextPath}/board/write.brd">새 게시글 작성</a>
  </div>

  <hr>
  
  <%-- 리퀘스트에 저장된 모든 카운트와 리스트는 EL로 확인할 수 있다 --%>
  <div>
    <span>게시글 개수</span>
    <span>${boardCount}</span>
  </div>
  
  <div>
    <c:if test="${empty boardList}">
      <div>작성된 게시글이 없습니다. 첫 게시글의 주인공이 되어보세용</div>
    </c:if>
    <c:if test="${not empty boardList}">
    <%-- for 문 안에 id 주면 id 반복됨 --%>
      <c:forEach items="${boardList}" var = "board">
        <div class="row">
        <span><input type="checkbox" class="chk-each" value="${board.board_no}"></span>
          <span><a href="${contextPath}/board/detail.brd?board_no=${board.board_no}">${board.title}</a></span>
          <span>${board.created_at}</span>
        </div>      
      </c:forEach>
      <div>
        <button type="button" id="btn-remove">선택삭제</button>
      </div>
      <script>
      	const chkEach = $('.chk-each');
      	const btnRemove = $('#btn-remove');
      	btnRemove.on('click', (evt)=>{
      		if(!confirm('선택한 게시글을 삭제할까요?')) {
      			return;
      		}
      		let array = [];
      		$.each(chkEach, (i, elem)=>{
      			if(elem.checked) {  // $(elem).is(':checked')
      				array.push(elem.value);
      			}
      		})
      		// array     	    === [3, 2, 1]
      		// array.join(',')  === '3, 2, 1'
      		location.href = '${contextPath}/board/removes.brd?param=' + array.join(',');
      	})
      </script>
    </c:if>
  </div>  
  
</body>
</html>