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
    <div class="header">
        <div align="left">
            <div style="position: absolute; right: 1820px; top: 30px">
                <img src="https://i.ibb.co/HKrc7By/Phone-icon-w.png" alt="Cheetah!" width="42" height="42" style="float:left"/>
            </div>
            <div style="position: absolute; right: 1600px; top: 30px">
                <p>+38(097)-123-45-67</p>
            </div>
            <div style="position: absolute; right: 290px; top: 30px">
                <img src="https://i.ibb.co/F4TVTv2/f.png" alt="Cheetah!" width="38" height="38"/>
            </div>
            <div style="position: absolute; right: 230px; top: 30px">
                <img src="https://i.ibb.co/z2HCRs5/ln.png" alt="Cheetah!" width="38" height="30"/>
            </div>
            <div style="position: absolute; right: 170px; top: 30px">
                <img src="https://i.ibb.co/RCnvgbb/instagrammm.png" alt="Cheetah!" width="43" height="38"/>
            </div>
            <div style="position: absolute; right: 110px; top: 30px">
                <img src="https://i.ibb.co/Yy3r8tP/g.png" alt="Cheetah!" width="38" height="38"/>
            </div>
            <div style="position: absolute; right: 50px; top: 30px">
                <img src="https://i.ibb.co/XZs7y2Q/youtube.png" alt="Cheetah!" width="38" height="38"/>
            </div>
        </div>
    </div>
    <style>
        body {
            background-image: url(https://i.ibb.co/6Rsf8Kp/img111.jpg);
        }

        .header {
            -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=50)";
            filter: alpha(opacity=80);
            -moz-opacity: 0.80;
            -khtml-opacity: 0.8;
            opacity: 0.8;
            padding: 50px;
            text-align: center;
            background: #484d5c;
            color: whitesmoke;
            font-size: 25px;
        }

        .nav-link:link, .nav-link:visited {
            background-color: transparent;
            color: white;
            padding: 50px 25px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-weight: bold;
        }

        .nav-link:hover, .nav-link:active {
            background-color: #515763;
            text-decoration: none;
        }

        .logo-image{
            width: 350px;
            height: 300px;
            border-radius: 20%;
            overflow: hidden;
            margin-top: -20px;
        }

        div p {
            font-family: Oswald Regular;
        }
    </style>
</head>
<body>
<div class="nav justify-content-end">
    <ul class="nav nav-pills">
        <div class="logo-image" style="position: absolute; right: 1590px; top: 95px">
            <img src="https://i.ibb.co/84R6xnJ/design-a-high-quality-home-builder-logo-for-your-construction-companywwww.png" class="img-fluid">
        </div>
        <li class="nav-item" >
            <a class="nav-link" href="${pageContext.request.contextPath}/">HOMEPAGE</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/registration">REGISTRATION</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/login">LOGIN</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">LOGOUT</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/inject">INJECT</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/users/all">USERS</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/products/add">ADD PRODUCT</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/products/all">PRODUCTS</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/products/all/admin">PRODUCTS ADMIN</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/order/all">ORDERS</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/order/all/admin">ORDERS ADMIN</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/shopping-cart/products/get"><img alt="Qries" src="https://i.ibb.co/cxCs8KC/png-clipart-black-shopping-cart-icon-for-free-black-shopping-cart.png" width=25" height="25"></a>
        </li>
    </ul>

</div>
</body>
</html>
