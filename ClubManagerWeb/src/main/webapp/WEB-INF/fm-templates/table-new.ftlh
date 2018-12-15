<form method="post" action="table-new">
    <form method="post" action="/table-new">
        <#--HUBERT, you fuck - Delete below when you implement hid in session -->
            <div class="form-group">
                <label for="hallId">Id</label>
                <input id="hallId" name="hid" type="number" class="form-control"/>
            </div>
        <#--HUBERT, you fuck - Delete above when you implement hid in session, make it type=hidden -->
            <div class="form-group">
                <label for="tableName">Name</label>
                <input id="tableName" name="name" type="text" class="form-control"/>
            </div>
        <#list types as t>
            <div class="form-check">
            <input name="type" value="${t}" class="form-check-input" type="radio" id="${t}"/>
        <label class="form-check-label" for="${t}">
            ${t}
            </label>
            </div>
        </#list>
        <button type="submit" class="btn btn-primary">Save!</button>
    </form>