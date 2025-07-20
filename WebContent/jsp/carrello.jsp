<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<script>
  document.body.classList.add('catalogo-moodbox-bg');
</script>

<div class="container">
  <h2>Il tuo carrello</h2>

  <c:choose>

    <%-- ===== CARRELLO VUOTO ===== --%>
    <c:when test="${empty articoli}">
     
  <div class="empty-cart-fullscreen">
  <img src="${pageContext.request.contextPath}/images/cart-vuoto.png" alt="Carrello vuoto">

  <h3>Il tuo carrello è vuoto!</h3>
  <p>Scopri le nostre MoodBox e trova quella perfetta per te.</p>
  <a href="${pageContext.request.contextPath}/box" class="btn-primary">Vai al catalogo</a>
</div>


      
    </c:when>

    <%-- ===== CARRELLO CON PRODOTTI ===== --%>
    <c:otherwise>

      <%-- Calcola totale prodotti --%>
      <c:set var="totale" value="0" />
      <c:forEach var="a" items="${articoli}">
        <c:set var="totale" value="${totale + (a.box.prezzo * a.quantita)}" />
      </c:forEach>

      <table border="1" cellpadding="6">
        <thead>
          <tr>
            <th>Immagine</th>
            <th>Prodotto</th>
            <th>Quantità</th>
            <th>Prezzo singolo</th>
            <th>Totale riga</th>
            <th>Azioni</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="a" items="${articoli}">
            <tr>
              <!-- Immagine -->
              <td>
                <img src="${pageContext.request.contextPath}/images_uploaded/${a.box.immagine}"
                     alt="${a.box.nome}" width="60" />
              </td>

              <td>${a.box.nome}</td>

              <!-- Quantità editabile -->
              <td>
                <form action="${pageContext.request.contextPath}/carrello" method="post" style="display:inline;">
                  <input type="hidden" name="action"  value="update" />
                  <input type="hidden" name="boxId"   value="${a.boxId}" />
                  <input type="number" name="quantita" value="${a.quantita}" min="0" style="width:70px;" />
                  <button type="submit">Aggiorna</button>
                </form>
              </td>

              <td>€ ${a.box.prezzo}</td>
              <td>€ ${a.box.prezzo * a.quantita}</td>

              <!-- Pulsante Rimuovi -->
              <td>
                <form action="${pageContext.request.contextPath}/carrello" method="post" style="display:inline;">
                  <input type="hidden" name="action" value="remove" />
                  <input type="hidden" name="boxId"  value="${a.boxId}" />
                  <button type="submit">Rimuovi</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <h3>Totale carrello: € ${totale}</h3>

      <%-- Pulsante di checkout --%>
      <c:choose>

        <%-- Utente loggato --> vai al checkout --%>
        <c:when test="${not empty sessionScope.utente}">
          <form action="${pageContext.request.contextPath}/checkout" method="get" style="margin-top:1rem;">
            <button type="submit">Procedi all’ordine</button>
          </form>
        </c:when>

        <%-- Guest --> richiedi login/registrazione --%>
        <c:otherwise>
          <p>
            Per completare l'acquisto effettua il
            <a href="${pageContext.request.contextPath}/login">login</a>
            o
            <a href="${pageContext.request.contextPath}/register">registrati</a>.
          </p>
        </c:otherwise>

      </c:choose>
    </c:otherwise>

  </c:choose>
</div>
<%@ include file="/jsp/partials/footer.jsp" %>