document.addEventListener('DOMContentLoaded', () => {
  // ----- Tua logica per spedizione e totale -----
  const spedizioneSelect = document.getElementById('spedizione');
  const costoSpedizioneSpan = document.getElementById('costo-spedizione');
  const totaleProdottiEl = document.getElementById('totale-prodotti');
  const totaleOrdineEl = document.getElementById('totale-ordine');

  const totaleProdotti = parseFloat(totaleProdottiEl.dataset.val || 0);

  function aggiornaTotali() {
    const selected = spedizioneSelect.options[spedizioneSelect.selectedIndex];
    const costoSpedizione = parseFloat(selected.dataset.costo || 0);

    // Aggiorna costo spedizione
    costoSpedizioneSpan.textContent = costoSpedizione.toFixed(2).replace('.', ',');

    // Calcola nuovo totale ordine
    const totaleOrdine = totaleProdotti + costoSpedizione;
    totaleOrdineEl.textContent = totaleOrdine.toFixed(2).replace('.', ',');
  }

  spedizioneSelect.addEventListener('change', aggiornaTotali);
  aggiornaTotali(); // inizializza al primo caricamento

  // ----- INTEGRA VALIDAZIONE CARTA -----
  const checkoutForm = document.querySelector('.checkout-form');
  if (checkoutForm) {
    checkoutForm.addEventListener('submit', function(e) {
      const metodo = document.getElementById('metodoPagamento').value;
      if (metodo === 'Carta') {
        const numero = document.getElementById('cardNumber').value.trim();
        const nome = document.getElementById('cardName').value.trim();
        const scadenza = document.getElementById('cardExpiry').value.trim();
        const cvc = document.getElementById('cardCVC').value.trim();
        if (!numero || !nome || !scadenza || !cvc) {
          alert('Compila tutti i campi della carta!');
          e.preventDefault();
        }
      }
    });
  }
});