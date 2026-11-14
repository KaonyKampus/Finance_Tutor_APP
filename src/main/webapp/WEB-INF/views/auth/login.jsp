<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Iniciar sesión · FinanceTutor</title>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/img/favicon-login.svg">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
<div class="glass-page">
    <div class="blob blob-1"></div>
    <div class="blob blob-2"></div>
    <div class="blob blob-3"></div>

    <div class="glass-card">
        <div class="glass-brand">
            <svg viewBox="0 0 24 24" fill="none" stroke="#E9A56A" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                <rect x="4" y="11" width="16" height="10" rx="2"/>
                <path d="M8 11V7a4 4 0 0 1 8 0v4"/>
            </svg>
            <h1>Finance<span>Tutor</span></h1>
        </div>
        <p class="glass-subtitle">Gestor de finanzas tutelado</p>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label for="email">Correo</label>
                <input type="email" id="email" name="email" placeholder="tucorreo@mail.com" autocomplete="username">
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <div class="password-wrap">
                    <input type="password" id="password" name="password" placeholder="••••••••" autocomplete="current-password">
                    <button type="button" class="toggle-password" id="togglePassword" aria-label="Mostrar contraseña">
                        <svg id="eyeIcon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"/>
                            <circle cx="12" cy="12" r="3"/>
                        </svg>
                    </button>
                </div>
            </div>
            <button type="submit" class="btn-glass">Ingresar</button>
        </form>

        <p class="glass-footer">Proyecto académico · David Silva y Sofía Caina</p>
    </div>
</div>

<script>
    const toggle = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");
    toggle.addEventListener("click", () => {
        const hidden = passwordInput.type === "password";
        passwordInput.type = hidden ? "text" : "password";
        toggle.setAttribute("aria-label", hidden ? "Ocultar contraseña" : "Mostrar contraseña");
        toggle.style.color = hidden ? "#E9A56A" : "";
    });
</script>
</body>
</html>