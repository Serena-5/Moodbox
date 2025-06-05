<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>MoodBox</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
    <header>
        <h1><a href="${pageContext.request.contextPath}/">MoodBox</a></h1>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                <c:choose>
                    <c:when test="${empty sessionScope.utente}">
                        <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">Registrati</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        <li><a href="${pageContext.request.contextPath}/carrello">Carrello</a></li>
                        <c:if test="${sessionScope.ruolo == 'admin'}">
                            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a></li>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
        <hr>
    </header>
