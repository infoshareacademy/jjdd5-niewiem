<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Club Manager</title>
    <link rel="icon" href="/icon.png">
    <link href='https://fonts.googleapis.com/css?family=B612 Mono' rel='stylesheet'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.0/animate.min.css">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="/css/styles.css" rel="stylesheet">
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-131299466-1"></script>

    <script>
        window.dataLayer = window.dataLayer || [];

        function gtag() {
            dataLayer.push(arguments);
        }

        gtag('js', new Date());

        gtag('config', 'UA-131299466-1');
    </script>

    <!-- Google -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/client.js" async defer></script>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id"
          content="750662202912-ou3jtpmon6qaoagpfithrqmvpgj2i76o.apps.googleusercontent.com">
    <script src="js/app.js"></script>
</head>
<body class="">
<div class="background animated fadeIn">
    <nav class="navbar navbar-expand-sm navbar-dark sticky-top">
        <a class="navbar-brand zoomLink" href="/">Club Manager</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample05"
                aria-controls="navbarsExample05" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExample05">
            <ul class="navbar-nav ml-auto">
                <#if activeHall??>
                <li class="nav-item nav-hall-name">
                    <h1>${activeHall.getName()}</h1>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tables-view">Tables</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/reservations">Reservations</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin-panel">Admin panel</a>
                </li>
                </#if>
                <li class="nav-item">
                    <#if userAuthenticated?? >
                        <a class="nav-link" href="/logout">Log out</a>
                    <#else>
                            <a class="nav-link" href="/login">Log in</a>
                    </#if>
                </li>
                <#if imageUrl??>
                <li>
                    <div class="inset" data-toggle="tooltip" title="${name}">
                        <img src="${imageUrl}">
                </li>
                <#else>
                <li>
                    <div class="inset" data-toggle="tooltip" title="Anonymous">
                        <img src="https://static.thenounproject.com/png/82455-200.png">
                    </div>
                </li>
                </#if>
            </ul>
        </div>

    </nav>
    <div class="main">
        <div class="container">
            <div class="content center">
            <#include bodyTemplate>
            </div>
        </div>
    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/popper.min.js"></script>
</body>
</html>