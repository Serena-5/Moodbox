<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="partials/header.jsp" %>

<section class="checkout-wrapper">
  <!-- ===== Heading ===== -->
  <div class="checkout-head">
    <h2>Riepilogo Ordine</h2>
  </div>

  <!-- ===== Cart Summary ===== -->
  <div class="cart-summary">
    <table class="cart-table">
      <thead>
        <tr>
          <th>Prodotto</th>
          <th class="img-col">Img</th>
          <th>Qtà</th>
          <th class="right">Sub‑totale</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="riga" items="${articoli}">
          <tr>
            <td class="title">${riga.box.nome}</td>
            <td class="img-col"><img src="${pageContext.request.contextPath}/images_uploaded/${riga.box.immagine}" alt="${riga.box.nome}"></td>
            <td>${riga.quantita}</td>
            <td class="right">
              <fmt:formatNumber value="${riga.box.prezzo * riga.quantita}" type="currency" currencySymbol="€"/>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    <tfoot>
  <tr>
    <th colspan="3" class="right">Totale prodotti</th>
    <th class="right">
      <span id="totale-prodotti" data-val="${totaleProdotti}">
        <fmt:formatNumber value="${totaleProdotti}" type="currency" currencySymbol="€"/>
      </span>
    </th>
  </tr>
  <tr>
    <th colspan="3" class="right">Spedizione</th>
    <th class="right">€ <span id="costo-spedizione">0.00</span></th>
  </tr>
  <tr>
    <th colspan="3" class="right grand-total">Totale ordine</th>
    <th class="right grand-total">€ <span id="totale-ordine"></span></th>
  </tr>
</tfoot>
    
    </table>
  </div>

  <!-- ===== Checkout Form ===== -->
  <form class="checkout-form" action="${pageContext.request.contextPath}/checkout" method="post">
  
    <fieldset class="ship-fieldset">
      <legend>Indirizzo di spedizione</legend>

      <div class="two-cols">
        <div class="form-group">
          <label for="via">Via *</label>
          <input type="text" id="via" name="via" required>
        </div>
        <div class="form-group small">
          <label for="civico">N. civico *</label>
          <input type="text" id="civico" name="civico" required>
        </div>
      </div>

      <div class="two-cols">
        <div class="form-group small">
          <label for="cap">CAP *</label>
          <input type="text" id="cap" name="cap" pattern="[0-9]{5}" required title="5 cifre">
        </div>
        <div class="form-group">
          <label for="citta">Città *</label>
          <input type="text" id="citta" name="citta" required>
        </div>
      </div>

      <div class="two-cols">
        <div class="form-group small">
          <label for="provincia">Prov. *</label>
          <select id="provincia" name="provincia" required>
            <option value="MI">MI</option>
            <option value="RM">RM</option>
            <option value="NA">NA</option>
            <option value="TO">TO</option>
            <option value="FI">FI</option>
          </select>
        </div>
        <div class="form-group">
          <label for="paese">Paese *</label>
          <select id="paese" name="paese" required>
            <option value="Italia">Italia</option>
          </select>
        </div>
      </div>
    </fieldset>

    <div class="two-cols gap">
      <div class="form-group">
        <label for="metodoPagamento">Metodo di pagamento *</label>
        <select id="metodoPagamento" name="metodoPagamento" required>
          <option value="PayPal">PayPal</option>
        </select>
      </div>

      <div class="form-group">
        <label for="metodoSpedizione">Metodo di spedizione *</label>
       <select id="spedizione" name="metodoSpedizione" required>
  <option value="Standard" data-costo="5.00">Standard (3‑5 gg) – € 5,00</option>
  <option value="Express" data-costo="9.90">Express (24‑48 h) – € 9,90</option>
</select>

        
      </div>
    </div>

    <div class="form-group">
      <label for="noteOrdine">Note per l’ordine (facoltative)</label>
      <textarea id="noteOrdine" name="note" rows="3"></textarea>
    </div>

    <button type="submit" class="btn-primary">Conferma ordine</button>
  </form>
</section>

<%@ include file="/jsp/partials/footer.jsp" %>

