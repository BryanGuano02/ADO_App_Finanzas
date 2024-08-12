<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ver Movimientos</title>
    <style>
        /* Estilos existentes aquí */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 30%;
            text-align: center;
        }

        .modal-header {
            font-size: 18px;
            margin-bottom: 20px;
        }

        .modal-footer {
            display: flex;
            justify-content: space-around;
        }

        .btn-cancel {
            background-color: grey;
        }

        .btn-cancel:hover {
            background-color: darkgrey;
        }
    </style>
    <script>
        function cancelarEliminacion() {
            // Ocultar el pop-up sin eliminar
            document.getElementById('deleteModal').style.display = 'none';
        }

        function submitEliminarForm() {
            // Enviar el formulario para confirmar la eliminación
            document.getElementById('deleteForm').submit();
        }
    </script>
</head>
<body>

<div class="container">
    <h1>Movimientos</h1>
    <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>

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
</div>

<!-- Modal de confirmación -->
<div id="deleteModal" class="modal" style="display: ${idMovimiento != null ? 'block' : 'none'};">
    <div class="modal-content">
        <div class="modal-header">
            ¿Estás seguro de que deseas eliminar este movimiento?
        </div>
        <div class="modal-footer">
            <form id="cancelForm" action="ContabilidadController?ruta=c" method="POST">
                <input type="hidden" name="ruta" value="cancelar">
                <button type="submit" class="btn btn-cancel">Cancelar</button>
            </form>
            <form id="deleteForm" action="ContabilidadController?ruta=confirmarEliminacion" method="POST">
                <input type="hidden" name="id" value="${idMovimiento}">
                <input type="hidden" name="puedeEliminar" value="true">
                <button type="button" class="btn btn-danger" onclick="submitEliminarForm()">Confirmar</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
