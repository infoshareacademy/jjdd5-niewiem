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

<div class="row">
    <form method="post" action="/table?action=<#if table??>update-name<#else>new</#if>">

        <#if table??>
            <input type="hidden" name="tid" value="${table.id}">
        </#if>

        <div class="form-group">
            <label for="tableName">Table Name</label>
            <input id="tableName" name="name" type="text" class="form-control"
                    <#if table??>
                        value="${table.name}"
                    <#else>
                        placeholder="Table name" autofocus
                    </#if>
                   required/>
        </div>

        <div>
            <#if table??>
                <p>${table.type}</p>
            <#else>
                <#list types as t>
                    <div class="form-check">
                        <input name="type" value="${t}" class="form-check-input" type="radio" id="${t}" required
                                <#if t=="POOL">
                                    checked="checked"
                                </#if>
                        />
                        <label class="form-check-label" for="${t}">
                            ${t}
                        </label>
                    </div>
                </#list>
            </#if>
        </div>

        <#if table??>
            <button type="submit" class="btn btn-primary">Change name</button>
        <#else>
            <button type="submit" class="btn btn-primary">Save</button>
        </#if>

    </form>
</div>
