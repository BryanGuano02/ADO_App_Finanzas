<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_vercategoria.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <title>Ver Categoría</title>
</head>

<body>
<nav>
    <div class="titulo"><i class="fas fa-tags"></i> Ver Categoría</div>
<%--    <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>--%>
</nav>

<div class="container">
    <div class="categoria-header">
        <h1>${categoria.nombre}</h1>
        <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>
    </div>

    <h3>Lista de Movimientos</h3>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Concepto</th>
            <th>Valor</th>
            <th>Cuenta Origen</th>
            <th>Cuenta Destino</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="movimiento" items="${movimientos}">
            <tr>
                <td>${movimiento.id != null ? movimiento.id : 'N/A'}</td>
                <td>${movimiento.fecha != null ? movimiento.fecha : 'N/A'}</td>
                <td>${movimiento.concepto != null ? movimiento.concepto : 'N/A'}</td>
                <td>${movimiento.valor != null ? movimiento.valor : 'N/A'}</td>
                <td>${movimiento.cuentaOrigen != null ? movimiento.cuentaOrigen : 'N/A'}</td>
                <td>${movimiento.cuentaDestino != null ? movimiento.cuentaDestino : 'N/A'}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>
<%--<html>
<head>
    <title>Ver Categoria</title>
</head>
<body>
<h1>Información de la Categoría</h1>

<h2>Nombre de la Categoría: ${categoria.nombre}</h2>
&lt;%&ndash;<p>Saldo al Mes Actual: ${categoria.saldoActual}</p>&ndash;%&gt;

<a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>

<h3>Lista de Movimientos</h3>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Fecha</th>
        <th>Concepto</th>
        <th>Valor</th>
        <th>Cuenta Origen</th>
        <th>Cuenta Destino</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="movimiento" items="${movimientos}">
        <tr>
            <td>${movimiento.id != null ? movimiento.id : 'N/A'}</td>
            <td>${movimiento.fecha != null ? movimiento.fecha : 'N/A'}</td>
            <td>${movimiento.concepto != null ? movimiento.concepto : 'N/A'}</td>
            <td>${movimiento.valor != null ? movimiento.valor : 'N/A'}</td>
            <td>${movimiento.cuentaOrigen != null ? movimiento.cuentaOrigen : 'N/A'}</td>
            <td>${movimiento.cuentaDestino != null ? movimiento.cuentaDestino : 'N/A'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>--%>
