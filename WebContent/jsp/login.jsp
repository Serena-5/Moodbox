<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="noDefaultNav" value="true"/>   <!-- disattiva la navbar standard -->
<%@ include file="partials/header.jsp" %>  <!-- porta dentro <head> + CSS -->


<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>MoodBox - Login</title>
</head>
<body>

<!-- Banner + form -->
<section class="banner">
  <form class="glass" action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLogin()">
    <h2>Accedi</h2>

    <!-- errori lato server -->
    <c:if test="${param.error == 'accesso-negato'}">
      <div class="errore">Accesso negato. Effettua il login per continuare.</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
      <div class="errore">${errorMessage}</div>
    </c:if>

    <!-- email -->
    <div class="input-wrap">
      <img src="${pageContext.request.contextPath}/images/user.png" alt="user icon">
      <input type="email" id="email" name="email" placeholder="Email" required>
    </div>

    <!-- password -->
    <div class="input-wrap">
      <img src="${pageContext.request.contextPath}/images/lock.png" alt="lock icon">
      <input type="password" id="password" name="password" placeholder="Password" required>
    </div>

    <button class="btn" type="submit">Login</button>

    <!-- spazio per messaggi JS -->
    <div id="error-message" class="errore"></div>
<p style="text-align: center; margin-top: 10px;">
  <a href="${pageContext.request.contextPath}/home" style="color: white; text-decoration: underline;">
    Torna alla home
  </a>
</p>

  </form>
</section>
<script src="${pageContext.request.contextPath}/scripts/login-validation.js"></script>

</body>
</html>
