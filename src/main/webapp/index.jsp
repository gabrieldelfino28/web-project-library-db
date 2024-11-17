<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/styles.css">
<title>Home</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"></jsp:include>
	</div>
	<div align="left">
		<h1></h1>
	</div><b>Bem vindo ao sistema</b>
 
	<div align="center" class="container">
		<form action="index" method="post">
			<table>
				<tr>
					<td colspan="1">
						<input class="input_data" type="number" min="0" step="1" id="codigo"
						name="codigo" placeholder="Livro ou revista">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br>
	<div align="center">
		<c:if test="${not empty erro}">
			<H2><b><c:out value="${erro}"/></b></H2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida}">
			 <H2><b><c:out value="${saida}"/></b></H2>
		</c:if>
	</div>
</body>
</html>