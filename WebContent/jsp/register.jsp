<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="noDefaultNav" value="true"/>
<%@ include file="partials/header.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>MoodBox - Registrati</title>
</head>
<body>

<section class="banner">
  <form class="glass" action="${pageContext.request.contextPath}/register" method="post">
    <h2>Registrazione</h2>

    <!-- messaggi lato server -->
    <c:if test="${not empty errorMessage}">
      <div class="errore"><c:out value='${errorMessage}'/></div>
    </c:if>
    <c:if test="${not empty successMessage}">
      <div class="successo"><c:out value='${successMessage}'/></div>
    </c:if>

    <!-- campi -->
    <label for="nome">Nome</label>
    <input type="text" id="nome" name="nome" value="<c:out value='${nome}'/>" required>

    <label for="cognome">Cognome</label>
    <input type="text" id="cognome" name="cognome" value="<c:out value='${cognome}'/>" required>

    <label for="email">Email</label>
    <input type="email" id="email" name="email" value="<c:out value='${email}'/>" required>

    <label for="password">Password</label>
    <input type="password" id="password" name="password" required>

    <label for="confirmPassword">Conferma Password</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>

    <label style="display:flex;align-items:center;margin-top:18px">
      <input type="checkbox" id="newsletter" name="newsletter"
             <c:if test='${newsletter}'>checked</c:if>>
      <span style="margin-left:8px">Iscrivimi alla newsletter</span>
    </label>

    <button class="btn" type="submit">Registrati</button>

    <p style="margin-top:20px;font-size:.9rem;text-align:center">
      Hai già un account?
      <a href="${pageContext.request.contextPath}/login">Accedi</a>
    </p>
  </form>
</section>

</body>
</html>
