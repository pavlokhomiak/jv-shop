<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>

<h4 style="color: red">${message}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    <br/>Login:<input type="text" name="log">
    <br/>Password:<input type="password" name="pas">

    <br/><button type="submit">Login</button>
    <br/><a href="${pageContext.request.contextPath}/">To main</a>
</form>

</body>
</html>
