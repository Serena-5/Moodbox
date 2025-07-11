<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<%
    /* ─── Protezione: se carrello mancante o vuoto → torna al carrello ─── */
    java.util.Map<?,?> cart = (java.util.Map<?,?>) session.getAttribute("carrello");
    if (cart == null || cart.isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/carrello");
        return;
    }

    /* Protezione: solo utenti loggati possono proseguire */
    if (session.getAttribute("utente") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<div class="container" style="max-width:600px;margin:auto;">
  <h2>Checkout</h2>

  <!-- Eventuale messaggio da query-string ?errore= -->
  <c:if test="${not empty param.errore}">
      <p style="color:red;">Si è verificato un errore: <c:out value="${param.errore}" /></p>
  </c:if>

  <!-- Riepilogo importi -->
  <c:set var="totaleProdotti" value="0" />
  <c:forEach var="articolo" items="${sessionScope.carrello.values()}">
      <c:set var="totaleProdotti"
             value="${totaleProdotti + (articolo.box.prezzo * articolo.quantita)}" />
  </c:forEach>

  <p>Totale prodotti: <strong>€ <c:out value="${totaleProdotti}" /></strong></p>
  <p>Costo spedizione: <strong>€ 5,00</strong></p>

  <c:set var="totaleOrdine" value="${totaleProdotti + 5.0}" />
  <p><strong>Totale ordine: € <c:out value="${totaleOrdine}" /></strong></p>

  <!-- Form di conferma ordine -->
  <form action="${pageContext.request.contextPath}/ordine" method="post" style="margin-top:20px;">

      <label for="indirizzoSpedizione">Indirizzo di spedizione *</label><br>
      <input type="text" id="indirizzoSpedizione" name="indirizzoSpedizione"
             required style="width:100%;"><br><br>

      <label for="metodoSpedizione">Metodo di spedizione *</label><br>
      <select id="metodoSpedizione" name="metodoSpedizione" required>
          <option value="Standard">Standard (3-5 gg) – € 5,00</option>
          <option value="Express">Express (24-48 h) – € 9,90</option>
      </select><br><br>

      <label for="noteOrdine">Note per l’ordine (facoltative)</label><br>
      <textarea id="noteOrdine" name="noteOrdine" rows="3" style="width:100%;"></textarea><br><br>

      <button type="submit">Conferma ordine</button>
  </form>
</div>
