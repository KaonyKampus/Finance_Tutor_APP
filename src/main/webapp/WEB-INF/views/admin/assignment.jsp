<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Vincular tutor · FinanceTutor</title>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/img/favicon-assign.svg">
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
        <h1>Vincular tutor con usuario</h1>
        <p>Un usuario principal puede tener varios tutores.</p>
    </div>

    <div class="card" style="max-width: 560px;">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/admin/assignments">
            <div class="form-group">
                <label for="tutorId">Tutor</label>
                <select id="tutorId" name="tutorId">
                    <c:forEach var="u" items="${tutors}">
                        <option value="${u.id}">${u.name} (${u.email})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="principalId">Usuario principal</label>
                <select id="principalId" name="principalId">
                    <c:forEach var="u" items="${principals}">
                        <option value="${u.id}">${u.name} (${u.email})</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-accent">Vincular</button>
        </form>
    </div>
</main>

<p class="footer">FinanceTutor · Proyecto académico de David Silva/ Sofia Caina</p>
</body>
</html>