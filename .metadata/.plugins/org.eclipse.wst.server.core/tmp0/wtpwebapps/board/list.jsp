<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록 보기</title>
</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>번호</td>
			<td>이름</td>
			<td>제목</td>
			<td>날짜</td>
			<td>조회수</td>
		</tr>
		<c:forEach items="${list}" var="dto">
			<tr>
				<td>${dto.b_id}</td>
				<td>${dto.b_b_name}</td>
				<td>
					<c:forEach begin="1" end="${dto.b_indent}">-</c:forEach>
					<a href="contentView.do?b_id="${bto.b_id}> ${dto.b_title}</a>
				</td>
				<td>${dto.b_date}</td>
				<td>${dto.b_hit}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5"> <a href="writeView.do">글작성</a></td>
		</tr>
	</table>
</body>
</html>