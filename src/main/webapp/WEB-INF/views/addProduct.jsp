<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        label {
            cursor: pointer;
            color: white;
            display: block;
            padding: 10px;
            margin: 10px;
            font-weight: bold;
            font-size: large;
        }
    </style>
    <title>Add product</title>
</head>
<body>
<%@include file="menu.jsp"%>

<div class="container" align="center">
    <h2 style="color:white">Provide product details</h2>
    <form method="post" action="${pageContext.request.contextPath}/products/add">
        <h4 style="color: red">${message}</h4>
        <div class="form-group">
            <label>Name</label>
            <input class="form-control" type="text" name="name" style="text-align: center; width: 500px" required>
        </div>
        <div class="form-group">
            <label>Price</label>
            <input type="number" name="price" class="form-control" style="text-align: center; width: 500px" required>
        </div>

        <button style="color:white" type="submit"  class="btn btn-dark">Add product</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-dark">Home</a>
    </form>
</div>
</body>
</html>
