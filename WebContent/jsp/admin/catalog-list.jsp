<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione catalogo</title>

    <!-- CSS principale -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        table.catalog { width:100%; border-collapse:collapse; margin-top:1rem; }
        table.catalog th, table.catalog td { padding:.6rem; border:1px solid #bbb; }
        table.catalog th { background:#f5f5f5; }
        .right       { text-align:right; }
        .text-center { text-align:center; }
        .thumb       { height:60px; object-fit:cover; }

        /* bottoni compatti */
        .btn-small {
            font-size:.8rem; padding:.15rem .45rem;
            border:none; border-radius:4px; cursor:pointer;
        }
        .btn-small.edit   { background:#0777d9; color:#fff; }
        .btn-small.delete { background:#d9534f; color:#fff; } /* rosso: disattiva */
        .btn-small.enable { background:#5cb85c; color:#fff; } /* verde: riattiva */
    </style>
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