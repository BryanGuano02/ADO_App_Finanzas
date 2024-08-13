<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/styles_error.css">
    <title>Error</title>
</head>
<body>

<div class="container">
    <h1>Se ha producido un error</h1>
    <p class="error-message">${error}</p>
    <p>Por favor, regrese y corrija la información.</p>
    <a href="ContabilidadController?ruta=verDashboard">Volver al dashboard</a>
    <img src="${pageContext.request.contextPath}/jsp/imagen/papagallo.png" alt="" class="parrot">

</div>
</body>
</html>


