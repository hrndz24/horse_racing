<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form action="/controller" method="post" class="md-form">
    <div class="container">
        <div class="card" style="width: 33rem; display: inline-block; text-align: center; height: 22rem;padding: 2rem">
            <div class="card-body px-lg-5 pt-0">
                <div class="text-center font-weight-bold">
                    <h4><fmt:message bundle="${locale}" key="race_info"/>:</h4>
                </div>
                <hr>
                <input placeholder=
                       <fmt:message bundle="${locale}" key="race.location"/> type="text"
                       class="form-control" pattern="[\w\d_-\s]+" name="raceLocation" required>
                <input placeholder=
                       <fmt:message bundle="${locale}" key="race.prize_money"/> type="text"
                       class="form-control" pattern="^[0-9]+(\.[0-9]{1,2})?$" name="racePrizeMoney" required>

                <div class="md-form">
                    <input placeholder=
                           "<fmt:message bundle="${locale}" key="race.date"/>" type="datetime-local" data-provide="datepicker"
                           id="date-picker-example"
                           class="form-control datepicker" name="raceDate">
                </div>
                <input placeholder=
                       <fmt:message bundle="${locale}" key="race.distance"/> type="text"
                       class="form-control" pattern="\d{3,}" name="raceDistance" required>
            </div>
        </div>
    </div>
    <div class="container">
        <div style="position: absolute; right: 550px; top: 20px">
            <p class="font-weight-bold h4"><fmt:message bundle="${locale}" key="select_horses"/></p>
            <c:forEach var="horse" items="${performingHorses}">
                <div class="custom-control custom-checkbox">
                    <input type="checkbox" class="custom-control-input" id="${horse.id}" name="horseId"
                           value="${horse.id}">
                    <label class="custom-control-label text-dark" for="${horse.id}"><c:out
                            value="${horse.name}"/></label>
                </div>
                <br/>
            </c:forEach>
            <br/>
            <button type="submit" value="add_race" name="command" class="btn elegant-color-dark text-white">
                <fmt:message bundle="${locale}" key="submit"/>
            </button>
        </div>
    </div>
</form>

<footer class="page-footer font-small elegant-color fixed-bottom"  style="height: 2.5rem">
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
