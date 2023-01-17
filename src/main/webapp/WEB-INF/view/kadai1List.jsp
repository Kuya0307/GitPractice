<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Account2" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border = "1">
	<tr>
		<th>氏名</th>
		<th>年齢</th>
		<th>性別</th>
		<th>電話番号</th>
		<th>メールアドレス</th>
	</tr>

	<%
	List<Account2> list = (ArrayList<Account2>)request.getAttribute("list");
	for(Account2 a : list){
	%>
	<tr>
		<td><%=a.getName() %></td>
		<td><%=a.getAge() %></td>
		<td><%=a.getGender() %></td>
		<td><%=a.getTel() %></td>
		<td><%=a.getMail() %></td>
	</tr>
	
	<%} %>
	</table>
	<a href = "./" >戻る</a>
</body>
</html>