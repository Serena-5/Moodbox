<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="noDefaultNav" value="true"/>
<%@ include file="partials/header.jsp" %>


<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>MoodBox - Home</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css" />
</head>
<body>
  <!-- ===== NAVBAR ===== -->
  <header class="nav">
  
    <a href="${pageContext.request.contextPath}/home" class="nav-logo">
      <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoodBox">
    </a>
    <input type="checkbox" id="toggle">
    <label for="toggle" class="hamb"></label>
    <ul>
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/box">Catalogo</a></li>
      <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
      <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
      </ul>
  </header>

  <!-- ===== HERO SECTION ===== -->
<section class="hero">
  <div class="hero-left">
    <div class="brand">
      <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoodBox">
    </div>
    <h1>Benvenuto in MoodBox</h1>
    <p>Scopri la magia delle nostre box a tema: ogni scatola è un viaggio sensoriale che trasforma il tuo umore in un'esperienza unica da vivere e condividere.</p>
    <a href="${pageContext.request.contextPath}/box" class="cta">Scopri le box</a>
  </div>
  <div class="hero-right"></div>
</section>

</body>
</html>
