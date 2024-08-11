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
    <style>
        .categoriaIngreso, .categoriaEgreso, .nombre_cuenta {
            cursor: pointer; /* Cambia el cursor a una mano apuntando */
        }
    </style>
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
            <form action="ContabilidadController?ruta=filtrarPorFechas" method="post" >
                <label for="fechaRango"> <i class="fas fa-calendar-alt calendar-icon" id="openCalendar"></i></label>
                <input type="text" id="fechaRango" name="fechaRango" placeholder="Selecciona el rango de fechas">

                <input type="hidden" id="desde" name="desde">
                <input type="hidden" id="hasta" name="hasta">

                <button type="submit">Filtrar</button>
            </form>
        </ul>
    </div>
</nav>

<div class="cuentas" id="cuentas">
    <c:forEach items="${cuentas}" var="cuenta">


        <div class="cuenta">
            <form action="ContabilidadController?ruta=verCuenta" method="post">
                <input type="hidden" name="idCuenta" value="${cuenta.id}">
                <h1 class="nombre_cuenta" onclick="this.closest('form').submit()">${cuenta.nombre}</h1>
                <h1 class="nombre_cuenta" onclick="this.closest('form').submit()">${cuenta.total}</h1>
            </form>
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
            <form action="ContabilidadController?ruta=registrarTransferencia" method="POST" style="display:inline;">
                <input type="hidden" name="idCuenta" value="${cuenta.id}">
                <button type="submit" class="iconos">
                    <i class="fas fa-arrow-up"></i> Transferencia
                </button>
            </form>

            <!-- Movimiento -->
            <div class="iconos">
                <i class="fas fa-sync-alt"></i> Movimiento
            </div>


        </div>

    </c:forEach>
</div>

<div class="categorias" id="categorias">
    <h1>Categorias</h1>
    <h2>Categorias Ingreso</h2>
    <c:forEach items="${categoriasIngreso}" var="categoriaIngreso">
        <form action="ContabilidadController?ruta=verCategoria" method="post">
            <input type="hidden" name="idCategoriaIngreso" value="${categoriaIngreso.ID}">
            <div class="categoriaIngreso" onclick="this.closest('form').submit()">
                <h1 class="categoriaNombre">${categoriaIngreso.nombre}</h1>
                    <%--            <h1 class="nombre_cuenta">${categoriaIngreso.total}</h1>--%>
            </div>
        </form>
    </c:forEach>


    <h2>Categorias Egreso</h2>
    <c:forEach items="${categoriasEgreso}" var="categoriaEgreso">
        <form action="ContabilidadController?ruta=verCategoria" method="post">
            <input type="hidden" name="idCategoriaEgreso" value="${categoriaEgreso.ID}">
            <div class="categoriaEgreso" onclick="this.closest('form').submit()">
                <h1 class="categoriaNombre">${categoriaEgreso.nombre}</h1>
                    <%--            <h1 class="nombre_cuenta">${categoriaEgreso.total}</h1>--%>
            </div>
        </form>
    </c:forEach>
</div>

<div class="movimientos">

    <div class="container">
        <h1>Movimientos</h1>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>Concepto</th>
                <th>Valor</th>
                <th>Cuenta Origen</th>
                <th>Cuenta Destino</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="movimiento" items="${movimientos}">
                <tr>
                    <td>${movimiento.id != null ? movimiento.id : 'N/A'}</td>
                    <td>${movimiento.fecha != null ? movimiento.fecha : 'N/A'}</td>
                    <td>${movimiento.concepto != null ? movimiento.concepto : 'N/A'}</td>
                    <td>${movimiento.valor != null ? movimiento.valor : 'N/A'}</td>
                    <td>${movimiento.cuentaOrigen != null ? movimiento.cuentaOrigen : 'N/A'}</td>
                    <td>${movimiento.cuentaDestino != null ? movimiento.cuentaDestino : 'N/A'}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<!-- Incluir Flatpickr JS -->
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    flatpickr("#fechaRango", {
        mode: "range",
        dateFormat: "Y-m-d",
        onChange: function (selectedDates, dateStr, instance) {
            if (selectedDates.length === 2) {
                document.getElementById("desde").value = instance.formatDate(selectedDates[0], "Y-m-d");
                document.getElementById("hasta").value = instance.formatDate(selectedDates[1], "Y-m-d");
            }
        }
    });
</script>
</body>


</html>
