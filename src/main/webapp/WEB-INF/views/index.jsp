<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<h1>Welcome to internet-shop!${time}</h1>

<a href="${pageContext.request.contextPath}/registration">Register</a>

<br/><a href="${pageContext.request.contextPath}/users/all">Show all users</a>

<br/><a href="${pageContext.request.contextPath}/addProduct">Add product</a>

<br/><a href="${pageContext.request.contextPath}/allProducts">Show all products</a>

<br/><a href="${pageContext.request.contextPath}/getShoppingCart">Show shopping cart</a>

<br/><a href="${pageContext.request.contextPath}/injectData">Inject test data into DB</a>

</body>
</html>
