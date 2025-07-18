<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<div class="container" style="max-width: 600px; margin:auto;">
    <c:choose>
        <c:when test="${not empty box}">
            <h2><c:out value="${box.nome}" /></h2>

            <img src="${pageContext.request.contextPath}/images_uploaded/${box.immagine}"
                 alt="${box.nome}" style="max-width:90%;height:auto; border-radius:8px;" />

            <p><strong>Descrizione:</strong> <c:out value="${box.descrizione}" /></p>
            <p><strong>Prezzo:</strong> € <c:out value="${box.prezzo}" /></p>

            <form class="add-to-cart-form" data-box-id="${box.id}" style="margin-top:15px;">
  Quantità:
  <input type="number" name="quantita" value="1" min="1" style="width:70px;" />
  <button type="submit">Aggiungi al carrello</button>
</form>
            
            
        </c:when>

        <c:otherwise>
            <p style="color:red;">Prodotto non trovato.</p>
        </c:otherwise>
    </c:choose>
</div>
<script src="${pageContext.request.contextPath}/scripts/cart.js"></script>

<%@ include file="/jsp/partials/footer.jsp" %>

