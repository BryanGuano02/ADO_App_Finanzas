<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<html>
<head>
    <title>Ver Registrar Egreso</title>
</head>
<body>
<h1>Registro de Movimiento de Egreso</h1>
<h3>${cuenta.nombre}</h3>
<p>Saldo actual de la cuenta: <strong>${cuenta.total}</strong></p>

<a href="ContabilidadController?ruta=cancelar">Cancelar</a>

<!-- Formulario para ingresar el movimiento de egreso -->
<form action="ContabilidadController?ruta=ingresarInfoEgreso" method="post">
    <label for="concepto">Concepto:</label><br>
    <input type="text" id="concepto" name="concepto" required><br><br>

    <label for="fecha">Fecha del movimiento:</label><br>
    <input type="date" id="fecha" name="fecha" required><br><br>

    <label for="valor">Valor del Egreso:</label><br>
    <input type="number" id="valor" name="valor" required min="-1000000" step="-0.01" oninput="validateNegative(this)"><br><br>

    <label for="idCategoria">Categoría del egreso:</label><br>
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
