<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/jsp/partials/header.jsp" %>

<h1 class="mb-3">Dettaglio ordine #${ordine.id}</h1>

<!-- =========== DATI ORDINE ================================================= -->
<div class="card mb-4">
  <div class="card-body">
    <p class="mb-1">
      <strong>Data ordine:</strong>
      <fmt:formatDate value="${dataOrdineDate}" pattern="dd/MM/yyyy HH:mm"/>
    </p>
    <p class="mb-1"><strong>Cliente (e‑mail):</strong> ${ordine.emailCliente}</p>
    <p class="mb-1">
      <strong>Indirizzo:</strong>
      ${ordine.via}, ${ordine.civico}, ${ordine.cap} ${ordine.citta}
      (${ordine.provincia}) – ${ordine.paese}
    </p>
    <p class="mb-1"><strong>Metodo spedizione:</strong> ${ordine.metodoSpedizione}</p>
    <p class="mb-1"><strong>Metodo pagamento:</strong> ${ordine.metodoPagamento}</p>
  </div>
</div>

<!-- =========== FORM CAMBIO STATO ========================================== -->
<form method="post" class="d-flex align-items-center gap-2 mb-4">
  <input type="hidden" name="id" value="${ordine.id}" />
  <label class="mb-0"><strong>Stato ordine:</strong></label>
  <select name="stato" class="form-select d-inline-block w-auto mx-2">
        <option value="in attesa" ${ordine.statoOrdine == 'in attesa' ? 'selected' : ''}>In attesa</option>
        <option value="in lavorazione" ${ordine.statoOrdine == 'in lavorazione' ? 'selected' : ''}>In lavorazione</option>
        <option value="spedito" ${ordine.statoOrdine == 'spedito' ? 'selected' : ''}>Spedito</option>
        <option value="completato" ${ordine.statoOrdine == 'completato' ? 'selected' : ''}>Completato</option>
        <option value="annullato" ${ordine.statoOrdine == 'annullato' ? 'selected' : ''}>Annullato</option>
    </select>
  <button class="btn btn-primary">Aggiorna stato</button>
</form>

<!-- =========== TABELLA ARTICOLI =========================================== -->
<table class="table table-bordered align-middle">
  <thead class="table-light">
    <tr>
      <th style="width:90px;">Immagine</th>
      <th>Prodotto</th>
      <th class="text-center">Prezzo&nbsp;€</th>
      <th class="text-center">Qtà</th>
      <th class="text-end">Subtotale&nbsp;€</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="r" items="${righe}">
      <tr>
        <td>
  <img src="${pageContext.request.contextPath}/images/${r.imgUrl}"
       alt="${r.boxNome}"
       style="height:80px;width:80px;object-fit:cover;border-radius:6px;">
</td>

        <td>${r.boxNome}</td>
        <td class="text-center">
          <fmt:formatNumber value="${r.prezzoUnitario}" type="currency" currencySymbol="€"/>
        </td>
        <td class="text-center">${r.quantita}</td>
        <td class="text-end">
          <fmt:formatNumber value="${r.totaleRiga}" type="currency" currencySymbol="€"/>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<!-- =========== TOTALE ORDINE + BOTTONE BACK =============================== -->
<!-- Contenitore flessibile verticale -->
<div class="d-flex flex-column align-items-end gap-2 mt-4">

  <!-- Totale ordine -->
  <p class="fs-5 mb-0">
    <strong>Totale ordine:&nbsp;</strong>
    <fmt:formatNumber value="${ordine.totale}"
                      type="currency" currencySymbol="€"/>
  </p>

  <!-- Bottone dashboard -->
  <a href="${pageContext.request.contextPath}/admin/dashboard"
     class="btn btn-primary w-auto text-nowrap">
    ← Torna alla dashboard
  </a>

</div>






