<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_veractualizarmovimiento.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <title>Ver Actualizar Movimiento</title>
</head>
<body>
<%--<h1>Actualización de: ${movimiento}</h1>

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
</form>--%>

<nav>
    <div class="titulo"><i class="fas fa-piggy-bank"></i> Chaucherita</div>
    <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>
</nav>

<div class="container">
    <div class="form-container">
        <h1>Actualización de: ${movimiento}</h1>

        <form action="ContabilidadController?ruta=registrarInfoActualizacion" method="post">
            <label for="concepto">Concepto:</label>
            <input type="text" id="concepto" name="concepto" required>

            <label for="fecha">Fecha del movimiento:</label>
            <input type="date" id="fecha" name="fecha" required>

            <label for="valor">Valor del movimiento:</label>
            <input type="number" id="valor" name="valor" required min="0.01" step="0.01">

            <label for="categoria">Categoría:</label>
            <select id="categoria" name="categoria" required>
                <option value="" disabled selected>Selecciona una categoría</option>
                <!-- Mostrar las opciones dinámicamente -->
                <c:forEach var="categoria" items="${categorias}">
                    <option value="${categoria.ID}">${categoria.nombre}</option>
                </c:forEach>
            </select>

            <button type="submit">Actualizar Movimiento</button>
        </form>
    </div>
</div>
</body>
</html>
