<form method="post" action="/table-new">

    <div class="form-group">
        <input id="tableName" name="name" type="text" class="form-control"
               placeholder="Table name" autofocus required/>
    </div>

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

    <button type="submit" class="btn btn-primary">Save!</button>

</form>
