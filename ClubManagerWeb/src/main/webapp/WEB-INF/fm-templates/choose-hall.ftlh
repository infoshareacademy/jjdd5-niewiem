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
    <#list halls>
        <div class="row">
        <#items as h>
            <div class="col">
            <a href="tables-view?hallId=${h.id}">${h.name}</a>
            </div>
        </#items>
        </div>
    </#list>

        <div class="row">
            <div id="create-new-hall">
                <a href="create-hall">Create new hall</a>
            </div>
        </div>

    <#if savedSuccess??>
        <div class="row">
        <div class="alert alert-success">
        <p>Saved ${savedHall} in database</p>
        </div>
        </div>
    </#if>

    <div style="position: fixed; bottom: 0; right: 0">
        <a href="dev-panel">π</a>
    </div>

</div>
<#--BOOTSTRAP-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</body>
</html>