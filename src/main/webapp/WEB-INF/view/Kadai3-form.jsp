<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		if(request.getParameter("error") != null){	
	%>
		<p style="color:red">削除失敗</p>
	<form action="LoginServlet" method="post">
		【メールアドレスを入力してください。】<br>
	 	メールアドレス：<input type="text" name="mail" value="<%=request.getParameter("mail") %>"><br>
		<input type="submit" value="ログイン">
	</form>
	<%
		} else {
	%>
	<p>削除するデータのメールアドレスを入力してください。</p>
	<form action = "Kadai3DeletedaoServlet" method="post">
	<input type="text" name="mail">
	<input type="submit" value="削除">
	</form>
	<%
		}
	%>
</body>
</html>