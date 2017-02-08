<%--
  Created by IntelliJ IDEA.
  User: xinwei
  Date: 1/25/2017
  Time: 11:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Wei&Xinwei</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/fileUpload.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/public.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/content.css">
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
                <li><a href="/myAccount">MY ACCOUNT</a></li>
                <li><a href="/logout">LOGOUT</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container text-center">
    <div class="row">
        <div class="col-sm-3 well">
            <div class="well">
                <p><strong><c:out value="${sessionScope.get(\"user\").getDisplayName()}"></c:out></strong></p>
                <img src="${sessionScope.get("user").getImageURL()}" class="img-circle"
                     height="95" width="95">
            </div>
        </div>
        <div class="col-sm-9">

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-default text-left">
                        <div class="panel-body">
                            <form role="form" action="/createNews" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <input type="Text" class="form-control" name="message"
                                           placeholder="Say some thing...">
                                </div>
                                <div class="form-group">
                                    <input type="file" name="files" id="file-1" class="inputfile"
                                           data-multiple-caption="{count} files selected" multiple/>
                                    <label for="file-1" class="btn">
                                        <div class="glyphicon glyphicon-camera"></div>
                                        <span> Pick Images</span></label>
                                </div>

                                <button type="submit" class="btn">Post
                                    <span class="glyphicon glyphicon-ok"></span>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <c:forEach items="${pageContent}" var="current">
                <c:set var="imageSize" scope="page" value="${current.newsContent.images.size()}"/>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="col-sm-3">
                                    <div class="well">
                                        <p><c:out value="${current.email}"></c:out></p>
                                        <img src="https://s3-us-west-2.amazonaws.com/weixinwei/${current.email}/icon.jpg"
                                             class="img-circle" height="65" width="65">
                                    </div>
                                    <c:if test="${current.email.equals(sessionScope.get(\"user\").getUserEmail())}">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <a class="btn btn-block"
                                                   href="/delete/${current.email}/${current.timeStamp}">Delete</a>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="col-sm-9">
                                    <c:if test="${current.newsContent.message!=null&&!current.newsContent.message.equals(\"\")}">
                                        <div class="well">
                                            <p><c:out value="${current.newsContent.message}"/></p>
                                        </div>
                                    </c:if>
                                    <c:if test="${imageSize>0&&imageSize<3}">
                                        <div class="row">

                                            <c:forEach items="${current.newsContent.images}" var="image">
                                                <div class="col-sm-4">
                                                    <div class="thumbnail">
                                                        <img src="${image}"/>
                                                    </div>
                                                </div>
                                            </c:forEach>

                                        </div>
                                    </c:if>
                                    <c:if test="${imageSize>=3}">
                                        <div class="row">

                                            <c:forEach var="i" begin="0" end="2">
                                                <div class="col-sm-4">
                                                    <div class="thumbnail">
                                                        <img src="${current.newsContent.images.get(i)}"/>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="row">
                                            <c:forEach var="i" begin="3" end="${imageSize-1}">
                                                <div class="col-sm-4">
                                                    <div class="thumbnail">
                                                        <img src="${current.newsContent.images.get(i)}"/>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div>
        <ul class="pager">
            <c:if test="${hasPre}">
                <li><a href="/content/previous">Previous</a></li>
            </c:if>
            <c:if test="${hasNext}">
                <li><a href="/content/next">Next</a></li>
            </c:if>
        </ul>
    </div>
</div>

<footer class="container-fluid text-center">
    <p><span class="logo">Wei&Xinwei</span></p>
</footer>
<script src="${pageContext.request.contextPath}/resource/js/fileUpload.js"></script>
</body>
</html>
