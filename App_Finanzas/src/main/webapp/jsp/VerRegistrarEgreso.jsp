<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_verregistraregreso.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <title>Registrar Egreso</title>
</head>

<body>
<nav>
    <div class="titulo"><i class="fas fa-money-bill-wave"></i> Registrar Egreso</div>
    <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>
</nav>

<div class="container">
    <div class="cuenta-header">
        <h1>${cuenta.nombre}</h1>
        <p>Saldo actual de la cuenta: <strong><fmt:formatNumber value="${cuenta.total}" type="number" maxFractionDigits="2"/></strong></p>
    </div>

    <div class="formulario">
        <form action="ContabilidadController?ruta=ingresarInfoEgreso" method="post">
            <label for="concepto">Concepto:</label>
            <input type="text" id="concepto" name="concepto" required>

            <label for="fecha">Fecha del movimiento:</label>
            <input type="date" id="fecha" name="fecha" required>

            <label for="valor">Valor del Egreso:</label>
            <input type="number" id="valor" name="valor" required min="0.01" step="0.01">

            <label for="idCategoria">Categoría del egreso:</label>
            <select id="idCategoria" name="idCategoria" required>
                <option value="" disabled selected>Selecciona una categoría</option>
                <!-- Mostrar las opciones dinámicamente -->
                <c:forEach var="categoria" items="${categoriasEgreso}">
                    <option value="${categoria.ID}">${categoria.nombre}</option>
                </c:forEach>
            </select>

            <button type="submit">Registrar</button>
        </form>
<%--        <button href="ContabilidadController?ruta=cancelar">Cancelar</button>--%>
        <a href="ContabilidadController?ruta=cancelar" class="btn-cancelar">Cancelar</a>

    </div>
</div>
</body>

</html>
<%--<html>
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
</html>--%>
