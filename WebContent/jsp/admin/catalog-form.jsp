<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<%
    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${item != null}">Modifica articolo</c:when>
            <c:otherwise>Nuovo articolo</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="<%=ctx%>/css/style.css">
</head>
<body>

<div class="container">
    <h2 class="checkout-head">
        <c:choose>
            <c:when test="${item != null}">Modifica articolo</c:when>
            <c:otherwise>Nuovo articolo</c:otherwise>
        </c:choose>
    </h2>

    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <form method="post" action="<%=ctx%>/admin/catalogo" class="catalog-form" enctype="multipart/form-data">
        <c:if test="${item != null}">
            <input type="hidden" name="id" value="${item.id}">
            <input type="hidden" name="immagineOld" value="${item.immagine}">
        </c:if>

        <label>Nome</label>
        <input type="text" name="nome" value="${item != null ? item.nome : ''}" required>

        <label>Descrizione</label>
        <textarea name="descrizione" rows="3" required>${item != null ? item.descrizione : ''}</textarea>

        <label>Prezzo (€)</label>
        <input type="number" step="0.01" name="prezzo" value="${item != null ? item.prezzo : ''}" required>

        <label>Immagine (JPG/PNG)</label>
        <input type="file" name="fileImg" accept="image/*">

        <label>
            <input type="checkbox" name="disponibile"
                   <c:if test="${item != null && item.disponibile}">checked</c:if>>
            Disponibile per i clienti
        </label>

        <div class="btn-row">
            <button type="submit" class="btn btn-save">Salva</button>
            <a href="<%=ctx%>/admin/catalogo" class="btn btn-small">Annulla</a>
        </div>
    </form>
</div>

</body>
</html>
