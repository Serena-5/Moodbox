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
    <style>
        form.catalog-form { max-width: 500px; }
        form.catalog-form label { display: block; margin-top: .8rem; }
        form.catalog-form input, form.catalog-form textarea {
            width: 100%; padding: .5rem; border: 1px solid #bbb; border-radius: 4px;
        }
        .btn-row { margin-top: 1.2rem; }
        .btn-row .btn { display: inline-block; padding: .5rem 1rem; border-radius: 4px; }
        .btn-save   { background:#0777D9; color:#fff; }
        .btn-cancel { background:#aaa;    color:#fff; margin-left:.6rem; }
        .error-msg  { color:#d00; margin-top:.5rem; }
    </style>
</head>
<body>

<h1>
  <c:choose>
    <c:when test="${item != null}">Modifica articolo</c:when>
    <c:otherwise>Nuovo articolo</c:otherwise>
  </c:choose>
</h1>

<c:if test="${not empty error}">
    <p class="error-msg">${error}</p>
</c:if>

<form method="post" action="<%=ctx%>/admin/catalogo" class="catalog-form" enctype="multipart/form-data">
    <c:if test="${item != null}">
        <input type="hidden" name="id" value="${item.id}">
        <input type="hidden" name="immagineOld" value="${item.immagine}">
    </c:if>

    <label>Nome
        <input type="text" name="nome"
               value="${item != null ? item.nome : ''}" required>
    </label>

    <label>Descrizione
        <textarea name="descrizione" rows="3" required>${item != null ? item.descrizione : ''}</textarea>
    </label>

    <label>Prezzo (€)
        <input type="number" step="0.01" name="prezzo"
               value="${item != null ? item.prezzo : ''}" required>
    </label>

    <label>Immagine (carica file JPG/PNG)
        <input type="file" name="fileImg" accept="image/*">
    </label>

    <label>
        <input type="checkbox" name="disponibile"
               ${item != null && item.disponibile ? "checked" : ""}/>
        Disponibile per i clienti
    </label>

    <div class="btn-row">
        <button type="submit" class="btn btn-save">Salva</button>
        <a href="<%=ctx%>/admin/catalogo" class="btn btn-cancel">Annulla</a>
    </div>
</form>

</body>
</html>