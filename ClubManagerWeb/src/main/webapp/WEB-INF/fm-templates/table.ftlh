<#if errors??>
    <#list errors>
        <div class="alert alert-danger">
            <ul>
                <#items as error>
                    <li>${error}</li>
                </#items>
            </ul>
        </div>
    </#list>
</#if>


<#if table??>
    <div class="row">
        <form method="post" action="/table?action=delete">
            <input type="hidden" name="tid" value="${table.id}">
            <button type="submit" class="btn btn-danger">Delete table</button>
        </form>
    </div>
</#if>

<#if table??>
    <div class="row">
        <form method="post" action="/table?action=update-name">
            <input type="hidden" name="tid" value="${table.id}">

            <div class="form-group">
                <label for="tableName">Table Name</label>
                <input id="tableName" name="name" type="text" class="form-control" value="${table.name}" required/>
            </div>
            <div>
                <p>${table.type}</p>
            </div>
            <button type="submit" class="btn btn-danger">Change name</button>
        </form>
    </div>
</#if>