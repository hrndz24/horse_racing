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

<div class="container">
    <br/>
    <div style="display: flex;align-items: center; justify-content: center;margin-top: 2rem">
        <div style="clear:both;">
            <div class="card white"
                 style="width: 33rem; display: inline-block;  height: 32rem;padding: 2rem">
                <div class="card-body px-lg-5 pt-0">
                    <div class="text-center font-weight-bold">
                        <h4><fmt:message bundle="${locale}" key="horse_info"/>:</h4>
                    </div>
                    <hr>
                    <form action="/controller" method="post">
                        <input type="hidden" name="horseId" value="${horse.id}">
                        <div class="md-form">
                            <input id="name" type="text" class="form-control" name="horseName" value=
                                    "<c:out value="${horse.name}"/>" required>
                            <label for="name"><fmt:message bundle="${locale}" key="horse.name"/></label>
                        </div>

                        <div class="md-form">
                            <input id="breed" type="text" class="form-control" name="horseBreed" value=
                                    "<c:out value="${horse.breed}"/>" required>
                            <label for="breed"><fmt:message bundle="${locale}" key="horse.breed"/></label>
                        </div>

                        <div class="md-form">
                            <input id="age" type="text" class="form-control" name="horseAge" value=
                                    "<c:out value="${horse.age}"/>" required>
                            <label for="age"><fmt:message bundle="${locale}" key="horse.age"/></label>
                        </div>

                        <div class="md-form">
                            <input id="wonRaces" type="text" class="form-control" name="horseWonRaces" value=
                                    "<c:out value="${horse.racesWonNumber}"/>" required>
                            <label for="wonRaces"><fmt:message bundle="${locale}" key="horse.races_won_number"/></label>
                        </div>

                        <div class="md-form">
                            <input id="lostRaces" type="text" class="form-control" name="horseLostRaces" value=
                                    "<c:out value="${horse.racesLostNumber}"/>" required>
                            <label for="lostRaces"><fmt:message bundle="${locale}" key="horse.races_lost_number"/></label>
                        </div>

                        <div style="display: flex;align-items: center; justify-content: center">
                            <button type="submit" class="btn elegant-color-dark text-white" name="command"
                                    value="edit_horse">
                                <fmt:message bundle="${locale}" key="submit"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <br/>
</div>

<footer class="page-footer font-small elegant-color fixed-bottom"  style="height: 3rem">
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



