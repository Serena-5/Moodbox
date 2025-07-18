document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form');
  const emailInput = form.querySelector('#email');
  const passwordInput = form.querySelector('#password');
  const confirmPasswordInput = form.querySelector('#confirmPassword');

  function showError(input, message) {
    let error = input.nextElementSibling;
    if (!error || !error.classList.contains('error-msg')) {
      error = document.createElement('div');
      error.classList.add('error-msg');
      input.insertAdjacentElement('afterend', error);
    }
    error.textContent = message;
  }

  function clearError(input) {
    const error = input.nextElementSibling;
    if (error && error.classList.contains('error-msg')) {
      error.remove();
    }
  }

  emailInput.addEventListener('input', () => {
    const value = emailInput.value.trim();
    if (!value.match(/^[^@]+@[^@]+\.[a-z]{2,}$/i)) {
      showError(emailInput, 'Inserisci un’email valida.');
    } else {
      clearError(emailInput);
    }
  });

  passwordInput.addEventListener('input', () => {
    const value = passwordInput.value;
    if (value.length < 6) {
      showError(passwordInput, 'La password deve contenere almeno 6 caratteri.');
    } else {
      clearError(passwordInput);
    }

    // Verifica conferma password in tempo reale
    if (confirmPasswordInput.value && confirmPasswordInput.value !== value) {
      showError(confirmPasswordInput, 'Le password non corrispondono.');
    } else {
      clearError(confirmPasswordInput);
    }
  });

  confirmPasswordInput.addEventListener('input', () => {
    const value = confirmPasswordInput.value;
    if (value !== passwordInput.value) {
      showError(confirmPasswordInput, 'Le password non corrispondono.');
    } else {
      clearError(confirmPasswordInput);
    }
  });
});
