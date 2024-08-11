<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %><html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Registro de Movimiento de Ingreso</h1>
<h3>${cuenta.nombre}</h3>

<a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>

<form action="ContabilidadController?ruta=ingresarInfoIngreso" method="post">
  <div>
    <label for="concepto">Concepto:</label>
    <input type="text" id="concepto" name="concepto" required>
  </div>
  <div>
    <label for="fecha">Fecha:</label>
    <input type="date" id="fecha" name="fecha" required>
  </div>
  <div>
    <label for="valor">Valor:</label>
    <input type="number" id="valor" name="valor" required><br><br>

    <label for="idCategoria">Categoría:</label>
    <select id="idCategoria" name="idCategoria"  required>
      <option value="" disabled selected>Selecciona una categoría</option>
      <!-- Mostrar las opciones dinámicamente -->
      <c:forEach var="categoria" items="${categoriasIngreso}">
        <option value="${categoria.ID}">${categoria.nombre}</option>
      </c:forEach>
    </select>
  </div>
  <div>
    <button type="submit">Registrar</button>
  </div>
</form>
</body>
</html>
