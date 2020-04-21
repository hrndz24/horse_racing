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
<img src="../images/twilight.png" alt="Smiley face" align="left">

<br/>
<br/>
<br/>
<div style="display: flex;align-items: center; justify-content: center">
    <div style="clear:both;">
        <section class="p-md-3 mx-md-5 grey lighten-4" style="width: 32rem;">

            <p><fmt:message bundle="${locale}" key="name"/>: <c:out value="${userName}"/>.
                <button style="float: right;" type="button" class="btn btn-elegant btn-sm" data-toggle="modal"
                        data-target="#changeNameModal">
                    <fmt:message bundle="${locale}" key="change_name"/>
                </button>
            </p>
            <br/>
            <p><fmt:message bundle="${locale}" key="login"/>: <c:out value="${user.login}"/>.
                <button style="float: right" type="button" class="btn btn-elegant btn-sm" data-toggle="modal"
                        data-target="#changeLoginModal">
                    <fmt:message bundle="${locale}" key="change_login"/>
                </button>
            </p>
            <br/>
            <p><fmt:message bundle="${locale}" key="email"/>: <c:out value="${user.email}"/>.
                <button style="float: right;" type="button" class="btn btn-elegant btn-sm" data-toggle="modal"
                        data-target="#changeEmailModal">
                    <fmt:message bundle="${locale}" key="change_email"/>
                </button>
            </p>
            <br/>
            <p><fmt:message bundle="${locale}" key="balance"/>: <c:out value="${user.balance}"/>.
                <button style="float: right;" type="button" class="btn btn-elegant btn-sm" data-toggle="modal"
                        data-target="#replenishAccountModal">
                    <fmt:message bundle="${locale}" key="replenish"/>
                </button>
            </p>
        </section>
        <br/>
        <br/>
        <form action="/controller" method="post" style="float: right; margin-right: 5rem">
            <button style="float: right" type="submit" name="command" value="change_password" class="btn btn-elegant"
                    data-toggle="modal"
                    data-target="#changePasswordModal">
                <fmt:message bundle="${locale}" key="change_password"/>
            </button>
            <br/>
            <br/>
            <button style="float: right" type="submit" name="command" value="view_bets" class="btn btn-elegant">
                <fmt:message bundle="${locale}" key="view_bets"/>
            </button>
        </form>
    </div>
</div>

<!-- change login popup window -->
<div class="modal fade" id="changeLoginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="loginModalLabel"><fmt:message bundle="${locale}"
                                                                          key="change_login"/></h5>
            </div>
            <form action="/controller" method="post">
                <div class="modal-body">
                    <input type="text" class="form-control" name="login" pattern="^[a-z0-9_.@-]{3,16}$" required
                           placeholder=
                    <fmt:message bundle="${locale}" key="login"/>>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="edit_login"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- replenish popup window -->
<div class="modal fade" id="replenishAccountModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="accountModalLabel"><fmt:message bundle="${locale}"
                                                                            key="replenish"/></h5>
            </div>
            <form action="/controller" method="post">
                <div class="modal-body">
                    <input type="text" class="form-control" name="sum" pattern="\d{2,10}" required
                           placeholder=
                    <fmt:message bundle="${locale}" key="sum"/>>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="replenish_account">
                        <fmt:message
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
