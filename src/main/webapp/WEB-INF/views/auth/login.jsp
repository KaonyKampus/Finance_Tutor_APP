
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Iniciar Sesión</title>
	</head>
	<body>
		<h1>Estas a un paso de ingresar a Finance Tutor APP</h1>

		<p>${error}</p>
		<form method="POST" action="${pageContext.request.contextPath}/login">


			<label>Correo</label>
			<input type="email" name="email">

			<label>Contraseña</label>
			<input type="password" name="password">



			<button type="submit">Ingresar</button>
		</form>

	</body>
</html>
