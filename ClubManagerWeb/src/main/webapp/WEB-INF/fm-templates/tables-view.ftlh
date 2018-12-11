<!doctype html>
<html lang="pl">

<head>
    <meta charset="UTF-8"/>
    <title>Choose Hall</title>
    <#--BOOTSTRAP-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
</head>

<body>
<div class="container">
    <div class="row">
        <h1>Welcome to ${hall.name}</h1>
    </div>

    <div class="row">
        <#list hallTables as t>
            <div class="col table-in-hall">
                <p>id: ${t.id} name: ${t.name} </p>
            </div>
        </#list>
    </div>
</div>
</div>

<#--BOOTSTRAP-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</body>
</html>