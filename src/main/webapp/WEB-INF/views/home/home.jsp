<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
	</head>
	<body>
		<h1>Hola, ${userName}</h1>
		<<p>Tu rol es: ${userRole}</p>
		<a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
	</body>
</html>
