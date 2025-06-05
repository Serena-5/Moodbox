<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<div class="container">
    <h2>Accedi</h2>

    <!-- Mostra messaggio di errore se esiste -->
    <c:if test="${not empty errorMessage}">
        <div class="errore">${errorMessage}</div>
    </c:if>

   <!-- <form action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLogin()">-->
    
        <label for="email">Email:</label><br>
        <input type="email" name="email" id="email" required><br><br>

        <label for="password">Password:</label><br>
        <input type="password" name="password" id="password" required><br><br>

        <input type="submit" value="Login">
    <!--  </form>-->

    <div id="error-message" class="errore-js"></div>
</div>

<!--  <script src="${pageContext.request.contextPath}/scripts/login.js"></script> -->
<%@ include file="partials/footer.jsp" %>
