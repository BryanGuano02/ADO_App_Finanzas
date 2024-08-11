<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<html>
<head>
    <title>Ver Registrar Gasto</title>
</head>
<body>
<h1>Registro de Movimiento de Gasto</h1>
<h3>${cuenta.nombre}</h3>

<!-- Muestra el saldo actual de la cuenta -->
<p>Saldo actual de la cuenta: <strong>${cuenta.total}</strong></p>

<!-- Formulario para ingresar el movimiento de gasto -->
<form action="ContabilidadController?ruta=ingresarInfoGasto" method="post">
    <label for="concepto">Concepto:</label><br>
    <input type="text" id="concepto" name="concepto" required><br><br>

    <label for="fecha">Fecha del movimiento:</label><br>
    <input type="date" id="fecha" name="fecha" required><br><br>

    <label for="valor">Valor del gasto:</label><br>
    <input type="number" id="valor" name="valor" required><br><br>

    <label for="idCategoria">Categoría del gasto:</label><br>
    <select id="idCategoria" name="idCategoria" required>
        <option value="" disabled selected>Selecciona una categoría</option>
        <!-- Mostrar las opciones dinámicamente -->
        <c:forEach var="categoria" items="${categoriasEgreso}">
            <option value="${categoria.ID}">${categoria.nombre}</option>
        </c:forEach>
    </select>

    <button type="submit">Registrar</button>
</form>
</body>
</html>
