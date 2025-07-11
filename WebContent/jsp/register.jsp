<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<div class="container" style="max-width: 500px; margin: auto;">
    <h2>Registrazione</h2>

    <!-- Messaggi -->
    <c:if test="${not empty errorMessage}">
        <p style="color: red;"><c:out value="${errorMessage}" /></p>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p style="color: green;"><c:out value="${successMessage}" /></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <label for="nome">Nome:</label><br>
        <input type="text" id="nome" name="nome" value="<c:out value='${nome}' />" required><br><br>

        <label for="cognome">Cognome:</label><br>
        <input type="text" id="cognome" name="cognome" value="<c:out value='${cognome}' />" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="<c:out value='${email}' />" required><br><br>

        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <label for="confirmPassword">Conferma Password:</label><br>
        <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

        <input type="checkbox" id="newsletter" name="newsletter"
               <c:if test="${newsletter}">checked</c:if>>
        <label for="newsletter">Iscrivimi alla newsletter</label><br><br>

        <input type="submit" value="Registrati">
    </form>

    <p>Hai già un account?
        <a href="${pageContext.request.contextPath}/login">Accedi</a>
    </p>
</div>
