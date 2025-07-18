document.addEventListener('DOMContentLoaded', () => {
  const userToggle = document.getElementById('user-toggle');
  const userMenu = document.getElementById('user-menu');
  const dropdownContainer = userToggle?.closest('.user-dropdown');

  if (userToggle && userMenu && dropdownContainer) {
    userToggle.addEventListener('click', (e) => {
      e.stopPropagation();
      dropdownContainer.classList.toggle('open');
    });

    document.addEventListener('click', (e) => {
      if (!dropdownContainer.contains(e.target)) {
        dropdownContainer.classList.remove('open');
      }
    });
  }
});
