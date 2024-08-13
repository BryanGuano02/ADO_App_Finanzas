<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/verregistrartransferencia.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <title>Registro de Transferencia</title>
</head>

<body>
<nav>
    <div class="titulo"><i class="fas fa-piggy-bank"></i> Chaucherita</div>
    <a href="ContabilidadController?ruta=cancelar">Cancelar</a>
</nav>

<div class="container">
    <div class="header">
        <div>
            <h1>Registro de Transferencia</h1>
            <p>${cuentaOrigen.nombre}</p>
            <p>Saldo actual de la cuenta: <strong><fmt:formatNumber value="${cuentaOrigen.total}" type="number" maxFractionDigits="2"/></strong></p>
        </div>
        <a href="ContabilidadController?ruta=cancelar">Cancelar</a>
    </div>

    <!-- Formulario para ingresar la información de la transferencia -->
    <form action="ContabilidadController?ruta=ingresarInfoTransferencia" method="post">
        <label for="concepto">Concepto:</label>
        <input type="text" id="concepto" name="concepto" required>

        <label for="valor">Valor a transferir:</label>
        <input type="number" id="valor" name="valor" required min="0.01" step="0.01">

        <label for="fecha">Fecha de la transferencia:</label>
        <input type="date" id="fecha" name="fecha" required>

        <label for="idCuentaDestino">Cuenta destino:</label>
        <select id="idCuentaDestino" name="idCuentaDestino" required>
            <option value="" disabled selected>Selecciona una cuenta de destino:</option>
            <!-- Mostrar las opciones dinámicamente -->
            <c:forEach var="cuentaDestino" items="${cuentasDestino}">
                <option value="${cuentaDestino.id}">${cuentaDestino.nombre}</option>
            </c:forEach>
        </select>

        <label>Categoría del egreso:</label>
        <!-- Mostrar las opciones dinámicamente -->
        <c:forEach var="categoria" items="${categoriasTransferencia}">
            <label>${categoria.nombre}</label><br>
        </c:forEach>

        <button type="submit">Registrar Transferencia</button>
    </form>
</div>
</body>

</html>



<%--<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Registro de Transferencia</h1>
<h3>${cuentaOrigen.nombre}</h3>
<p>Saldo actual de la cuenta: <strong>${cuentaOrigen.total}</strong></p>

<a href="ContabilidadController?ruta=cancelar">Cancelar</a>

<!-- Formulario para ingresar la información de la transferencia -->
<form action="ContabilidadController?ruta=ingresarInfoTransferencia" method="post">
    <label for="concepto">Concepto:</label><br>
    <input type="text" id="concepto" name="concepto" required><br><br>

    <label for="valor">Valor a transferir:</label><br>
    <input type="number" id="valor" name="valor" step="0.01" required><br><br>

    <label for="fecha">Fecha de la transferencia:</label><br>
    <input type="date" id="fecha" name="fecha" required><br><br>

    <label for="idCuentaDestino">Cuenta destino:</label><br>
    <select id="idCuentaDestino" name="idCuentaDestino" required>
        <option value="" disabled selected>Selecciona una cuenta de destino:</option>
        <!-- Mostrar las opciones dinámicamente -->
        <c:forEach var="cuentaDestino" items="${cuentasDestino}">
            <option value="${cuentaDestino.id}">${cuentaDestino.nombre}</option>
        </c:forEach>
    </select>

    <label >Categoría del egreso:</label><br>
    <!-- Mostrar las opciones dinámicamente -->
    <c:forEach var="categoria" items="${categoriasTransferencia}">
        <label value="${categoria.ID}">${categoria.nombre}</label><br>
    </c:forEach>
    <button type="submit">Registrar Transferencia</button>
</form>
</body>
</html>--%>
