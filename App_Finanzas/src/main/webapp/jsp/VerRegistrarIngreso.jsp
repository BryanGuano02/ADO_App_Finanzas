<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %><html>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_verregistraringreso.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <title>Registro de Movimiento de Ingreso</title>
</head>

<body>
<nav>
  <div class="titulo"><i class="fas fa-piggy-bank"></i> Chaucherita</div>
  <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>
</nav>

<div class="container">
  <div class="form-header">
    <h1>Registro de Movimiento de Ingreso</h1>
    <a href="ContabilidadController?ruta=cancelar">Cancelar</a>
  </div>

  <h3>${cuenta.nombre}</h3>

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
      <input type="number" id="valor" name="valor" required min="0.01" step="0.01">
    </div>
    <div>
      <label for="idCategoria">Categoría:</label>
      <select id="idCategoria" name="idCategoria" required>
        <option value="" disabled selected>Selecciona una categoría</option>
        <c:forEach var="categoria" items="${categoriasIngreso}">
          <option value="${categoria.ID}">${categoria.nombre}</option>
        </c:forEach>
      </select>
    </div>
    <div>
      <button type="submit">Registrar</button>
    </div>
  </form>
</div>
</body>

</html>

<%--
<head>
  <title>Title</title>
</head>
<body>
<h1>Registro de Movimiento de Ingreso</h1>
<h3>${cuenta.nombre}</h3>

<a href="ContabilidadController?ruta=cancelar">Cancelar</a>

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
--%>

