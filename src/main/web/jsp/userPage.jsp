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

            <li class="md-form">
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
            <c:if test="${sessionScope.user.userRole.id==1}">
                <li class="nav-item md-form">
                    <form action="/controller" method="post">
                        <button class="nav-link btn btn-sm" type="submit" name="command"
                                value="show_users">
                            <fmt:message bundle="${locale}" key="navbar.users"/></button>
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
                    <c:out value="${user.getName()}"/>
                </button>
            </form>
        </div>
    </div>
</nav>
<img src="../images/rarity.png" alt="Smiley face" align="left">

<br/>
<br/>
<br/>
<div style="display: flex;align-items: center; justify-content: center">
    <div style="clear:both;">
        <div class="card white"
             style="width: 27rem; display: inline-block;padding-top: 2rem;margin: 1rem">
            <div class="card-body px-lg-5 pt-0">
                <p><fmt:message bundle="${locale}" key="name"/>: <c:out value="${user.getName()}"/>.
                    <button style="float: right;" type="button" class="btn elegant-color-dark text-white btn-sm"
                            data-toggle="modal"
                            data-target="#changeNameModal">
                        <fmt:message bundle="${locale}" key="change_name"/>
                    </button>
                </p>
                <br/>
                <p><fmt:message bundle="${locale}" key="login"/>: <c:out value="${user.login}"/>.
                    <button style="float: right" type="button" class="btn elegant-color-dark text-white btn-sm"
                            data-toggle="modal"
                            data-target="#changeLoginModal">
                        <fmt:message bundle="${locale}" key="change_login"/>
                    </button>
                </p>
                <br/>
                <p><fmt:message bundle="${locale}" key="email"/>: <c:out value="${user.email}"/>.
                    <button style="float: right;" type="button" class="btn elegant-color-dark text-white btn-sm"
                            data-toggle="modal"
                            data-target="#changeEmailModal">
                        <fmt:message bundle="${locale}" key="change_email"/>
                    </button>
                </p>
                <br/>
                <p><fmt:message bundle="${locale}" key="balance"/>: <c:out value="${user.balance}"/>.
                    <button style="float: right;" type="button" class="btn elegant-color-dark text-white btn-sm"
                            data-toggle="modal"
                            data-target="#replenishAccountModal">
                        <fmt:message bundle="${locale}" key="replenish"/>
                    </button>
                </p>
            </div>
        </div>
        <br/>
        <form action="/controller" method="post" style="float: right; margin-right: 2rem">
            <button style="float: right" type="button"
                    class="btn btn-light font-weight-bolder"
                    data-toggle="modal"
                    data-target="#changePasswordModal">
                <fmt:message bundle="${locale}" key="change_password"/>
            </button>
            <br/>
            <br/>
            <br/>
            <c:if test="${sessionScope.user.userRole.id==2}">
                <button style="float: right" type="submit" name="command" value="view_bets"
                        class="btn btn-light font-weight-bolder">
                    <fmt:message bundle="${locale}" key="view_bets"/>
                </button>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <button style="float: right; background-color: #8c0009" type="button" data-toggle="modal"
                        data-target="#deleteModal"
                        class="btn text-white">
                    <fmt:message bundle="${locale}" key="deactivate_account"/>
                </button>
            </c:if>
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
                           placeholder=<fmt:message bundle="${locale}" key="login"/>>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="change_login"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- change name popup window -->
<div class="modal fade" id="changeNameModal" tabindex="-1" role="dialog" aria-labelledby="nameModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="nameModalLabel"><fmt:message bundle="${locale}"
                                                                         key="change_name"/></h5>
            </div>
            <form action="/controller" method="post">
                <div class="modal-body">
                    <input type="text" class="form-control" name="name" pattern="^[a-zA-Z0-9_.-]{3,16}$" required
                           placeholder=
                    <fmt:message bundle="${locale}" key="name"/>>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="change_name"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- change email popup window -->
<div class="modal fade" id="changeEmailModal" tabindex="-1" role="dialog" aria-labelledby="emailModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="emailModalLabel"><fmt:message bundle="${locale}"
                                                                          key="change_email"/></h5>
            </div>
            <form action="/controller" method="post">
                <div class="modal-body">
                    <input type="text" class="form-control" name="email" pattern="^[A-Za-z0-9+_.-]+@(.+)$" required
                           placeholder=<fmt:message bundle="${locale}" key="email"/>>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="change_email"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- change password popup window -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="passwordModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="passwordModalLabel"><fmt:message bundle="${locale}"
                                                                             key="change_password"/></h5>
            </div>
            <form action="/controller" method="post">
                <div class="modal-body">
                    <div class="md-form">
                        <input type="password" id="oldPassword" class="form-control" name="oldPassword"
                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" required>
                        <label for="oldPassword"><fmt:message bundle="${locale}" key="old_password"/></label>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="md-form">
                        <input type="password" id="newPassword" class="form-control" name="newPassword"
                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" required>
                        <label for="newPassword"><fmt:message bundle="${locale}" key="new_password"/></label>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                            bundle="${locale}" key="close"/></button>
                    <button type="submit" class="btn btn-elegant" name="command" value="change_password"><fmt:message
                            bundle="${locale}"
                            key="submit"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- replenish popup window -->
<div class="modal fade" id="replenishAccountModal" tabindex="-1" role="dialog" aria-labelledby="accountModalLabel"
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

<!-- delete popup window -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center;display: inline-block">
                <h5 class="modal-title" id="confirmModalLabel"><fmt:message bundle="${locale}"
                                                                            key="confirm"/></h5>
            </div>
            <div style="display: flex;align-items: center; justify-content: center">
                <form action="/controller" method="post">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-light" data-dismiss="modal"><fmt:message
                                bundle="${locale}" key="close"/></button>
                        <button type="submit" class="btn btn-elegant" name="command" value="deactivate_account">
                            <fmt:message bundle="${locale}" key="confirm"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

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
