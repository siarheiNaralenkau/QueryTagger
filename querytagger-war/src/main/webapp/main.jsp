<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>QueryTagger Main</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/main.css" rel="stylesheet">
	</head>
<body>
	<div class="main-container">
		<ul class="nav nav-tabs">
			<li role="presentation">
				<a href="./query_phrases">Query Phrases</a>
			</li>
			<li role="presentation">
				<a href="./golden_answer_sets">Golden Answer Sets</a>
			</li>
		</ul>
		<div class="import-container">
			<form method="post" action="./import_query_phrases" enctype="multipart/form-data">
				<label for="importFile">Select *.xls file to import: </label>
				<input type="file" name="importFile" id="importFile" accept="application/vnd.ms-excel"/>
				<input type="submit" value="Upload"/>
			</form>	
		</div>
	</div>
	
	<script src="js/jquery-2.1.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>