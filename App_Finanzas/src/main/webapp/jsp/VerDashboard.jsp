<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ver Da4shboard</title>
</head>
<body>
<h1>Hola</h1>
<h1>Hola 2 </h1>
<h1>Hola 3</h1>

<c:forEach items="${cuentas}" var="cuenta">
    <h1>${cuenta.nombre}</h1>
    <h1>hola</h1>
</c:forEach>


</body>
</html>