<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
	</head>
	<body>
		<h1>Hola, ${userName}</h1>
		<c:if test="${not empty account}">
			<h2>Tu saldo: $${account.balance}</h2>
			<p>Límite diario: $${account.dailyLimit}</p>
		</c:if>
		<p>Tu rol es: ${userRole}</p>
		<a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
	</body>
</html>
