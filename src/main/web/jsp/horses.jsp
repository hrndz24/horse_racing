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
    <br/>
    <div class="nav-item dropdown md-form" style="margin-left: 5rem">
        <h1 class="btn nav-link dropdown-toggle font-weight-bold" id="horsesDropdownMenuLink" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false" style="width: 12rem"><fmt:message bundle="${locale}"
                                                                                         key="navbar.horses"/></h1>
        <div class="dropdown-menu blue lighten-5"
             aria-labelledby="horsesDropdownMenuLink" style="text-align: center">
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="change_horses"/>
                <input type="hidden" name="jsp" value="${pageContext.request.requestURI}"/>
                <input type="submit" name="horsesType" value="ALL"
                       class="dropdown-item font-weight-bold"/>
                <input type="submit" name="horsesType" value="PERFORMING"
                       class="dropdown-item font-weight-bold"/>
                <input type="submit" name="horsesType" value="NON-PERFORMING"
                       class="dropdown-item font-weight-bold"/>

            </form>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <c:forEach var="horse" items="${horses}">
                <div class="col-md-6">
                    <div class="card white"
                         style="width: 28rem; display: inline-block;  height: 22rem;padding-top: 2rem;padding-bottom: 2rem;margin: 2rem">
                        <div class="card-body px-lg-5 pt-0">
                            <form action="/controller" method="post">
                                <p><fmt:message bundle="${locale}" key="horse.name"/>: <c:out
                                        value="${horse.name}"/>.</p>

                                <p><fmt:message bundle="${locale}" key="horse.breed"/>: <c:out
                                        value="${horse.breed}"/>.</p>

                                <p><fmt:message bundle="${locale}" key="horse.age"/>: <c:out value="${horse.age}"/>.</p>

                                <p><fmt:message bundle="${locale}" key="horse.races_won_number"/>: <c:out
                                        value="${horse.racesWonNumber}"/>.</p>

                                <p><fmt:message bundle="${locale}" key="horse.races_lost_number"/>: <c:out
                                        value="${horse.racesLostNumber}"/>.</p>

                                <input type="hidden" name="horseId" value="${horse.id}"/>

                                <div style="display: flex;align-items: center; justify-content: center">
                                    <button style="margin: 1rem" type="submit" class="btn elegant-color-dark text-white"
                                            name="command"
                                            value="redirect_edit_horse">
                                        <fmt:message bundle="${locale}" key="edit"/>
                                    </button>
                                    <c:if test="${horse.performing eq true}">
                                        <button style="margin: 1rem" type="submit"
                                                class="btn elegant-color-dark text-white" name="command"
                                                value="invalidate_horse">
                                            <fmt:message bundle="${locale}" key="invalidate"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${horse.performing ne true}">
                                        <button style="margin: 1rem" type="submit"
                                                class="btn elegant-color-dark text-white" name="command"
                                                value="validate_horse">
                                            <fmt:message bundle="${locale}" key="validate"/>
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<!-- change horse's info popup window -->
<c:forEach var="horse" items="${horses}">
    <div class="modal fade" id="${horse.id}" tabindex="-1" role="dialog"
         aria-labelledby="horseModalLabel${horse.id}"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header" style="text-align: center;display: inline-block">
                    <h5 class="modal-title" id="horseModalLabel${horse.id}"><fmt:message
                            bundle="${locale}"
                            key="edit_horse"/></h5>
                </div>
                <form action="/controller" method="post">
                    <div class="modal-body">
                        <div class="md-form">
                            <input id="name" type="text" class="form-control"
                                   name="horseName"
                                   value="<c:out value="${horse.name}"/>" required>

                            <label for="name"><fmt:message bundle="${locale}"
                                                           key="horse.name"/></label>
                        </div>
                        <div class="md-form">
                            <input id="breed" type="text" class="form-control"
                                   name="horseBreed"
                                   value=
                                           "<c:out value="${horse.breed}"/>" required>
                            <label for="breed"><fmt:message bundle="${locale}"
                                                            key="horse.breed"/></label>
                        </div>
                        <div class="md-form">
                            <input id="age" type="text" class="form-control" name="horseAge"
                                   value=
                                           "<c:out value="${horse.age}"/>" required>
                            <label for="age"><fmt:message bundle="${locale}"
                                                          key="horse.age"/></label>
                        </div>
                        <div class="md-form">
                            <input id="wonRaces" type="text" class="form-control"
                                   name="horseWonRaces" value=
                                           "<c:out value="${horse.racesWonNumber}"/>"
                                   required>
                            <label for="wonRaces"><fmt:message bundle="${locale}"
                                                               key="horse.races_won_number"/></label>
                        </div>
                        <div class="md-form">
                            <input id="lostRaces" type="text" class="form-control"
                                   name="horseLostRaces" value=
                                           "<c:out value="${horse.racesLostNumber}"/>"
                                   required>
                            <label for="lostRaces"><fmt:message bundle="${locale}"
                                                                key="horse.races_lost_number"/></label>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-light" data-dismiss="modal">
                            <fmt:message
                                    bundle="${locale}" key="close"/></button>
                        <button type="submit" class="btn btn-elegant" name="command"
                                value="edit_horse"><fmt:message
                                bundle="${locale}"
                                key="submit"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:forEach>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/popper.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/mdb.min.js"></script>

</body>
</html>
