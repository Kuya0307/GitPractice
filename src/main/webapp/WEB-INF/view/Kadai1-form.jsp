<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Account2" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		String errorCode = request.getParameter("error");
		if(errorCode != null && errorCode.equals("1")){
			Account2 account2 = (Account2)session.getAttribute("input_data");
	%>
		<p style="color:red">登録に失敗しました。</p>
		<h3>新規会員登録</h3>
		<form action="Kadai1ConfirmServlet" method="post">
			名前：<input type="text" name="name" value="<%=account2.getName()%>"><br>
			年齢：<input type="number" name="age" value="<%=account2.getAge()%>"><br>
			性別：<input type="radio" name="gender" value="男">男
		      	<input type="radio" name="gender" value="女">	女<br>
				電話番号：<input type="text" name="tel" value="<%=account2.getTel()%>" ><br>
			メール：<input type="email" name="email" value="<%=account2.getMail()%>"><br>
			パスワード：<input type="password" name="pw"><br>
			<input type="submit" value="登録">
		</form>
	<%
		} else {
	%>
	<h3>新規会員登録</h3>
	<form action="Kadai1ConfirmServlet" method="post">
		名前：<input type="text" name="name"><br>
		年齢：<input type="number" name="age"><br>
		性別：<input type="radio" name="gender" value="男">男
		      <input type="radio" name="gender" value="女">	女<br>
		電話番号：<input type="text" name="tel"><br>
		メールアドレス：<input type="email" name="mail"><br>
		パスワード：<input type="password" name="pw"><br>
		<input type="submit" value="登録">
	</form>
	<%
		}
	%>
</body>
</html>