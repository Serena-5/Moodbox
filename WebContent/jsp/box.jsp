<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<h2 class="catalog-title">Catalogo MoodBox</h2>


<div class="grid">

    <c:forEach var="b" items="${boxList}">
        <div class="card">
            <img src="${pageContext.request.contextPath}/images_uploaded/${b.immagine}" alt="${b.nome}" style="max-width:100%; height:auto;" />

            <h3><c:out value="${b.nome}" /></h3>
            <p><c:out value="${b.descrizione}" /></p>
            <p class="price">€ <c:out value="${b.prezzo}" /></p>

            <!-- Form Aggiungi -->
           <form class="add-to-cart-form" data-box-id="${b.id}">
    <button type="submit">Aggiungi al carrello</button>
</form>
           

            <!-- Link ai dettagli -->
            <p>
                <a href="${pageContext.request.contextPath}/box/dettaglio/${b.id}">Dettagli</a>
            </p>
        </div>
    </c:forEach>
</div>
<script>
  const ctxPath = '<%= request.getContextPath() %>';
</script>


<%@ include file="/jsp/partials/footer.jsp" %>
