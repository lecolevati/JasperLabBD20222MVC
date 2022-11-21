<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css"/>'>
<title>F1 2014</title>
</head>
<body>
	<div align="center">
		<form action="relatorio" method="post" target="_blank">
			<table>
				<tr>
					<td>
						<input class="id_input_data" type="text" min="0"
							step="1" id="pais" name="pais" placeholder="Pais">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Gerar Relatório">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br />
	<br />
	<div align="center">
		<c:if test="${not empty erro }">
			<H2><c:out value="${erro }" /></H2>
		</c:if>
	</div>
</body>
</html>