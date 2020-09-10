<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Provide user details</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Name: <input type="text" name="name" required placeholder="User name">
    <br/>Login: <input type="text" name="log" required placeholder="User login">
    <br/>Password: <input type="password" name="pas" required placeholder="User password">
    <br/>Repeat Password: <input type="password" name="pas-rep" required placeholder="Repeat user password">

    <br/><button type="submit">Register</button>
    <br/><a href="${pageContext.request.contextPath}/">To main</a>
</form>

</body>
</html>
