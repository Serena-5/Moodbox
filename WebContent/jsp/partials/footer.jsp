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
</footer>

</body>
</html>
