<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar usuario · FinanceTutor</title>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/img/favicon-register.svg">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>

<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/admin/users/new">Finance<span>Tutor</span></a>
    <div class="topbar-right">
        <a href="${pageContext.request.contextPath}/admin/users/new">Registrar usuarios</a>
        <a href="${pageContext.request.contextPath}/admin/assignments">Vincular tutores</a>
        <span class="role-badge">${userRole}</span>
        <a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
    </div>
</header>

<main class="container">
    <div class="page-title">
        <h1>Registrar usuario</h1>
        <p>Crea cuentas para usuarios principales, tutores y administradores.</p>
    </div>

    <div class="card" style="max-width: 560px;">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/admin/users/new">
            <div class="form-group">
                <label for="name">Nombre</label>
                <input type="text" id="name" name="name">
            </div>
            <div class="form-group">
                <label for="email">Correo</label>
                <input type="email" id="email" name="email">
            </div>
            <div class="form-group">
                <label for="password">Contraseña (mínimo 8 caracteres)</label>
                <input type="password" id="password" name="password">
            </div>
            <div class="form-group">
                <label for="role">Rol</label>
                <select id="role" name="role">
                    <option value="PRINCIPAL">Usuario principal</option>
                    <option value="TUTOR">Tutor</option>
                    <option value="ADMIN">Administrador</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
    </div>
</main>

<p class="footer">FinanceTutor · Proyecto académico de David Silva /Sofia Caina </p>
</body>
</html>