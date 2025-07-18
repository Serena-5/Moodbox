document.addEventListener('DOMContentLoaded', () => {
  const ctxPath = document.body.dataset.context || '';

  function showToast(message) {
    const toast = document.getElementById('cart-toast');
    if (!toast) return;

    toast.textContent = message;
    toast.classList.add('show');

    clearTimeout(toast._hideTimeout); // se c'è un timeout in corso
    toast._hideTimeout = setTimeout(() => {
      toast.classList.remove('show');
    }, 2000);
  }

  document.querySelectorAll('.add-to-cart-form').forEach(form => {
    form.addEventListener('submit', function (e) {
      e.preventDefault();

      const boxId = this.dataset.boxId;
	  const quantitaInput = this.querySelector('input[name="quantita"]');
	  const quantita = quantitaInput ? parseInt(quantitaInput.value) || 1 : 1;

	  fetch(`${ctxPath}/carrello`, {
	    method: 'POST',
	    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	    body: `action=ajax-add&boxId=${boxId}&quantita=${quantita}`
	  })
	    .then(res => res.json())  // 👈 PARSE DELLA RISPOSTA JSON
	    .then(data => {
	      const counter = document.querySelector('#cart-counter');
	      if (counter && typeof data.cartCount !== 'undefined') {
	        counter.textContent = data.cartCount;
	      }
	      showToast('Aggiunto al carrello!');
	    })
	    .catch(err => {
	      console.error('Errore durante l’aggiunta al carrello:', err);
	      showToast('Errore. Riprova.');
	    });

    });
  });
});
