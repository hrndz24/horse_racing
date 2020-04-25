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

<div class="container">
    <br/>
    <div style="display: flex;align-items: center; justify-content: center">
        <div style="clear:both;">
            <section class="p-md-3 mx-md-5 grey lighten-4" style="width: 32rem;">

                <p><fmt:message bundle="${locale}" key="race_info"/>:</p>

                <p><fmt:message bundle="${locale}" key="race.distance"/>:
                    <c:out value="${race.distance}"/>.</p>

                <p><fmt:message bundle="${locale}" key="race.prize_money"/>:
                    <c:out value="${race.prizeMoney}"/>. </p>

                <p><fmt:message bundle="${locale}" key="race.date"/>:
                    <c:out value="${race.date}"/>.</p>

                <p><fmt:message bundle="${locale}" key="race.location"/>:
                    <c:out value="${race.location}"/>.</p>

                <div style="display: flex;align-items: center; justify-content: center">
                    <button type="button" class="btn btn-elegant" data-toggle="modal"
                            data-target="#changeRaceModal">
                        <fmt:message bundle="${locale}" key="edit"/>
                    </button>
                </div>

            </section>
        </div>
    </div>
    <br/>
    <br/>
    <form action="/controller" method="post" style="margin-left: 10rem;margin-right: 15rem">
        <button style="margin-left: 5rem" type="button" class="btn btn-elegant" data-toggle="modal"
                data-target="#addHorsesModal">
            <fmt:message bundle="${locale}" key="add_horses"/>
        </button>

        <button style="float: right" type="submit" class="btn btn-elegant" name="command" value="delete_race">
            <fmt:message bundle="${locale}" key="delete_race"/>
        </button>
    </form>
    <br/>
    <br/>
    <c:forEach var="horse" items="${horses}">
        <div class="container">
            <section class="p-md-3 mx-md-5 grey lighten-3">
                <form action="/controller" method="post">
                    <p><fmt:message bundle="${locale}" key="horse.name"/>: <c:out value="${horse.name}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.breed"/>: <c:out value="${horse.breed}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.age"/>: <c:out value="${horse.age}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.races_won_number"/>: <c:out
                            value="${horse.racesWonNumber}"/>.</p>

                    <p><fmt:message bundle="${locale}" key="horse.races_lost_number"/>: <c:out
                            value="${horse.racesLostNumber}"/>.</p>


                    <input type="hidden" name="horseId" value="${horse.id}"/>
                    <button class="btn btn-elegant" type="submit" name="command" value="remove_horse_from_race">
                        <fmt:message bundle="${locale}" key="exclude"/></button>
                </form>
            </section>
            <br/>
        </div>
    </c:forEach>
</div>

<!-- change race's info popup window -->
<div class="modal fade" id="changeRaceModal" tabindex="-1" role="dialog" aria-labelledby="raceModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="raceModalLabel"><fmt:message bundle="${locale}"
                                                                         key="edit_race"/></h5>
            </div>

            <form action="/controller" method="post">
                <div class="modal-body">
                    <div class="md-form">
                        <input id="distance" type="text" class="form-control" name="raceDistance" value=
                                "<c:out value="${race.distance}"/>" required>
                        <label for="distance"><fmt:message bundle="${locale}" key="race.distance"/></label>
                    </div>
                    <div class="md-form">
                        <input id="prizeMoney" type="text" class="form-control" name="racePrizeMoney" value=
                                "<c:out value="${race.prizeMoney}"/>" required>
                        <label for="prizeMoney"><fmt:message bundle="${locale}" key="race.prize_money"/></label>
                    </div>
                    <div class="md-form">
                        <input id="date" type="text" class="form-control" name="raceDate" value=
                                "<c:out value="${race.date}"/>" required>
                        <label for="date"><fmt:message bundle="${locale}" key="race.date"/></label>
                    </div>
                    <div class="md-form">
                        <input id="location" type="text" class="form-control" name="raceLocation" value=
                                "<c:out value="${race.location}"/>" required>
                        <label for="location"><fmt:message bundle="${locale}" key="race.location"/></label>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="edit_race"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- add horses popup window -->
<div class="modal fade" id="addHorsesModal" tabindex="-1" role="dialog" aria-labelledby="addHorsesModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="addHorsesModalLabel"><fmt:message bundle="${locale}"
                                                                              key="add_horses"/></h5>
            </div>
            <form action="/controller" method="post">
                <div class="modal-body">
                    <c:forEach var="horse" items="${performingHorses}">
                        <p>
                            <input type="checkbox" name="horseId" value="${horse.id}">
                            <c:out value="${horse.name}"/>
                        </p>
                    </c:forEach>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="add_horse_to_race"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
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


