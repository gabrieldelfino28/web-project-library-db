<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/styles.css">
<title>Revista</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<br>
	<div align="center" class="container">
		<form action="revista" method="post">
			<p class="title">
				<b>Revista</b>
			</p>
			<table>
				<tr>
					<td colspan="3">
						<input class="input_data" type="number" min="0" step="1" id="codigo" 
						name="codigo" placeholder="Código"
						value='<c:out value="${revista.codigo}"></c:out>'>
					</td>
					<td> 
						<input type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text" id="nome" name="nome" placeholder="Nome"
						value='<c:out value="${revista.nome}"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="number" min="0" step="1" id="qtd" name="qtd" placeholder="Qtd. de páginas"
						value='<c:out value="${revista.qtdPaginas}"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text" id="issn" name="issn" placeholder="ISSN"
						value='<c:out value="${revista.ISSN}"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="submit" id="botao" name="botao" value="Cadastrar"> 
						<input type="submit" id="botao" name="botao" value="Alterar"> 
						<input type="submit" id="botao" name="botao" value="Excluir"> 
						<input type="submit" id="botao" name="botao" value="Listar">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br>
	<div align="center">
		<c:if test="${not empty erro}">
			<H2><b><c:out value="${erro }"/></b></H2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida}">
			<H3><b><c:out value="${saida }"/></b></H3>
		</c:if>
	</div>
	<br>
	<div align="center">
		<c:if test="${not empty revistas }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Código</th>
						<th>Nome</th>
						<th>Quantidade de páginas</th>
						<th>ISSN</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="l" items="${revistas }">
					<tr>
						<td><c:out value="${l.codigo }"></c:out></td>
						<td><c:out value="${l.nome }"></c:out></td>
						<td><c:out value="${l.qtdPaginas }"></c:out></td>
						<td><c:out value="${l.ISSN }"></c:out></td>
					</tr>
				</c:forEach>	
				</tbody>
			</table>	
		</c:if>
	</div>
</body>
</html>