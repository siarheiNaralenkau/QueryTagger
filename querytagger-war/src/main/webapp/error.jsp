<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>QueryTagger Errors Page</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/error.css" rel="stylesheet">
	</head>
<body>
	<div class="container">
		<h4>Error: </h4>
		<p class="error-message"><c:out value="${errorMsg}"/></p>
		<a href="./index.jsp" btn btn-default>To the main page</a>
	</div>
	
	<script src="js/jquery-2.1.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>