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
        <c:if test="${sessionScope.user.userRole.id==1}">
            <div>
                <form class="form-inline nav-item my-2" method="post" action="/controller">
                    <input type="hidden" name="command" value="search_user">
                    <input class="form-control mr-sm-2" type="text" name="search"
                           placeholder="<fmt:message bundle="${locale}" key="search"/>"
                           aria-label="Search">
                </form>
            </div>
        </c:if>
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
<br/>
<%--users--%>
<div class="container">
    <table class="table table-bordered table-striped table-light text-white">
        <thead class="black white-text text-center">
        <tr>
            <th><fmt:message bundle="${locale}" key="id"/></th>
            <th><fmt:message bundle="${locale}" key="name"/></th>
            <th><fmt:message bundle="${locale}" key="login"/></th>
            <th><fmt:message bundle="${locale}" key="email"/></th>
            <th><fmt:message bundle="${locale}" key="active"/></th>
        </tr>
        </thead>
        <c:forEach var="user" items="${users}">
            <tr>
                <td class="font-weight-bold text-dark text-center"><c:out value="${user.id}"/></td>
                <td class="font-weight-bold text-dark text-center"><c:out value="${user.getName()}"/></td>
                <td class="font-weight-bold text-dark text-center"><c:out value="${user.login}"/></td>
                <td class="font-weight-bold text-dark text-center"><c:out value="${user.email}"/></td>
                <td class="font-weight-bold text-dark text-center">
                    <form method="post" action="/controller">
                        <input type="hidden" name="userId" value="${user.id}">
                        <input type="hidden" name="pageNumber" value="${currentPage}">
                        <c:if test="${user.active eq true}">
                            <button style="margin: 1rem; background-color: #a80009" type="submit"
                                    class="btn text-white" name="command"
                                    value="block_user">
                                <fmt:message bundle="${locale}" key="block"/>
                            </button>
                        </c:if>
                        <c:if test="${user.active eq false}">
                            <button style="margin: 1rem" type="submit"
                                    class="btn light-blue darken-4 text-white" name="command"
                                    value="unblock_user">
                                <fmt:message bundle="${locale}" key="unblock"/>
                            </button>
                        </c:if>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<br/>

<div class="container">
    <div class="row">
        <%--For displaying Previous link except for the 1st page --%>
        <c:if test="${currentPage != 1}">
            <form method="post" action="/controller">
                <button type="submit" class="btn btn-sm unique-color text-white" name="command"
                        value="show_users"><fmt:message bundle="${locale}" key="previous"/>
                </button>
                <input type="hidden" name="pageNumber" value="${currentPage-1}">
            </form>
        </c:if>
        <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
        <c:forEach var="i" begin="1" end="${pageQuantity}">
            <div class="col-12 col-md-auto">
                <c:if test="${currentPage eq i}">
                    <button class="btn btn-sm elegant-color-dark text-white" type="button">${i}</button>
                </c:if>
                <c:if test="${currentPage ne i}">
                    <form method="post" action="/controller">
                        <button type="submit" class="btn btn-sm btn-light" name="command"
                                value="show_users">${i}</button>
                        <input type="hidden" name="pageNumber" value="${i}">
                    </form>
                </c:if>
            </div>
        </c:forEach>
        <%--For displaying Next link --%>
        <c:if test="${currentPage lt pageQuantity}">
            <form method="post" action="/controller">
                <button type="submit" class="btn btn-sm unique-color text-white" name="command"
                        value="show_users"><fmt:message bundle="${locale}" key="next"/>
                </button>
                <input type="hidden" name="pageNumber" value="${currentPage+1}">
            </form>
        </c:if>
    </div>
</div>

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
