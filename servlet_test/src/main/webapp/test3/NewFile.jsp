<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <select id="select">
        <option value="time">현재 시간</option>
        <option value="date">현재 날짜</option>
    </select>
    <button onclick="show()">요청하기</button>

  <script>
  	function show() {
      var selectedOption = document.getElementById("select").value;
      
      if (selectedOption === "time") {
          var currentTime = new Date();
          var hours = currentTime.getHours();
          var minutes = currentTime.getMinutes();
          var seconds = currentTime.getSeconds();
          alert("요청 결과는 " + hours + ":" + minutes + ":" + seconds + " 입니다.");
      } else if (selectedOption === "date") {
          var currentDate = new Date();
          var year = currentDate.getFullYear();
          var month = currentDate.getMonth() + 1;
          var day = currentDate.getDate();
          alert("요청 결과는 " + year + "-" + month + "-" + day + " 입니다.");
      }
 	 }
	</script>

</body>
</html>