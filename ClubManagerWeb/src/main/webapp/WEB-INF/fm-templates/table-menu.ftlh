<!doctype html>

<html>
<head>
    <title>table menu</title>
</head>
<body>
<h1>TABLE MENU</h1>
<form method="post" action="/table-menu">
    <input type="hidden" name="tid" value="${table.id}"/><br/><br/>
    Name: <input type="text" name="name" value="${table.name}"/><br/><br/>
    <input type="submit" name="save" value="save"/><br/><br/>
</form>
</body>
</html>