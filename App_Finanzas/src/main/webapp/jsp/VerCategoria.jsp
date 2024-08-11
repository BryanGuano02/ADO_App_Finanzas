<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %><html>
<html>
<head>
    <title>Ver Categoria</title>
</head>
<body>
<h1>Información de la Categoría</h1>

<h2>Nombre de la Categoría: ${categoria.nombre}</h2>
<p>Saldo al Mes Actual: ${categoria.saldoActual}</p>

<h3>Lista de Movimientos</h3>
<table>
  <thead>
  <tr>
    <th>Fecha</th>
    <th>Descripción</th>
    <th>Monto</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="movimiento" items="${movimientos}">
    <tr>
      <td>${movimiento.fecha}</td>
      <td>${movimiento.descripcion}</td>
      <td>${movimiento.monto}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
