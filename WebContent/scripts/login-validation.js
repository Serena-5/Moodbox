// Funzione di validazione del form di login
function validateLogin() {
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value.trim();
  const errorDiv = document.getElementById('error-message');

  errorDiv.textContent = ''; // pulisce eventuali messaggi precedenti

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!email || !emailRegex.test(email)) {
    errorDiv.textContent = 'Inserisci un’email valida.';
    return false;
  }

  if (!password) {
    errorDiv.textContent = 'Inserisci la password.';
    return false;
  }

  return true;
}

function showToast(msg) {
  var t = document.getElementById('toast-msg');
  if (!t) return; // Se il div non c'è, esci
  t.innerText = msg;
  t.classList.add('show');
  setTimeout(function(){
    t.classList.remove('show');
  }, 3000);
}