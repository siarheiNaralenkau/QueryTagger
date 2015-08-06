<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>QueryTagger login</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/login.css" rel="stylesheet">
	</head>
<body>
	<p class="page-header">Log in with your WoltersKluwer credentials</p>
	<div class="credentials-dialog">
		<form name="loginForm" action="./login_servlet" method="post">
			<div class="input-div">
				<label for="uLogin" class="input-label">Login: </label>
				<input type="text" class="text-input" name="login" id="uLogin"/>
			</div>
			<div class="input-div">
				<label for="uPassword" class="input-label">Password: </label>
				<input type="password" name="password" id="uPassword"/>
			</div>
			<div class="submit-div">
				<input type="submit" value="Enter" class="btn btn-default"/>
			</div>
		</form>
	</div>
	
	<script src="js/jquery-2.1.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>