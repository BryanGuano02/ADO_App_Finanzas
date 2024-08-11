<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Ver Actualizar Movimiento</title>
</head>
<body>
<h1>Actualización de: ${movimiento}</h1>
<h3>${cuenta.nombre}</h3>

<p>Saldo actual de la cuenta: <strong>${cuenta.total}</strong></p>

<form action="ContabilidadController?ruta=registrarInfoActualizacion" method="post">
    <label for="concepto">Concepto:</label>
    <input type="text" id="concepto" name="concepto" required><br><br>

    <label for="fecha">Fecha del movimiento:</label><br>
    <input type="date" id="fecha" name="fecha" required><br><br>

    <label for="valor">Valor del movimiento:</label><br>
    <input type="number" id="valor" name="valor" step="0.01" required><br><br>

   <label for="categoria">Categoría:</label><br>
    <select id="categoria" name="categoria" required>
        <option value="" disabled selected>Selecciona una categoría</option>
        <!-- Mostrar las opciones dinámicamente -->
        <c:forEach var="categoria" items="${categorias}">
            <option value="${categoria.ID}">${categoria.nombre}</option>
        </c:forEach>
    </select>


    <button type="submit">Actualizar Movimiento</button>
</form>
</body>
</html>
