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
