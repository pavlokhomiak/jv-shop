<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<h1>Products in shopping cart</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var = "product" items = "${products}">
        <tr>
            <td>
                <c:out value = "${product.id}"/>
            </td>
            <td>
                <c:out value = "${product.name}"/>
            </td>
            <td>
                <c:out value = "${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-cart/products/remove?id=${product.id}">Remove</a>
            </td>
        </tr>
    </c:forEach>
</table>

<br/><a href="${pageContext.request.contextPath}/order/complete">Complete order</a>

<br/><a href="${pageContext.request.contextPath}/">To main</a>
</body>
</html>
