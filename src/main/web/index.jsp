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
    <style>
<%--      <%@include file="/css/mdb.min.css"%>--%>
      body {
        /*background-image: url(images/maxresdefault.jpg);*/
      }
    </style>
  </head>
  <body>
<%--  <button type="button" class="btn btn-primary">Primary</button>--%>
<%--  <button type="button" class="btn btn-secondary">Secondary</button>--%>
<%--  <button type="button" class="btn btn-success">Success</button>--%>
<%--  <button type="button" class="btn btn-danger">Danger</button>--%>
<%--  <button type="button" class="btn btn-warning">Warning</button>--%>
<%--  <button type="button" class="btn btn-info">Info</button>--%>
<%--  <button type="button" class="btn btn-light">Light</button>--%>
<%--  <button type="button" class="btn btn-dark">Dark</button>--%>
<%--  <button type="button" class="btn btn-link">Link</button>--%>
<!--Navbar-->
<nav class="navbar navbar-expand-lg navbar-dark primary-color">

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
    <!-- Links -->


      <div class="md-form my-0">
        <c:out value="Hi">Hello</c:out>
      </div>

  </div>
  <!-- Collapsible content -->

</nav>
<!--/.Navbar-->

  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/popper.min.js"></script>
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <script type="text/javascript" src="js/mdb.min.js"></script>
  </body>
</html>
