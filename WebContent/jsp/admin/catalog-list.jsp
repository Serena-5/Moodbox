<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione catalogo</title>

    <!-- CSS principale -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    
</head>
<body>

<h1>Catalogo – Gestione</h1>

<p>
    <a href="${pageContext.request.contextPath}/admin/catalogo?action=new"
       class="btn btn-secondary">Nuovo articolo</a>
</p>

<c:if test="${not empty error}">
    <p style="color:#d00;">${error}</p>
</c:if>

<c:choose>
    <c:when test="${empty items}">
        <p><em>Nessun articolo presente.</em></p>
    </c:when>

    <c:otherwise>
        <table class="catalog">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Immagine</th>
                    <th>Prezzo €</th>
                    <th>Disp.</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="it" items="${items}">
                    <tr>
                        <td>${it.id}</td>
                        <td>${it.nome}</td>

                        <!-- Miniatura -->
                       
                        <td>
                            <img src="${pageContext.request.contextPath}/images_uploaded/${it.immagine}"
                                 alt="${it.nome}" class="thumb">
                        </td>

                        <!-- Prezzo -->
                        <td class="right">${it.prezzo}</td>

                        <!-- Toggle disponibilità -->
                        <td class="text-center">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/catalogo"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="toggle">
                                <input type="hidden" name="id"     value="${it.id}">
                                <button type="submit"
                                        class="btn-small ${it.disponibile ? 'delete' : 'enable'}"
                                        title="${it.disponibile ? 'Disattiva' : 'Riattiva'}">
                                    ${it.disponibile ? '✓' : '✗'}
                                </button>
                            </form>
                        </td>

                        <!-- Azione Modifica -->
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/catalogo?action=edit&id=${it.id}"
                               class="btn-small edit">Modifica</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<p>
    <a href="${pageContext.request.contextPath}/admin/dashboard"
       class="btn btn-secondary">← Dashboard</a>
</p>

</body>
</html>