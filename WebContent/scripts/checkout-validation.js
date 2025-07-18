document.addEventListener('DOMContentLoaded', () => {
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
});
