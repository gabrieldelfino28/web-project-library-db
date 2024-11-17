<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/styles.css">
<title>Aluguel</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<br>
	<div align="center" class="container">
		<form action="aluguel" method="post">
			<p class="title">
				<b>Aluguel</b>
			</p>
			<table>
				<tr>
					<td colspan="5">
						<select class="input_data" id="aluno" name="aluno" style="width: 350px">
							<option value="0">Aluno</option>
							<c:forEach var="a" items="${alunos }">
								<c:if test="${(empty aluguel) || (a.ra ne aluguel.aluno.ra) }">
									<option value="${a.ra }">
										<c:out value="${a }"/>
									</option>
								</c:if>
								<c:if test="${a.ra eq aluguel.aluno.ra }">
									<option value="${a.ra }" selected="selected">
										<c:out value="${a }"/>
									</option>
								</c:if>
							</c:forEach>
						</select>	
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<select class="input_data" id="exemplar" name="exemplar" style="width: 350px">
							<option value="0">Exemplar</option>
							<c:forEach var="e" items="${exemplares }">
								<c:if test="${(empty aluguel) || (e.codigo ne aluguel.exemplar.codigo) }">
									<option value="${e.codigo }">
										<c:out value="${e }"/>
									</option>
								</c:if>
								<c:if test="${e.codigo eq aluguel.exemplar.codigo }">
									<option value="${e.codigo }" selected="selected">
										<c:out value="${e }"/>
									</option>
								</c:if>
							</c:forEach>
						</select>	
					</td>
				</tr>
				<tr>
					<td colspan ="1"><b>Data de Retirada:</b></td>
					<td colspan="3">
						<input class="id_input_data" type="date" id="dataRetirada" name="dataRetirada" placeholder="Data de Retirada" style="width: 150px"
						value='<c:out value="${aluguel.dataRetirada }"></c:out>'>
					</td>
					<td> 
						<input type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan ="1"><b>Data de Devolução:</b></td>
					<td colspan="4">
						<input class="id_input_data" type="date" id="dataDevolucao" name="dataDevolucao" placeholder="Data de Devolução" style="width: 150px"
						value='<c:out value="${aluguel.dataDevolucao }"></c:out>'>
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
		<c:if test="${not empty alugueis }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Aluno</th>
						<th>Exemplar</th>
						<th>Data de Retirada</th>
						<th>Data de Devolução</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="a" items="${alugueis }">
					<tr>
						<td><c:out value="${a.aluno.nome }"></c:out></td>
						<td><c:out value="${a.exemplar.nome }"></c:out></td>
						<td><c:out value="${a.dataRetirada }"></c:out></td>
						<td><c:out value="${a.dataDevolucao}"></c:out></td>
					</tr>
				</c:forEach>	
				</tbody>
			</table>	
		</c:if>
	</div>

</body>
</html>