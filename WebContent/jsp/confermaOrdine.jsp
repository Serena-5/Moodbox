<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="partials/header.jsp" %>

<script>
  document.body.classList.add('catalogo-moodbox-bg');
</script>

<!-- banner con card bianca -->
<section class="confirm-page">
  <div class="confirm-box">
    <h2>Grazie per il tuo ordine!</h2>

    <p>
      Il tuo ordine è stato registrato correttamente.
    </p>

    <!-- Riepilogo facoltativo -->
    <c:if test="${not empty sessionScope.carrelloConfermato}">
      <div class="order-info">
        <h3>Riepilogo ordine</h3>
        <table>
          <thead>
            <tr>
              <th>Prodotto</th>
              <th style="text-align:center;">Qtà</th>
              <th style="text-align:right;">Totale</th>
            </tr>
          </thead>
          <tbody>
            <c:set var="totale" value="0"/>
            <c:forEach var="r" items="${sessionScope.carrelloConfermato}">
              <tr>
                <td>${r.box.nome}</td>
                <td style="text-align:center;">${r.quantita}</td>
                <td style="text-align:right;">€ ${r.totaleRiga}</td>
              </tr>
              <c:set var="totale" value="${totale + r.totaleRiga}"/>
            </c:forEach>
          </tbody>
        </table>

        <p style="margin-top:10px;text-align:right;">
          <strong>TOTALE: € ${totale}</strong>
        </p>
      </div>

      <!-- Pulisci il riepilogo dalla sessione -->
      <%
        session.removeAttribute("carrelloConfermato");
      %>
    </c:if>

    <p>Riceverai una mail con i dettagli appena l’ordine sarà stato spedito.</p>

    <div class="btn-row">
      <a href="${pageContext.request.contextPath}/home" class="button">Home</a>
    </div>
  </div>
</section>
<%@ include file="/jsp/partials/footer.jsp" %>