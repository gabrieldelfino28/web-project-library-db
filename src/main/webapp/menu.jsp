<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/styles.css">
<title>Menu</title>
</head>
<body>
	<nav id = "menu">
		<ul>
			<li><a href= "${pageContext.request.contextPath }/">Home</a>
			<li><a href= "${pageContext.request.contextPath }/livro">Livro</a>
			<li><a href= "${pageContext.request.contextPath }/revista">Revista</a>
			<li><a href= "${pageContext.request.contextPath }/aluguel">Aluguel</a>
			<li><a href= "${pageContext.request.contextPath }/aluno">Aluno</a>
		</ul>
	</nav>

</body>
</html>