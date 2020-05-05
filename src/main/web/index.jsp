<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${empty sessionScope.language}">
    <fmt:setLocale value="es"/>
</c:if>
<c:if test="${not empty sessionScope.language}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="locale" var="locale"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/mdb.min.css">
    <title><fmt:message bundle="${locale}" key="navbar.name"/></title>
    <style>
        body, html {
            height: 100%;
            background-image: url("https://mdbootstrap.com/img/Photos/Horizontal/Nature/full page/img(11).jpg");
            background-repeat: repeat-y;
            background-position: center;
            background-size: cover;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark elegant-color-dark fixed-top" style="height: 4rem">

    <a class="navbar-brand" href="#"><fmt:message bundle="${locale}" key="navbar.name"/></a>

    <div class="collapse navbar-collapse" id="basicExampleNav">

        <ul class="navbar-nav mr-auto">
            <li class="md-form align-items-center">
                <form action="/controller" method="post">
                    <button class="nav-link btn btn-sm text-white" type="submit" name="command" value="redirect_home">
                        <fmt:message bundle="${locale}"
                                     key="navbar.home_button"/></button>
                </form>
            </li>

            <!-- Language dropdown -->
            <li class="nav-item dropdown md-form">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"><fmt:message bundle="${locale}"
                                                                           key="navbar.language"/></a>
                <div class="dropdown-menu"
                     aria-labelledby="navbarDropdownMenuLink" style="width: 10rem;text-align: center">
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="language"/>
                        <input type="hidden" name="jsp" value="${pageContext.request.requestURI}"/>
                        <button style="width: 10rem" type="submit" name="lang" value="EN"
                                class="dropdown-item text-uppercase font-weight-bold"><fmt:message bundle="${locale}"
                                                                                                   key="navbar.language_english"/></button>
                        <button style="width: 10rem" type="submit" name="lang" value="ES"
                                class="dropdown-item text-uppercase font-weight-bold"><fmt:message bundle="${locale}"
                                                                                                   key="navbar.language_spanish"/></button>

                    </form>
                </div>
            </li>
        </ul>
    </div>
</nav>

<%--log in stuff--%>
<div style="text-align: center; margin-top: 3rem">
    <h1 style="margin-top: 1rem"><strong><fmt:message bundle="${locale}" key="welcome"/></strong></h1>
    <div class="card" style="width: 28rem; display: inline-block; margin-top: 1rem">

        <h5 class="card-header elegant-color white-text text-center py-4">
            <strong><fmt:message bundle="${locale}" key="log_in"/></strong>
        </h5>

        <div class="card-body px-lg-5 pt-0">
            <br/>
            <form action="/controller" method="post" class="md-form" style="color: #757575;">
                <div class="md-form">
                    <input id="form1" type="text" class="form-control"
                           pattern="^[a-z0-9_.@-]{3,16}$" name="login" required>
                    <label for="form1"><fmt:message bundle="${locale}" key="login"/></label>
                </div>
                <div class="md-form">
                    <input type="password" id="inputValidationEx2" class="form-control validate"
                           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" name="password" required>
                    <label for="inputValidationEx2"><fmt:message
                            bundle="${locale}" key="password"/></label>
                </div>

                <button class="btn btn-elegant text-white btn-block my-4 waves-effect z-depth-0" type="submit"
                        name="command" value="log_in">
                    <fmt:message bundle="${locale}" key="log_in"/>
                </button>

            </form>
            <div class="text-center">
                <p><fmt:message bundle="${locale}" key="invitation_to_sign_up"/></p>
            </div>

            <form action="/controller" method="post">
                <button class="btn btn-outline-elegant" type="submit" name="command" value="redirect_sign_up">
                    <fmt:message bundle="${locale}" key="sign_up"/>
                </button>
            </form>

        </div>
    </div>
</div>

<footer class="page-footer font-small elegant-color fixed-bottom"  style="height: 3rem">
    <!-- Copyright -->
    <div class="footer-copyright text-center py-3">
        <ctg:copyright/>
    </div>
</footer>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/popper.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/mdb.min.js"></script>
</body>
</html>
