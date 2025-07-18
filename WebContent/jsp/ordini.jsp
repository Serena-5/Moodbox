<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<div class="container">
    <div style="margin-bottom: 1rem;">
        <a href="${pageContext.request.contextPath}/box" class="btn-primary" style="width: auto; display: inline-block; padding: 10px 20px;">← Torna al catalogo</a>
    </div>

    <h2 class="catalog-title">I miei ordini</h2>


    <c:if test="${empty ordini}">
        <p>Non hai ancora effettuato ordini.</p>
    </c:if>

    <c:forEach var="ordine" items="${ordini}">
        <div class="confirm-box" style="margin-bottom: 2rem;">
            <h3 style="color: #0777D9;">Ordine #${ordine.id}</h3>

            <table class="order-info">
                <tr><th>Data</th><td>${ordine.dataOrdine}</td></tr>
                <tr><th>Totale</th><td>€ ${ordine.totale}</td></tr>
                <tr><th>Stato</th><td>${ordine.statoOrdine}</td></tr>
                <tr><th>Spedizione</th><td>${ordine.metodoSpedizione} - € ${ordine.costoSpedizione}</td></tr>
            </table>

            <h4 style="margin: 1.5rem 0 1rem;">Prodotti acquistati:</h4>

            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Immagine</th>
                        <th>Prodotto</th>
                        <th>Quantità</th>
                        <th>Prezzo</th>
                        <th>Totale Riga</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="righe" value="${righeOrdiniMap[ordine.id]}" />
                    <c:forEach var="riga" items="${righe}">
                        <tr>
                            <td class="img-col"><img src="${pageContext.request.contextPath}/images_uploaded/${riga.imgUrl}" alt="${riga.boxNome}"></td>
                            <td>${riga.boxNome}</td>
                            <td>${riga.quantita}</td>
                            <td>€ ${riga.prezzoUnitario}</td>
                            <td class="grand-total">€ ${riga.totaleRiga}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:forEach>
</div>

<%@ include file="partials/footer.jsp" %>
