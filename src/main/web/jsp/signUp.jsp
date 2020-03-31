<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Welcome</title>

    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/mdb.min.css">

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark elegant-color">

    <a class="navbar-brand" href="#">Horse Racing</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="basicExampleNav">


        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="../index.jsp">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Races</a>
            </li>

            <!-- Language dropdown -->
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">Language</a>
                <div class="dropdown-menu grey lighten-3" style="text-align: center"
                     aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item font-weight-bold" href="#">English</a>
                    <a class="dropdown-item font-weight-bold" href="#">Spanish</a>
                </div>
            </li>

        </ul>

    </div>
</nav>
<img src="../images/rainbow.png" alt="Smiley face" align="left">
<div style="text-align: center; margin-top: 4rem">
    <div class="card" style="width: 23rem; display: inline-block">

        <h5 class="card-header elegant-color white-text text-center py-4">
            <strong>Sign up</strong>
        </h5>

        <div class="card-body px-lg-5 pt-0">

            <form action="/controller" method="post" class="md-form" style="color: #757575;">

                <input placeholder="Name" type="text" id="materialRegisterFormFirstName" class="form-control"
                       pattern="^[a-zA-Z0-9_.-]{3,16}$" name="name" required>

                <input placeholder="Login" type="text" id="materialRegisterFormLastName" class="form-control"
                       pattern="^[a-z0-9_.@-]{3,16}$" name="login" required>

                <input placeholder="Email" type="email" id="materialRegisterFormEmail" class="form-control"
                       pattern="^[A-Za-z0-9+_.-]+@(.+)$" name="email" required>

                <input placeholder="Password" type="password" id="materialRegisterFormPassword" class="form-control"
                       aria-describedby="materialRegisterFormPasswordHelpBlock"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" name="password" required>

                <small id="materialRegisterFormPasswordHelpBlock" class="form-text text-muted mb-4">
                    At least 8 characters, 1 digit and 1 upper case letter
                </small>

                <button class="btn btn-outline-elegant btn-rounded btn-block my-4 waves-effect z-depth-0" type="submit"
                name="command" value="sign_up">
                    Sign up
                </button>

                <div class="text-center">
                    <p>By clicking
                        <em>Sign up</em> you agree to use the best horse racing website and enjoy your time!</p>
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
