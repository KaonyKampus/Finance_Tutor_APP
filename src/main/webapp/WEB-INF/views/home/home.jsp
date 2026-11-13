<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
		<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">	
	</head>
	<body>
		<h1>Hola, ${userName}</h1>
		<c:if test="${not empty account}">

			<h2>Tu saldo: $${account.balance}</h2>
			<p>Límite diario: $${account.dailyLimit}</p>

			<p style="color:red">${error}</p>
			<p style="color:green">${success}</p>

			<h3>Ingresar dinero</h3>
			<form method="POST" action="${pageContext.request.contextPath}/home">
				<input type="hidden" name="type" value="INCOME">

				<label>Monto</label>
				<input type="number" name="amount" step="0.01" min="0.01">

				<label>Descripción</label>
				<input type="text" name="description">

				<button type="submit">Ingresar</button>
			</form>

			<h3>Retirar dinero</h3>
			<form method="POST" action="${pageContext.request.contextPath}/home">
				<input type="hidden" name="type" value="WITHDRAWAL">

				<label>Monto</label>
				<input type="number" name="amount" step="0.01" min="0.01">

				<label>Descripción</label>
				<input type="text" name="description">

				<button type="submit">Retirar</button>
			</form>

			<h3>Historial de movimientos</h3>
			<table border="1">
				<tr>
					<th>Fecha</th>
					<th>Tipo</th>
					<th>Monto</th>
					<th>Descripción</th>
					<th>Saldo después</th>
				</tr>

				<c:forEach var="t" items="${history}">
					<tr>
						<td>${t.createdAt}</td>
						<td>${t.type}</td>
						<td>${t.amount}</td>
						<td>${t.description}</td>
						<td>${t.balanceAfter}</td>
					</tr>

				</c:forEach>
			</table>
		</c:if>

		<c:if test="${not empty wards}">
			<h2>Mis tutelados</h2>
			<table border="1">
				<tr>
					<th>Nombre</th>
					<th>Saldo</th>
					<th>Límite diario</th>
				</tr>
				<c:forEach var="acc" items="${wards}">
					<tr>
						<td>${acc.user.name}</td>
						<td>$${acc.balance}</td>
						<td>$${acc.dailyLimit}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<p>Tu rol es: ${userRole}</p>
		<a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
	</body>
</html>
