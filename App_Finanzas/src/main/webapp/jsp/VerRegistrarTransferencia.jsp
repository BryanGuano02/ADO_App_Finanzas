<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
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
</html>
