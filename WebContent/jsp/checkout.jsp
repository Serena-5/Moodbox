<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<%@ include file="partials/header.jsp" %>

<div class="container" style="max-width:600px;margin:auto;">
  <h2>Checkout</h2>

  <!-- Riepilogo prodotti ------------------------------------------------ -->
  <table style="width:100%;border-collapse:collapse;margin-bottom:20px;">
    <thead>
      <tr>
        <th style="text-align:left;">Prodotto</th>
        <th style="text-align:center;">Img</th>     
        <th style="text-align:center;">Qtà</th>
        <th style="text-align:right;">Sub‑totale</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="riga" items="${articoli}">
        <tr>
          <td>${riga.box.nome}</td>

          <!-- miniatura 80 px -->
          <td style="text-align:center;">
            <img src="${pageContext.request.contextPath}/images/${riga.box.immagine}"
              alt="${riga.box.nome}" style="width:80px;height:auto;">
          </td>

          <td style="text-align:center;">${riga.quantita}</td>
          <td style="text-align:right;">
            <fmt:formatNumber value="${riga.box.prezzo * riga.quantita}"
                              type="currency" currencySymbol="€"/>
          </td>
        </tr>
      </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <th colspan="3" style="text-align:right;">Totale prodotti</th>
        <th style="text-align:right;">
          <fmt:formatNumber value="${totaleProdotti}" type="currency" currencySymbol="€"/>
        </th>
      </tr>
      <tr>
        <th colspan="3" style="text-align:right;">Spedizione</th>
        <th style="text-align:right;">€ 5,00</th>
      </tr>
      <tr>
        <th colspan="3" style="text-align:right;">Totale ordine</th>
        <th style="text-align:right;">
          <fmt:formatNumber value="${totaleProdotti + 5}" type="currency" currencySymbol="€"/>
        </th>
      </tr>
    </tfoot>
  </table>

  <!-- Form di conferma ordine ------------------------------------------- -->
  <form action="${pageContext.request.contextPath}/ordine" method="post">
    <fieldset style="border:1px solid #ccc;padding:16px;margin-bottom:24px;">
      <legend><strong>Indirizzo di spedizione</strong></legend>

      <label for="via">Via *</label><br>
      <input type="text" id="via" name="via" required style="width:100%;"><br><br>

      <label for="civico">Numero civico *</label><br>
      <input type="text" id="civico" name="civico" required style="width:120px;"><br><br>


     

      <label for="cap">CAP *</label><br>
       <input type="text" id="cap" name="cap"
       pattern="[0-9]{5}" required title="5 cifre"
       style="width:120px;">
      <br><br>

      <label for="citta">Città *</label><br>
      <input type="text" id="citta" name="citta" required style="width:100%;"><br><br>

      <label for="provincia">Provincia *</label><br>
      <select id="provincia" name="provincia" required style="width:150px;">
        <option value="MI">MI</option>
        <option value="RM">RM</option>
        <option value="NA">NA</option>
        <option value="TO">TO</option>
        <option value="FI">FI</option>
      </select><br><br>

      <label for="paese">Paese *</label><br>
      <select id="paese" name="paese" required style="width:200px;">
        <option value="Italia">Italia</option>
      </select>
    </fieldset>

    <label for="metodoSpedizione">Metodo di spedizione *</label><br>
    <select id="metodoSpedizione" name="metodoSpedizione" required>
      <option value="Standard">Standard (3‑5 gg) – € 5,00</option>
      <option value="Express">Express (24‑48 h) – € 9,90</option>
    </select><br><br>

    <label for="noteOrdine">Note per l’ordine (facoltative)</label><br>
    <textarea id="noteOrdine" name="noteOrdine" rows="3" style="width:100%;"></textarea><br><br>

    <button type="submit">Conferma ordine</button>
  </form>
</div>
