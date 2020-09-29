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
    <title>Registration</title>
</head>
<body>
<%@include file="menu.jsp"%>
<div class="container" align="center">
    <h2 style="color:white">Provide user details</h2>
    <form method="post" action="${pageContext.request.contextPath}/registration">
        <h4 style="color: red">${message}</h4>
        <div class="form-group">
            <label>Name</label>
            <input class="form-control" type="text" name="name" style="text-align: center; width: 500px" required>
        </div>
        <div class="form-group">
            <label>Login</label>
            <input type="text" name="log" class="form-control" style="text-align: center; width: 500px" required>
        </div>
        <div class="form-group">
            <label>Password</label>
            <input type="password" name="pas" class="form-control" style="text-align: center; width: 500px" required>
        </div>
        <div class="form-group">
            <label>Confirm Password</label>
            <input type="password" name="pas-rep" class="form-control" style="text-align: center; width: 500px" required>
        </div>
        <button style="color:white" type="submit"  class="btn btn-dark">Submit</button>
    </form>
</div>


<form>

</form>

</body>
</html>
