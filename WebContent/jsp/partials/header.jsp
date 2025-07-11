<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>MoodBox</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<header>
    <div class="nav-container">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/" class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoodBox">
        </a>

        <!-- Nav menu -->
        <nav>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/box">Catalogo</a></li>
                
                <c:choose>
                    <c:when test="${empty sessionScope.utente}">
                        <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">Registrati</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        <c:if test="${sessionScope.ruolo == 'admin'}">
                            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a></li>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>

        <!-- Carrello -->
        <a href="${pageContext.request.contextPath}/carrello" class="cart-btn">
    <img src="${pageContext.request.contextPath}/images/shopping-cart.svg" alt="Carrello" class="cart-icon" />
    <span class="cart-count">
        <c:out value="${sessionScope.carrello != null ? sessionScope.carrello.size() : 0}" />
    	</span>
	</a>
   </div>
</header>
