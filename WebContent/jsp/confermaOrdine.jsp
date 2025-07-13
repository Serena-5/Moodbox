<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="partials/header.jsp" %>

<style>
  .confirm-box{max-width:700px;margin:auto;text-align:center;padding:40px 20px}
  .confirm-box h2{color:#2e7d32;margin-bottom:10px}
  .order-info{margin:25px auto;border:1px solid #ccc;border-radius:8px;padding:15px;max-width:500px}
  .order-info table{width:100%;border-collapse:collapse;font-size:0.9rem}
  .order-info th,.order-info td{padding:6px 4px}
  .order-info th{text-align:left;border-bottom:1px solid #ddd}
  .btn-row a{margin:0 12px}
</style>

<div class="confirm-box">
    <h2>Grazie per il tuo ordine!</h2>

    <p>
        Il tuo ordine è stato registrato correttamente
        <c:if test="${not empty param.id}">
            (n° <strong>${param.id}</strong>)
        </c:if>.
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

        <!-- Pulisci il riepilogo dalla sessione per evitare che resti -->
        <%
            session.removeAttribute("carrelloConfermato");
        %>
    </c:if>

    <p>Riceverai una mail con i dettagli appena l’ordine sarà stato spedito.</p>

    <div class="btn-row">
        <a href="${pageContext.request.contextPath}/home"        class="button">Home</a>
       
    </div>
</div>


