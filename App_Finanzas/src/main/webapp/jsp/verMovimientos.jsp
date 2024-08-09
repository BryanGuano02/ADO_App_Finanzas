<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ver Movimientos</title>
</head>
<body>

<h1>Movimientos</h1>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Fecha</th>
        <th>Concepto</th>
        <th>Valor</th>
        <th>Cuenta Origen</th>
        <th>Cuenta Destino</th>
        <th>Tipo de Movimiento</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="movimiento" items="${movimientos}">
        <tr>
            <td>${movimiento.id}</td>
            <td>${movimiento.fecha}</td>
            <td>${movimiento.concepto}</td>
            <td>${movimiento.valor}</td>
            <td>${movimiento.cuentaOrigen}</td>
            <td>${movimiento.cuentaDestino}</td>
            <td>${movimiento.tipoDeMovimiento}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
