<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>MoodBox</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body data-context="${pageContext.request.contextPath}" data-totale-prodotti="${totaleProdotti}">

<c:if test="${empty noDefaultNav}">
  <header>
    <div class="nav-container">

      <a href="${pageContext.request.contextPath}/home" class="logo">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoodBox">
      </a>

      

      <div class="icon-wrapper">

        <c:if test="${sessionScope.ruolo == 'admin'}">
          <a href="${pageContext.request.contextPath}/admin/catalogo" class="admin-link">Gestione catalogo</a>
        </c:if>

        <div class="user-dropdown">
          <img src="${pageContext.request.contextPath}/images/user.png"
               alt="Utente" class="user-icon" id="user-toggle">

          <div class="dropdown-menu" id="user-menu">
            <c:choose>
              <c:when test="${empty sessionScope.utente}">
                <a href="${pageContext.request.contextPath}/login">Login</a>
                <a href="${pageContext.request.contextPath}/register">Registrati</a>
              </c:when>
              <c:when test="${sessionScope.ruolo == 'admin'}">
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/ordini">I miei ordini</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
              </c:otherwise>
            </c:choose>
          </div>
        </div>

        <c:if test="${empty sessionScope.ruolo || sessionScope.ruolo ne 'admin'}">
          <a href="${pageContext.request.contextPath}/carrello" class="cart-btn">
            <img src="${pageContext.request.contextPath}/images/shopping-cart.svg" alt="Carrello" class="cart-icon" />
            <span class="cart-count" id="cart-counter">
              <c:out value="${sessionScope.carrelloCount != null ? sessionScope.carrelloCount : 0}" />
            </span>
          </a>
        </c:if>

      </div>
    </div>
  </header>

  <script src="${pageContext.request.contextPath}/scripts/user-menu.js"></script>
</c:if>
