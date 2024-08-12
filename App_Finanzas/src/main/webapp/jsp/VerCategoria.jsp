<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<html>
<head>
    <title>Ver Categoria</title>
</head>
<body>
<h1>Información de la Categoría</h1>

<h2>Nombre de la Categoría: ${categoria.nombre}</h2>
<%--<p>Saldo al Mes Actual: ${categoria.saldoActual}</p>--%>

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
</html>
