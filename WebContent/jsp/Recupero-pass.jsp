<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Recupero Password</title>
</head>
<body>

<section class="banner">
  <form class="glass" action="${pageContext.request.contextPath}/RecuperoServlet" method="post">
    <h2>Recupero Password</h2>

    <% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
        <p style="color:green;"><%= message %></p>
    <% } %>

    <div class="input-wrap">
      <input type="email" id="email" name="email" placeholder="Email" required />
    </div>

    <button class="btn" type="submit">Invia link di recupero</button>
    <p style="text-align: center; margin-top: 10px;">
      <a href="${pageContext.request.contextPath}/jsp/login.jsp" style="color: white; text-decoration: underline;">
        Torna al login
      </a>
    </p>
  </form>
</section>

</body>
</html>