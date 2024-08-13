<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_verdashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <title>Dashboard</title>
</head>

<body>
<nav>
    <div class="titulo"><i class="fas fa-piggy-bank"></i>
        Chaucherita
    </div>
    <div class="barraNavegacion">
        <ul>
            <li><a href="#cuentas">Cuentas</a></li>
            <li><a href="#categorias">Categorías</a></li>
            <li><a href="#movimientos">Movimientos</a></li>

            <li>
                <!-- Filtros por rango de fechas -->
                <form
                        action="ContabilidadController?ruta=filtrarPorFechas"
                        method="post">
                    <div class="input-icon-wrapper">
                        <input type="text" id="fechaRango"
                               name="fechaRango"
                               placeholder="&#xf073;‎ ▼" class="with-icon">
                    </div>

                    <input type="hidden" id="desde" name="desde">
                    <input type="hidden" id="hasta" name="hasta">

                    <button type="submit">Filtrar</button>
                </form>
            </li>
        </ul>
    </div>
</nav>
<div class="cuentas" id="cuentas">
    <c:forEach items="${cuentas}" var="cuenta">
        <div class="cuenta">
            <form action="ContabilidadController?ruta=verCuenta" method="post">
                <input type="hidden" name="idCuenta" value="${cuenta.id}">
                <h1 class="nombre_cuenta" onclick="this.closest('form').submit()">${cuenta.nombre}</h1>
                <h1 class="nombre_cuenta" onclick="this.closest('form').submit()">
                    <fmt:formatNumber value="${cuenta.total}" type="number" maxFractionDigits="2"/>
                </h1>
            </form>
            <!-- Ingreso -->
            <form action="ContabilidadController?ruta=registrarIngreso" method="POST" style="display:inline;">
                <input type="hidden" name="idCuenta" value="${cuenta.id}">
                <button type="submit" class="iconos">
                    <i class="fas fa-arrow-down"></i> Ingreso
                </button>
            </form>

            <!-- Egreso -->
            <form action="ContabilidadController?ruta=registrarEgreso" method="POST" style="display:inline;">
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
        </div>
    </c:forEach>
</div>

<%--<div class="categorias" id="categorias">
    <h1>Categorias</h1>

    <h2>Categorias Ingreso</h2>

    <c:forEach items="${categoriasIngreso}" var="categoriaIngreso">
        <form action="ContabilidadController?ruta=verCategoria" method="post">
            <input type="hidden" name="idCategoria" value="${categoriaIngreso.ID}">
            <h1 class="categoriaIngreso" onclick="this.closest('form').submit()">
                    ${categoriaIngreso.nombre}
                <span> - Valor: ${valorCategoriasIngreso[categoriaIngreso.ID]}</span>
            </h1>
        </form>
    </c:forEach>

    <h2>Categorias Egreso</h2>

    <c:forEach items="${categoriasEgreso}" var="categoriaEgreso">
        <form action="ContabilidadController?ruta=verCategoria" method="post">
            <input type="hidden" name="idCategoria" value="${categoriaEgreso.ID}">
            <h1 class="categoriaEgreso" onclick="this.closest('form').submit()">
                    ${categoriaEgreso.nombre}
                <span> - Valor: ${valorCategoriasEgreso[categoriaEgreso.ID]}</span>
            </h1>
        </form>
    </c:forEach>
</div>--%>
<div class="categorias" id="categorias">
    <h1>Categorías</h1>

    <section >
        <h2>Categorías Ingreso</h2>

            <div class="categoria-contenedor" >
                <c:forEach items="${categoriasIngreso}" var="categoriaIngreso">
                <form action="ContabilidadController?ruta=verCategoria" method="post">
                    <input type="hidden" name="idCategoria" value="${categoriaIngreso.ID}">
                    <button type="submit" class="categoria-item">
                        <h2>${categoriaIngreso.nombre}</h2>
                        <div class="valoringreso">
                            <fmt:formatNumber value="${valorCategoriasIngreso[categoriaIngreso.ID]}" type="number" maxFractionDigits="2"/>
                        </div>
                    </button>
                </form>
                </c:forEach>
            </div>



    </section>


    <section >
        <h2>Categorías Egreso</h2>

            <div class="categoria-contenedor">
                <c:forEach items="${categoriasEgreso}" var="categoriaEgreso">
                <form action="ContabilidadController?ruta=verCategoria" method="post">
                    <input type="hidden" name="idCategoria" value="${categoriaEgreso.ID}">
                    <button type="submit" class="categoria-item">
                        <h2>${categoriaEgreso.nombre}</h2>
                        <div class="valoregreso">
                            <fmt:formatNumber value="${valorCategoriasEgreso[categoriaEgreso.ID]}" type="number" maxFractionDigits="2"/>
                        </div>
                    </button>
                </form>
                </c:forEach>
            </div>


    </section>
</div>

<%--<div class="container">
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
            <th>Acciones</th>
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
                <td class="btn-group">
                    <form name="actualizar" action="ContabilidadController?ruta=actualizarMovimiento" method="POST">
                        <input type="hidden" name="id" value="${movimiento.id}">
                        <button type="submit" class="btn btn-update">Actualizar</button>
                    </form>
                    <form action="ContabilidadController?ruta=eliminarMovimiento" method="POST" style="display:inline;">
                        <input type="hidden" name="id" value="${movimiento.id}">
                        <button type="submit" class="btn btn-danger">Eliminar</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>--%>
<div class="movimientos" id="movimientos">

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
                <th>Acciones</th>
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
                    <td class="btn-group">
                        <form name="actualizar" action="ContabilidadController?ruta=actualizarMovimiento" method="POST">
                            <input type="hidden" name="id" value="${movimiento.id}">
                            <button type="submit" class="btn btn-update">Actualizar</button>
                        </form>
                        <form action="" method="POST" style="display:inline;" onsubmit="return confirmarEliminacion(this);">
                            <input type="hidden" name="id" value="${movimiento.id}">
                            <button type="submit" class="btn btn-danger">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function confirmarEliminacion(form) {
        var confirmacion = confirm("¿Estás seguro de que deseas eliminar este movimiento?");
        if (confirmacion) {
            // Si el usuario confirma, cambia la acción a confirmarMovimiento
            form.action = "ContabilidadController?ruta=eliminarMovimiento";
            return true;
        } else {
            // Si el usuario cancela, cambia la acción a cancelarMovimiento
            form.action = "ContabilidadController?ruta=cancelar";
            form.submit();
            return false;

        }
    }
</script>

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
