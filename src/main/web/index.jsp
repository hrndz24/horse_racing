<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Welcome</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/mdb.min.css">

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark elegant-color">

    <!-- Navbar brand -->
    <a class="navbar-brand" href="#">Navbar</a>

    <!-- Collapse button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Collapsible content -->
    <div class="collapse navbar-collapse" id="basicExampleNav">

        <!-- Links -->
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home
                    <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Features</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Pricing</a>
            </li>

            <!-- Dropdown -->
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">Dropdown</a>
                <div class="dropdown-menu dropdown-primary" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="#">Action</a>
                    <a class="dropdown-item" href="#">Another action</a>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </li>

        </ul>

    </div>
</nav>
<!--/.Navbar-->
<img src="images/rainbow.png" alt="Smiley face" align="left">
<div style="text-align: center; margin-top: 4rem">
    <div class="card" style="width: 23rem; display: inline-block">

        <h5 class="card-header elegant-color white-text text-center py-4">
            <strong>Log in</strong>
        </h5>

        <div class="card-body px-lg-5 pt-0">

            <form action="/controller" method="post" class="md-form" style="color: #757575;">

                <input placeholder="Login" type="text" id="materialRegisterFormLastName" class="form-control"
                       pattern="^[a-z0-9_.@-]{3,16}$" name="login" required>

                <input placeholder="Password" type="password" id="materialRegisterFormPassword" class="form-control"
                       aria-describedby="materialRegisterFormPasswordHelpBlock"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" name="password" required>


                <button class="btn btn-elegant text-white btn-block my-4 waves-effect z-depth-0" type="submit"
                        name="command" value="log_in">
                    Log In
                </button>

                <div class="text-center">
                    <p>Don't have an account yet?</p>
                </div>

                <a class="btn btn-outline-elegant" href="jsp/signUp.jsp">
                    Sign up
                </a>


            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/popper.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/mdb.min.js"></script>
</body>
</html>
