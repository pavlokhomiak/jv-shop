<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <style>
        tr {
            background: #ccc;
            text-align: center;
        }
        btn-dark {
            background-color: #515763;
            color: white;
            text-align: center;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
<%@include file="menu.jsp"%>

<div class="container">
    <div class="row justify-content-center align-items-center">
        <div style="text-align: center">
            <h3 id="all-orders" style="color: white">User orders:</h3>
            <p></p>
            <table class="table table-bordered table-hover" aria-describedby="all-products">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">User ID</th>
                    <th scope="col">Info</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var = "order" items = "${orders}">
                    <tr>
                        <th scope="row">
                            <c:out value = "${order.id}"/>
                        </th>
                        <th scope="row">
                            <c:out value = "${order.userId}"/>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/order/detail?id=${order.id}" class="btn btn-dark">Detail</a>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/" class="btn btn-dark">Home</a>
        </div>
    </div>
</div>

</body>
</html>
