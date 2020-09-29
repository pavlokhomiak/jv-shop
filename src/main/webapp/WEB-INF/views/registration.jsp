<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script crossorigin="anonymous"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script crossorigin="anonymous"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
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
