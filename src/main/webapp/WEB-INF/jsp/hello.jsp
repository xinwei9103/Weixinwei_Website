<html>

<head>
    <title>Wei&Xinwei</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/welcome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/public.css">
    <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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
            <a class="navbar-brand logo" href="#">Wei&Xinwei</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="signUp">SIGN UP</a></li>
                <li><a data-toggle="modal" data-target="#myModal">LOGIN</a></li>
            </ul>
        </div>
    </div>
</nav>

<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img src="${pageContext.request.contextPath}/resource/image/IMG_3092.JPG">
            <div class="carousel-caption">
                <h3>I love you</h3>
                <p>I love you</p>
            </div>
        </div>

        <div class="item">
            <img src="${pageContext.request.contextPath}/resource/image/IMG_3235.JPG">
            <div class="carousel-caption">
                <h3>I love you</h3>
                <p>I love you</p>
            </div>
        </div>

        <div class="item">
            <img src="${pageContext.request.contextPath}/resource/image/IMG_3793.JPG">
            <div class="carousel-caption">
                <h3>I love you</h3>
                <p>I love you</p>
            </div>
        </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<div class="jumbotron text-center">
    <p><span class="logo">Wei&Xinwei</span></p>
    <p><em>I Love You!</em></p>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4>Login</h4>
            </div>
            <div class="modal-body">
                <form role="form" action="/login" method="post">
                    <div class="form-group">
                        <label><span class="glyphicon glyphicon-user"></span> UserName</label>
                        <input type="text" class="form-control" name="email" placeholder="UserName">
                    </div>
                    <div class="form-group">
                        <label><span class="glyphicon glyphicon-lock"></span> Password</label>
                        <input type="password" class="form-control" name="psw" placeholder="Password">
                    </div>

                    <button type="submit" class="btn btn-block">Login
                        <span class="glyphicon glyphicon-ok"></span>
                    </button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-danger btn-default pull-right" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span> Cancel
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
