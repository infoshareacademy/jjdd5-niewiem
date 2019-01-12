<form method="post" action="create-hall" enctype="multipart/form-data">
    <div class="form-group">
        <input type="text" class="form-control" id="inputName" placeholder="Enter name" name="name" autofocus required>
    </div>
    <input class="form-control-file" type="file" name="image"/>
    <button class="btn" type="submit" name="save" value="save">Save</button>
    <input type="hidden" name="action" value="save-hall">
</form>