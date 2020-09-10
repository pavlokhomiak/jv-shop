<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<h1>All orders</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>User ID</th>
    </tr>
    <c:forEach var = "order" items = "${orders}">
        <tr>
            <td>
                <c:out value = "${order.id}"/>
            </td>
            <td>
                <c:out value = "${order.userId}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/remove?id=${order.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<br/><a href="${pageContext.request.contextPath}/">To main</a>
</body>
</html>
