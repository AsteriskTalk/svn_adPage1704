<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title> point </title>
</head>
<body>
<form action="receive.ad" method="post" >
	<input type="text" name="userId" />
	<input type="submit" value="파싱" />
</form>

<form action="http://117.52.31.199:3030/addPoint" method="post">
	<input type="text" name="userId" placeholder="userId">
	<input type="text" name="chatRoomId" placeholder="chatRoomId">
	<input type="text" name="ADCode" placeholder="ADCode">
	<input type="text" name="clientCode" placeholder="clientCode">
	<input type="text" name="point" placeholder="point">
	<input type="submit" value="전송">
</form>
</body>
</html>