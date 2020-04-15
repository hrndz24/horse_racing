<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="locale" var="locale"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title><fmt:message bundle="${locale}" key="navbar.name"/></title>

    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/mdb.min.css">

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark elegant-color">

    <a class="navbar-brand" href="#"><fmt:message bundle="${locale}" key="navbar.name"/></a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="basicExampleNav">

        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <form action="/controller" method="post">
                    <button class="nav-link btn btn-sm btn-elegant" type="submit" name="command" value="redirect_home">
                        <fmt:message bundle="${locale}"
                                     key="navbar.home_button"/></button>
                </form>
            </li>

            <!-- Language dropdown -->
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"><fmt:message bundle="${locale}"
                                                                           key="navbar.language"/></a>
                <div class="dropdown-menu grey lighten-3"
                     aria-labelledby="navbarDropdownMenuLink" style="width: 10rem;text-align: center">
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="language"/>
                        <input type="hidden" name="jsp" value="${pageContext.request.requestURI}"/>
                        <input style="width: 10rem" type="submit" name="lang" value="EN"
                               class="dropdown-item font-weight-bold"/>
                        <input style="width: 10rem" type="submit" name="lang" value="ES"
                               class="dropdown-item font-weight-bold"/>

                    </form>
                </div>
            </li>
        </ul>
    </div>
</nav>
<img src="../images/rainbow.png" alt="Smiley face" align="left">
<div style="text-align: center; margin-top: 4rem">
    <div class="card" style="width: 23rem; display: inline-block">

        <h5 class="card-header elegant-color white-text text-center py-4">
            <strong><fmt:message bundle="${locale}" key="sign_up"/></strong>
        </h5>

        <div class="card-body px-lg-5 pt-0">

            <form action="/controller" method="post" class="md-form" style="color: #757575;">

                <input placeholder=<fmt:message bundle="${locale}" key="name"/> type="text" id="materialRegisterFormFirstName" class="form-control"
                       pattern="^[a-zA-Z0-9_.-]{3,16}$" name="name" required>

                <input placeholder=<fmt:message bundle="${locale}" key="login"/> type="text" id="materialRegisterFormLastName" class="form-control"
                       pattern="^[a-z0-9_.@-]{3,16}$" name="login" required>

                <input placeholder=<fmt:message bundle="${locale}" key="email"/> type="email" id="materialRegisterFormEmail" class="form-control"
                       pattern="^[A-Za-z0-9+_.-]+@(.+)$" name="email" required>

                <input placeholder=<fmt:message bundle="${locale}" key="password"/> type="password" id="materialRegisterFormPassword" class="form-control"
                       aria-describedby="materialRegisterFormPasswordHelpBlock"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" name="password" required>

                <small id="materialRegisterFormPasswordHelpBlock" class="form-text text-muted mb-4">
                    <fmt:message bundle="${locale}" key="password_explanation"/>
                </small>

                <button class="btn btn-outline-elegant btn-rounded btn-block my-4 waves-effect z-depth-0" type="submit"
                name="command" value="sign_up">
                    <fmt:message bundle="${locale}" key="sign_up"/>
                </button>

                <div class="text-center">
                    <p><fmt:message bundle="${locale}" key="sign_up_agreement"/></p>
                </div>

            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/popper.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/mdb.min.js"></script>
</body>
</html>
