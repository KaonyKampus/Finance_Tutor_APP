<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Inicio · FinanceTutor</title>
		<c:choose>
			<c:when test="${userRole == 'TUTOR'}">
				<link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/img/favicon-home-tutor.svg">
			</c:when>
			<c:when test="${userRole == 'ADMIN'}">
				<link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/img/favicon-home-admin.svg">
			</c:when>
			<c:otherwise>
				<link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/img/favicon-home-principal.svg">
			</c:otherwise>
		</c:choose>
		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
	</head>
	<body>

		<header class="topbar">
			<a class="brand" href="${pageContext.request.contextPath}/home">Finance<span>Tutor</span></a>
			<div class="topbar-right">
				<span>${userName}</span>
				<span class="role-badge">${userRole}</span>
				<a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
			</div>
		</header>

		<main class="container">

			<div class="page-title">
				<h1>Hola, ${userName}</h1>
				<p>
					<c:choose>
						<c:when test="${not empty account}">Este es el resumen de tu cuenta.</c:when>
						<c:when test="${userRole == 'TUTOR'}">Supervisa a tus tutelados y responde sus solicitudes.</c:when>
						<c:otherwise>Bienvenido a FinanceTutor.</c:otherwise>
					</c:choose>
				</p>
			</div>

			<%-- ===================== USUARIO PRINCIPAL ===================== --%>
			<c:if test="${not empty account}">

				<div class="card balance-card">
					<div>
						<div class="label">Saldo disponible</div>
						<div class="amount">$${account.balance}</div>
					</div>
					<div class="limit">
						Límite diario
						<strong>$${account.dailyLimit}</strong>
					</div>
				</div>

				<c:if test="${not empty error}">
					<div class="alert alert-error">${error}</div>
				</c:if>
				<c:if test="${not empty success}">
					<div class="alert alert-success">${success}</div>
				</c:if>

				<div class="grid-2">
					<div class="card">
						<h3>Ingresar dinero</h3>
						<form method="POST" action="${pageContext.request.contextPath}/home">
							<input type="hidden" name="type" value="INCOME">
							<div class="form-group">
								<label>Monto</label>
								<input type="number" name="amount" step="0.01" min="0.01" placeholder="0.00">
							</div>
							<div class="form-group">
								<label>Descripción</label>
								<input type="text" name="description" placeholder="Ej: mesada">
							</div>
							<button type="submit" class="btn btn-success">Ingresar</button>
						</form>
					</div>

					<div class="card">
						<h3>Retirar dinero</h3>
						<form method="POST" action="${pageContext.request.contextPath}/home">
							<input type="hidden" name="type" value="WITHDRAWAL">
							<div class="form-group">
								<label>Monto</label>
								<input type="number" name="amount" step="0.01" min="0.01" placeholder="0.00">
							</div>
							<div class="form-group">
								<label>Descripción</label>
								<input type="text" name="description" placeholder="Ej: mercado">
							</div>
							<button type="submit" class="btn btn-danger">Retirar</button>
						</form>
					</div>
				</div>

				<div class="card" style="margin-top: 20px;">
					<h3>Historial de movimientos</h3>
					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>Fecha</th>
									<th>Tipo</th>
									<th>Descripción</th>
									<th class="num">Monto</th>
									<th class="num">Saldo después</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="t" items="${history}">
									<tr>
										<td>${fn:substring(fn:replace(t.createdAt, 'T', ' '), 0, 16)}</td>
										<td>
											<c:choose>
												<c:when test="${t.type == 'INCOME'}"><span class="badge badge-income">Ingreso</span></c:when>
												<c:otherwise><span class="badge badge-withdrawal">Retiro</span></c:otherwise>
											</c:choose>
										</td>
										<td>${t.description}</td>
										<td class="num ${t.type == 'INCOME' ? 'amount-in' : 'amount-out'}">
											${t.type == 'INCOME' ? '+' : '-'}$${t.amount}
										</td>
										<td class="num">$${t.balanceAfter}</td>
									</tr>
								</c:forEach>
								<c:if test="${empty history}">
									<tr><td colspan="5" class="empty">Todavía no tienes movimientos.</td></tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>

				<div class="card">
					<h3>Mis solicitudes de autorización</h3>
					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>Fecha</th>
									<th class="num">Monto</th>
									<th>Estado</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="r" items="${requests}">
									<tr>
										<td>${fn:substring(fn:replace(r.createdAt, 'T', ' '), 0, 16)}</td>
										<td class="num">$${r.amount}</td>
										<td>
											<c:choose>
												<c:when test="${r.status == 'APPROVED'}"><span class="badge badge-approved">Aprobada</span></c:when>
												<c:when test="${r.status == 'REJECTED'}"><span class="badge badge-rejected">Rechazada</span></c:when>
												<c:otherwise><span class="badge badge-pending">Pendiente</span></c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty requests}">
									<tr><td colspan="3" class="empty">No tienes solicitudes.</td></tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>

			<%-- ===================== TUTOR ===================== --%>
			<c:if test="${not empty wards}">

				<div class="card">
					<h3>Mis tutelados</h3>
					<c:if test="${not empty limitMessage}">
						<div class="alert alert-info">${limitMessage}</div>
					</c:if>
					<c:remove var="limitMessage" scope="session"/>

					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>Nombre</th>
									<th class="num">Saldo</th>
									<th class="num">Límite diario</th>
									<th>Cambiar límite</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="acc" items="${wards}">
									<tr>
										<td><strong>${acc.user.name}</strong></td>
										<td class="num">$${acc.balance}</td>
										<td class="num">$${acc.dailyLimit}</td>
										<td>
											<form class="inline-form" method="POST" action="${pageContext.request.contextPath}/tutor/limit">
												<input type="hidden" name="accountId" value="${acc.id}">
												<input type="number" name="dailyLimit" step="0.01" min="0" placeholder="Nuevo límite">
												<button type="submit" class="btn btn-primary btn-sm">Guardar</button>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="card">
					<h3>Solicitudes pendientes</h3>
					<c:if test="${not empty requestMessage}">
						<div class="alert alert-info">${requestMessage}</div>
					</c:if>
					<c:remove var="requestMessage" scope="session"/>

					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>Fecha</th>
									<th>Tutelado</th>
									<th class="num">Monto</th>
									<th>Decisión</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="r" items="${pending}">
									<tr>
										<td>${fn:substring(fn:replace(r.createdAt, 'T', ' '), 0, 16)}</td>
										<td>${r.requestedBy.name}</td>
										<td class="num">$${r.amount}</td>
										<td>
											<form class="inline-form" method="POST" action="${pageContext.request.contextPath}/tutor/requests">
												<input type="hidden" name="requestId" value="${r.id}">
												<button type="submit" name="action" value="approve" class="btn btn-success btn-sm">Aprobar</button>
												<button type="submit" name="action" value="reject" class="btn btn-danger btn-sm">Rechazar</button>
											</form>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty pending}">
									<tr><td colspan="4" class="empty">No hay solicitudes pendientes.</td></tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>

			<c:if test="${userRole == 'TUTOR' and empty wards}">
				<div class="card empty">Todavía no tienes tutelados vinculados. Pídele al administrador que te vincule.</div>
			</c:if>

		</main>

		<p class="footer">FinanceTutor · Proyecto académico de David Silva / Sofia Caina</p>
	</body>
</html>