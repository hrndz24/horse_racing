<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark elegant-color">

    <span class="navbar-brand"><fmt:message bundle="${locale}" key="navbar.name"/></span>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="basicExampleNav">

        <ul class="navbar-nav mr-auto">

            <li class="nav-item elegant-color">
                <form action="/controller" method="post" class="nav-item md-form"
                      style="width: 8rem; font-size : 12px; height: 1rem">
                    <input type="hidden" name="command" value="show_races" style="height: 0"/>
                    <button class="btn btn-elegant btn-sm text-white" type="submit"><fmt:message bundle="${locale}"
                                                                                                 key="navbar.races"/></button>
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

<div class="container">
    <br/>
    <span>Info about the race at </span> <c:out value="${raceDate}"/>.
    <br/>
    <form action="/controller" method="post">
        <input type="hidden" name="raceId" value="${raceId}"/>
        <c:forEach var="horse" items="${horses}">
            <div class="container">
                <section class="p-md-3 mx-md-5 grey lighten-3">

                    <p><fmt:message bundle="${locale}" key="horse.name"/>: <c:out value="${horse.name}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.breed"/>: <c:out value="${horse.breed}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.age"/>: <c:out value="${horse.age}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.races_won_number"/>: <c:out
                            value="${horse.racesWonNumber}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.races_lost_number"/>: <c:out
                            value="${horse.racesLostNumber}"/>.</p>

                    <input type="hidden" name="horseId" value="${horse.id}"/>
                    <br/>
                    <label for="oddsInFavour">
                        <fmt:message bundle="${locale}" key="odds_in_favour"/>
                        <input id="oddsInFavour" style="width: 5rem" type="text" class="form-control"
                               pattern="\d{1,3}" name="oddsInFavour" required/>

                        <label for="oddsAgainst">
                            <fmt:message bundle="${locale}" key="odds_against"/>
                        </label>
                        <input id="oddsAgainst" style="width: 5rem" type="text" class="form-control"
                               pattern="\d{1,3}" name="oddsAgainst" required/>

                </section>
                <br/>
            </div>
        </c:forEach>
        <button class="btn btn-elegant" type="submit" name="command" value="submit_odds">
            <fmt:message bundle="${locale}" key="submit"/></button>
    </form>
</div>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/popper.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/mdb.min.js"></script>
</body>
</html>

