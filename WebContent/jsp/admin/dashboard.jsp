<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ include file="/jsp/partials/header.jsp" %>

<h1 class="text-center">Dashboard ordini</h1>

<form method="get" class="filter-form mb-4 d-flex gap-2">
    <label>
        Dal:
        <input type="date" name="from"
               value="${param.from}" class="form-control" />
    </label>
    <label>
        Al:
        <input type="date" name="to"
               value="${param.to}" class="form-control" />
    </label>
    <label>
        Email cliente:
        <input type="text" name="cliente"
               value="${fn:escapeXml(param.cliente)}"
               class="form-control" placeholder="utente@example.com" />
    </label>
    <p>
    <button class="btn btn-primary" type="submit">Filtra</button>
    </p>
</form>

<table class="table table-striped table-hover responsive-table">
    <thead>
        <tr>
            <th>#Ordine</th>
            <th>Data</th>
            <th>Cliente</th>
            <th>Totale €</th>
            <th>Dettagli</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${ordini}" var="o">
            <tr>
                <td>${o.id}</td>
                <td>
                    <c:out value="${o.dataOrdine.toLocalDate()} ${o.dataOrdine.toLocalTime().toString().substring(0,5)}"/>
                </td>
                <td>${o.emailCliente}</td>
                <td>${o.totale}</td>
                <td>
                    <a href="ordine?id=${o.id}"
                       class="btn btn-sm btn-outline-secondary">📝</a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty ordini}">
            <tr><td colspan="5" class="text-center">Nessun ordine trovato</td></tr>
        </c:if>
    </tbody>
</table>
