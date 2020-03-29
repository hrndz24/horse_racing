<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Welcome</title>

    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/mdb.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark elegant-color">

    <a class="navbar-brand" href="#">Horse Racing</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="basicExampleNav">


        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="../index.jsp">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Races</a>
            </li>

            <!-- Language dropdown -->
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">Language</a>
                <div class="dropdown-menu grey lighten-3" style="text-align: center"
                     aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item font-weight-bold" href="#">English</a>
                    <a class="dropdown-item font-weight-bold" href="#">Spanish</a>
                </div>
            </li>

        </ul>
        <div class="md-form my-0 text-white">
            <c:out value="${userName}"/>
        </div>
    </div>
</nav>
<div class="container" style="text-align: center">
    <c:out value="Welcome, ${userName}"/>
</div>
</body>
</html>
