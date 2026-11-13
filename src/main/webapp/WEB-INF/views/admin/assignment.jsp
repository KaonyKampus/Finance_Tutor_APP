<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vincular tutor</title>
</head>
<body>
    <h1>Vincular tutor con usuario</h1>

    <p style="color:red">${error}</p>
    <p style="color:green">${success}</p>

    <form method="POST" action="${pageContext.request.contextPath}/admin/assignments">

        <label>Tutor</label>
        <select name="tutorId">
            <c:forEach var="u" items="${tutors}">
                <option value="${u.id}">${u.name} (${u.email})</option>
            </c:forEach>
        </select>

        <label>Usuario principal</label>
        <select name="principalId">
            <c:forEach var="u" items="${principals}">
                <option value="${u.id}">${u.name} (${u.email})</option>
            </c:forEach>
        </select>

        <button type="submit">Vincular</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/admin/users/new">Registrar usuarios</a></p>
    <p><a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a></p>
</body>
</html>