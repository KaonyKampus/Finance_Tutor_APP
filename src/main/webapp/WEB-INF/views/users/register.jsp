<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Register</title>
	</head>
	<body>
		<h1>Register</h1>

		<p>${error}</p>
		<p>${success}</p>
		<form method="POST" action="${pageContext.request.contextPath}/admin/users/new">

			<label>Nombre</label>
			<input type="text" name="name">

			<label>Correo</label>
			<input type="email" name="email">

			<label>Contraseña</label>
			<input type="password" name="password">

			<label>Rol</label>
			<select name="role">
				<option value="PRINCIPAL">Usuario principal</option>
				<option value="TUTOR">Tutor</option>
				<option value="ADMIN">Administrador</option>
			</select>

			<button type="submit">Registrar</button>
		</form>



	</body>
</html>
