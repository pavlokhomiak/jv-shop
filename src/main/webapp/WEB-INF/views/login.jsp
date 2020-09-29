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
    <title>Login</title>
</head>
<body>
<%@include file="menu.jsp"%>

<div class="container" align="center">
    <h2 style="color:white">Login user</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <h4 style="color: red">${message}</h4>
        <div class="form-group">
            <label>Login</label>
            <input class="form-control" type="text" name="log" style="text-align: center; width: 500px" required>
        </div>
        <div class="form-group">
            <label>Password</label>
            <input type="password" name="pas" class="form-control" style="text-align: center; width: 500px" required>
        </div>

        <button style="color:white" type="submit"  class="btn btn-dark">Submit</button>
    </form>
</div>

</body>
</html>
