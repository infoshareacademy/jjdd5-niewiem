<!doctype html>
<html lang="pl">

<head>
    <meta charset="UTF-8"/>
    <title>Create Hall</title>
    <#--BOOTSTRAP-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
</head>
<body>

<form method="post" action="create-hall" enctype="multipart/form-data">
    Name: <input type="text" name="name"><br/><br/>
    Image: <input type="file" name="image"/><br/><br/>
    <input type="submit" name="save" value="save">
    <input type="hidden" name="action" value="save-hall">
</form>

</body>
</html>