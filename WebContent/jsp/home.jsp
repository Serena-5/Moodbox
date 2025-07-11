<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<div class="container" style="max-width: 800px; margin: auto;">
    <!-- Saluto personalizzato -->
    <c:choose>
        <c:when test="${not empty sessionScope.utente}">
            <h2>Benvenuto, <c:out value="${sessionScope.utente.nome}" />!</h2>
        </c:when>
        <c:otherwise>
            <h2>Benvenuto in Moodbox!</h2>
        </c:otherwise>
    </c:choose>

    <!-- Sezione contenuti -->
    <p>Questa è la tua home. Qui potrai:</p>
    <ul>
        <li>Visualizzare i tuoi ultimi post o attività</li>
        <li>Accedere al profilo personale</li>
        <li>Gestire le preferenze di newsletter</li>
        <c:if test="${sessionScope.ruolo == 'admin'}">
            <li>Gestire l’area amministratore</li>
        </c:if>
    </ul>

    <!-- Link utili -->
    <p>
        <a href="${pageContext.request.contextPath}/profilo">Profilo</a>
        <c:if test="${sessionScope.ruolo == 'admin'}">
            | <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard Admin</a>
        </c:if>
        | <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </p>
</div>


