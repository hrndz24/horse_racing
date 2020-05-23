<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title><fmt:message bundle="${locale}" key="navbar.name"/></title>

    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/mdb.min.css">
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
<nav class="navbar navbar-expand-lg navbar-dark elegant-color-dark" style="height: 4rem">

    <span class="navbar-brand md-form"><fmt:message bundle="${locale}" key="navbar.name"/></span>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="basicExampleNav">

        <ul class="navbar-nav mr-auto">

            <li class="md-form align-items-center">
                <form action="/controller" method="post" class="nav-item">
                    <button class="nav-link btn btn-sm" type="submit" name="command" value="show_races">
                        <fmt:message bundle="${locale}" key="navbar.races"/></button>
                </form>
            </li>
            <c:if test="${sessionScope.user.userRole.id==1}">
                <li class="nav-item md-form">
                    <form action="/controller" method="post">
                        <button class="nav-link btn btn-sm" type="submit" name="command"
                                value="show_horses">
                            <fmt:message bundle="${locale}" key="navbar.horses"/></button>
                    </form>
                </li>
            </c:if>

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
        <div>
            <form action="/controller" method="post" class="nav-item my-1">
                <button type="submit" name="command" value="log_out" class="nav-link btn btn-sm text-white">
                    <fmt:message bundle="${locale}" key="log_out"/>
                </button>
            </form>
        </div>
        <div>
            <form action="/controller" method="post" class="nav-item my-0">
                <button type="submit" name="command" value="redirect_user" class="nav-link btn btn-sm text-white">
                    <c:out value="${userName}"/>
                </button>
            </form>
        </div>
    </div>
</nav>

<br/>
<%--races--%>
<div class="container">

    <c:forEach var="race" items="${pastRaces}">
        <div style="display: flex;align-items: center; justify-content: center">
            <div class="card white"
                 style="width: 33rem; display: inline-block;padding-top: 2rem;margin: 1rem">
                <div class="card-body px-lg-5 pt-0">
                    <form action="/controller" method="post">
                        <div class="text-center font-weight-bold">
                            <h4><fmt:message bundle="${locale}" key="race_info"/>:</h4>
                        </div>
                        <hr>
                        <p><fmt:message bundle="${locale}" key="race.distance"/>:
                            <c:out value="${race.distance}"/>.</p>
                        <p><fmt:message bundle="${locale}" key="race.prize_money"/>:
                            <c:out value="${race.prizeMoney}"/>. </p>
                        <p><fmt:message bundle="${locale}" key="race.date"/>:
                            <c:out value="${race.date}"/>.</p>
                        <p><fmt:message bundle="${locale}" key="race.location"/>:
                            <c:out value="${race.location}"/>.</p>
                        <input type="hidden" name="raceId" value="${race.id}"/>
                        <div style="display: flex;align-items: center; justify-content: center">
                            <button class="btn elegant-color-dark text-white" type="submit" name="command"
                                    value="show_past_race">
                                <fmt:message
                                        bundle="${locale}"
                                        key="view_details"/></button>
                        </div>
                    </form>
                </div>
            </div>
            <br/>
        </div>
    </c:forEach>
</div>
<br/>
<br/>
<footer class="page-footer font-small elegant-color fixed-bottom" style="height: 2.5rem">
    <!-- Copyright -->
    <div class="footer-copyright text-center py-3">
        <ctg:copyright/>
    </div>
</footer>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/popper.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/mdb.min.js"></script>
</body>
</html>
