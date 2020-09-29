<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Provide product details</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/products/add">
    Name: <input type="text" name="name" required placeholder="Product name">
    <br/>Price: <input type="number" name="price" required placeholder="Product price">

    <br/><button type="submit" >Add product</button>
    <br/><a href="${pageContext.request.contextPath}/">To main</a>
</form>
</body>
</html>