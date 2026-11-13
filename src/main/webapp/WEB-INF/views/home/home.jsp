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

			<h3>Mis solicitudes</h3>
			<table border="1">
				<tr>
					<th>Fecha</th>
					<th>Monto</th>
					<th>Estado</th>
				</tr>
				<c:forEach var="r" items="${requests}">
					<tr>
						<td>${r.createdAt}</td>
						<td>$${r.amount}</td>
						<td>${r.status}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<c:if test="${not empty wards}">
			<h2>Mis tutelados</h2>
			<p>${limitMessage}</p>
			<c:remove var="limitMessage" scope="session"/>
			<table border="1">
				<tr>
					<th>Nombre</th>
					<th>Saldo</th>
					<th>Límite diario</th>
					<th>Cambiar límite</th>
				</tr>
				<c:forEach var="acc" items="${wards}">
					<tr>
						<td>${acc.user.name}</td>
						<td>$${acc.balance}</td>
						<td>$${acc.dailyLimit}</td>
						<td>      

							<form method="POST" action="${pageContext.request.contextPath}/tutor/limit">
								<input type="hidden" name="accountId" value="${acc.id}">
								<input type="number" name="dailyLimit" step="0.01" min="0">
								<button type="submit">Guardar</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			<h3>Solicitudes pendientes</h3>
			<p>${requestMessage}</p>
			<c:remove var="requestMessage" scope="session"/>

			<table border="1">
				<tr>
					<th>Fecha</th>
					<th>Tutelado</th>
					<th>Monto</th>
					<th>Decisión</th>
				</tr>
				<c:forEach var="r" items="${pending}">
					<tr>
						<td>${r.createdAt}</td>
						<td>${r.requestedBy.name}</td>
						<td>$${r.amount}</td>
						<td>
							<form method="POST" action="${pageContext.request.contextPath}/tutor/requests">
								<input type="hidden" name="requestId" value="${r.id}">
								<button type="submit" name="action" value="approve">Aprobar</button>
								<button type="submit" name="action" value="reject">Rechazar</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<p>Tu rol es: ${userRole}</p>
		<a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
	</body>
</html>
