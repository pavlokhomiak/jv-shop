<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        tr {
            background: #ccc;
            text-align: center;
        }
        .table {
            text-align: center;
        }
    </style>
    <title>All users</title>
</head>
<body>
<%@include file="../menu.jsp" %>
<div class="container" align="center">
    <div class="row justify-content-center align-items-center">
        <div style="text-align: center">
            <h2 style="color: white"> ALL USERS </h2>
            <br/>
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>
                            <c:out value="${user.id}"/>
                        </td>
                        <td>
                            <c:out value="${user.name}"/>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/users/delete?id=${user.id}"
                               class="btn btn-dark">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/><a href="${pageContext.request.contextPath}/" class="btn btn-dark">Home</a>
            <br/>
        </div>
    </div>
</div>

</body>
</html>
