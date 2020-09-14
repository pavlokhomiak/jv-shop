<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script crossorigin="anonymous"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script crossorigin="anonymous"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Current user orders</h1>

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
                <a href="${pageContext.request.contextPath}/order/detail?id=${order.id}">Detail</a>
            </td>
        </tr>
    </c:forEach>
</table>

<br/><a href="${pageContext.request.contextPath}/">To main</a>
</body>
</html>
