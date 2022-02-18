<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table width="500" cellpacing="0" border="1">
		<form action="modify.do" method="post">
			<tr>
				<td>번호</td>
				<td>${contentView.b_id}</td>
			</tr>
			<tr>
				<td>조회수</td>
				<td>${contentView.b_hit}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="bName" value="${contentView.bName}"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="bTitle" value="${contentView.bTitle}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea ros="10" name="bContent" value="${contentView.bContent}"> </textarea> </td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="수정">&nbsp;&nbsp;
					<a href="list.do">목록보기</a>&nbsp;&nbsp;
					<a href=" deleto.do?bId=${contentView.bId}">삭제</a>&nbsp;&nbsp;
					<a href=" replyView.do?bId=${contentView.bId}">답변</a>
				</td>		
			</tr>
		</form>
	</table>
</body>
</html>