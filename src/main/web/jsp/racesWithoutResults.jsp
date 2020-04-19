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
<nav class="navbar navbar-expand-lg navbar-dark elegant-color" style="height: 4rem">

    <span class="navbar-brand md-form"><fmt:message bundle="${locale}" key="navbar.name"/></span>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="basicExampleNav">

        <ul class="navbar-nav mr-auto">

            <li class="nav-item md-form">
                <form action="/controller" method="post">
                    <button class="nav-link btn btn-sm btn-elegant" type="submit" name="command" value="show_races">
                        <fmt:message bundle="${locale}" key="navbar.races"/></button>
                </form>
            </li>

            <!-- Language dropdown -->
            <li class="nav-item dropdown md-form">
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
        <div>
            <form action="/controller" method="post" class="nav-item my-1">
                <button type="submit" name="command" value="log_out" class="nav-link btn btn-sm btn-elegant">
                    <fmt:message bundle="${locale}" key="log_out"/>
                </button>
            </form>
        </div>
        <div>
            <form action="/controller" method="post" class="nav-item my-0">
                <button type="submit" name="command" value="redirect_user" class="nav-link btn btn-sm btn-elegant">
                    <c:out value="${userName}"/>
                </button>
            </form>
        </div>
    </div>
</nav>

<br/>
<%--races--%>
<div class="container">
    <c:forEach var="race" items="${racesWithoutResults}">
        <div class="container">
            <section class="p-md-3 mx-md-5 grey lighten-3">
                <form action="/controller" method="post">
                    <p><fmt:message bundle="${locale}" key="race.distance"/>:
                        <c:out value="${race.distance}"/>.</p>
                    <p><fmt:message bundle="${locale}" key="race.prize_money"/>:
                        <c:out value="${race.prizeMoney}"/>. </p>
                    <p><fmt:message bundle="${locale}" key="race.date"/>:
                        <c:out value="${race.date}"/>.</p>
                    <p><fmt:message bundle="${locale}" key="race.location"/>:
                        <c:out value="${race.location}"/>.</p>
                    <input type="hidden" name="raceId" value="${race.id}"/>
                    <input type="hidden" name="raceDate" value="${race.date}"/>
                    <input type="hidden" name="raceLocation" value="${race.location}"/>
                    <c:if test="${sessionScope.user.userRole.id==1}">
                        <button class="btn btn-elegant" type="submit" name="command" value="set_results"><fmt:message
                                bundle="${locale}"
                                key="set_results"/></button>
                    </c:if>
                </form>
            </section>
            <br/>
        </div>
    </c:forEach>
</div>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/popper.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/mdb.min.js"></script>
</body>
</html>
