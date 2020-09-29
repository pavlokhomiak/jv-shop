<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        tr {
            background: #ccc;
            text-align: center;
        }
        .table {
            text-align: center;
        }
    </style>
    <title>Shopping cart</title>
</head>
<body>
<%@include file="menu.jsp" %>
<div class="container" align="center">
    <div class="row justify-content-center align-items-center">
        <div style="text-align: center">
            <h2 style="color: white"> SHOPPING CART </h2>
            <br/>
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>
                            <c:out value="${product.id}"/>
                        </td>
                        <td>
                            <c:out value="${product.name}"/>
                        </td>
                        <td>
                            <c:out value="${product.price}"/>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/shopping-cart/products/remove?id=${product.id}"
                               class="btn btn-dark">Remove</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/><a href="${pageContext.request.contextPath}/order/complete" class="btn btn-dark">Complete order</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-dark">Home</a>
            <br/>
        </div>
    </div>
</div>

</body>
</html>
