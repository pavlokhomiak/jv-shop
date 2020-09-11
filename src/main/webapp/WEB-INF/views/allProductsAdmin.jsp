<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1>All products</h1>

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
                <a href="${pageContext.request.contextPath}/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/><a href="${pageContext.request.contextPath}/products/add">New product</a>
<br/><a href="${pageContext.request.contextPath}/">To main</a>
</body>
</html>
