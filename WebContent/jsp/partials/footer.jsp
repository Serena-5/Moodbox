<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Toast -->
<div id="cart-toast" class="toast"></div>

<!-- Scripts -->
<script>
  const ctxPath = '<%= request.getContextPath() %>';
</script>
<script src="${pageContext.request.contextPath}/scripts/cart.js"></script>
<script src="${pageContext.request.contextPath}/scripts/checkout-validation.js"></script>

<footer>
  <p>&copy; 2025 MoodBox - Tutti i diritti riservati</p>
<div class="footer-socials">
  <a href="https://www.instagram.com" target="_blank">
    <img src="${pageContext.request.contextPath}/images/instagram.png" alt="Instagram">
  </a>
  <a href="https://www.facebook.com" target="_blank">
    <img src="${pageContext.request.contextPath}/images/facebook-circle.png" alt="Facebook">
  </a>
  <a href="https://www.tiktok.com" target="_blank">
    <img src="${pageContext.request.contextPath}/images/tiktok.png" alt="TikTok">
  </a>
  <a href="https://www.threads.net" target="_blank">
    <img src="${pageContext.request.contextPath}/images/threads.png" alt="Threads">
  </a>
</div>

</footer>

</body>
</html>
