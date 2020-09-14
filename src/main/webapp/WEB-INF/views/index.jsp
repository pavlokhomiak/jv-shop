<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<h1>Welcome to internet-shop!${time}</h1>

<a href="${pageContext.request.contextPath}/registration">Register</a>

<br/><a href="${pageContext.request.contextPath}/users/all">Users</a>

<br/><a href="${pageContext.request.contextPath}/products/add">Add product</a>

<br/><a href="${pageContext.request.contextPath}/products/all">Products</a>

<br/><a href="${pageContext.request.contextPath}/products/all/admin">Products admin</a>

<br/><a href="${pageContext.request.contextPath}/shopping-cart/products/get">Shopping cart</a>

<br/><a href="${pageContext.request.contextPath}/order/all">Orders</a>

<br/><a href="${pageContext.request.contextPath}/order/all/admin">Orders admin</a>

<%--<button type="button" class="btn btn-secondary">Secondary</button>--%>

</body>
</html>
