<%--
  Created by IntelliJ IDEA.
  User: xinwei
  Date: 1/30/2017
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wei&Xinwei</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/signUp.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/public.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/fileUpload.css">
    <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand logo" href="/content/first">Wei&Xinwei</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/content/first">BACK</a></li>
                <li><a href="/logout">LOGOUT</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container content">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">
            <form role="form" action="/updateAccount" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label><span class="glyphicon glyphicon-user"></span> User Name</label>
                    <input type="text" class="form-control" name="email"
                           value="${sessionScope.get("user").getUserEmail()}" disabled>
                </div>
                <input type="hidden" name="email" value="${sessionScope.get("user").getUserEmail()}"/>
                <div class="form-group">
                    <label><span class="glyphicon glyphicon-lock"></span> Password</label>
                    <input type="password" class="form-control" name="psw"
                           value="${sessionScope.get("user").getPassword()}">
                </div>
                <!--
                <div class="form-group">
                    <label><span class="glyphicon glyphicon-credit-card"></span> DisplayName</label>
                    <input type="Text" class="form-control" name="name" placeholder="Display Name">
                </div>
                -->
                <div class="form-group">
                    <label><span class="glyphicon glyphicon-camera"></span> Pick Up a Image</label><br>
                    <input type="file" name="file" id="file-1" class="inputfile"
                           data-multiple-caption="{count} files selected"/>
                    <label for="file-1" class="btn"><span>Choose a file</span></label>
                </div>

                <button type="submit" class="btn btn-block">Update Account
                    <span class="glyphicon glyphicon-ok"></span>
                </button>
            </form>
        </div>
        <div class="col-sm-3">

        </div>
    </div>
</div>


<footer class="container-fluid text-center">
    <p><span class="logo">Wei&Xinwei</span></p>
</footer>
</body>
<script src="${pageContext.request.contextPath}/resource/js/fileUpload.js"></script>
</html>
