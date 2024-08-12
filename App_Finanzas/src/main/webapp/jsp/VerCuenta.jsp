<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_cuenta.css.css">
    <title>Ver Cuenta</title>
</head>
<body>
<%--<h1> ${cuenta.nombre}</h1>
<p>${cuenta.total}</p>

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
</table>--%>

<nav>
    <div class="titulo"><i class="fas fa-piggy-bank"></i> Chaucherita</div>
<%--    <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>--%>
</nav>

<div class="container">
    <div class="cuenta-header">
        <h1>${cuenta.nombre}</h1>
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
