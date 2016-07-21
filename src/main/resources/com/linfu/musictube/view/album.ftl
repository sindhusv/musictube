<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Music tube</title>

    <!-- Bootstrap -->
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet">

    <link href="/assets/css/musictube.css" rel="stylesheet">

    <style>

        body {
            background-color: white;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>
</head>

<body style="background-color: black; color: whitesmoke">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/assets/js/lib/jquery.1.11.3.jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/musictube.js"></script>

<#include "header.ftl">
<#include "listAlbum.ftl">
</body>
</html>