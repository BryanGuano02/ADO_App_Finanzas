<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/dash.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <title>Página de Inicio</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <title>Página de Inicio</title>
</head>

<body>
<nav>
    <div class="titulo"><i class="fas fa-piggy-bank"></i>
        Chaucherita
    </div>
    <div class="barraNavegacion">
        <ul>
            <li><a href="#Inicio">Cuentas</a></li>
            <li><a href="#">Categorías</a></li>
            <li><a href="#">Movimientos</a></li>
            <!-- Filtros por rango de fechas -->
            <form action="ContabilidadController?ruta=filtrarPorFechas" method="post">
                <label for="fechaRango"> <i class="fas fa-calendar-alt calendar-icon" id="openCalendar"></i></label>
                <input type="text" id="fechaRango" name="fechaRango" placeholder="Selecciona el rango de fechas">

                <input type="hidden" id="fechaInicio" name="fechaInicio">
                <input type="hidden" id="fechaFin" name="fechaFin">

                <button type="submit">Filtrar</button>
            </form>
        </ul>
    </div>
</nav>

<div class="cuentas" id="cuentas">
    <c:forEach items="${cuentas}" var="cuenta">
        <div class="cuenta">
            <h1 class="nombre_cuenta">${cuenta.nombre}</h1>
            <h1 class="nombre_cuenta">${cuenta.total}</h1>

            <!-- Ingreso -->
            <form action="ContabilidadController?ruta=registrarIngreso" method="POST" style="display:inline;">
                <input type="hidden" name="idCuenta" value="${cuenta.id}">
                <button type="submit" class="iconos">
                    <i class="fas fa-arrow-down"></i> Ingreso
                </button>
            </form>

            <!-- Egreso -->
            <form action="ContabilidadController?ruta=registrarGasto" method="POST" style="display:inline;">
                <input type="hidden" name="idCuenta" value="${cuenta.id}">
                <button type="submit" class="iconos">
                    <i class="fas fa-arrow-up"></i> Egreso
                </button>
            </form>


            <!-- Transferencia -->
            <div class="iconos">
                <i class="fas fa-exchange-alt"></i> Transferencia
            </div>

            <!-- Movimiento -->
            <div class="iconos">
                <i class="fas fa-sync-alt"></i> Movimiento
            </div>
        </div>

    </c:forEach>


</div>

<div class="categorias">

</div>

<div class="movimientos">

</div>

<!-- Incluir Flatpickr JS -->
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    flatpickr("#fechaRango", {
        mode: "range",
        dateFormat: "Y-m-d",
        onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length === 2) {
                document.getElementById("fechaInicio").value = instance.formatDate(selectedDates[0], "Y-m-d");
                document.getElementById("fechaFin").value = instance.formatDate(selectedDates[1], "Y-m-d");
            }
        }
    });
</script>
</body>


</html>
