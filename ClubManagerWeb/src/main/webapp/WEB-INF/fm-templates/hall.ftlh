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

<div class="row">
    <form method="post" action="/hall?action=<#if hall??>update<#else>new</#if>" enctype="multipart/form-data">

        <#if hall??>
            <input type="hidden" name="hid" value="${hall.id}">
        </#if>

        <div class="form-group">
            <label for="hallName">Hall Name</label>
            <input id="hallName" name="name" type="text" class="form-control"
                    <#if hall??>
                        value="${hall.name}"
                    <#else>
                        placeholder="Hall name" autofocus
                    </#if>
                   required/>
        </div>

        <#if hall??>
            <#if hall.imageURL??>
                <img class="float-right hallImage" src="${h.imageURL}"/>
            <#else>
                <p>Currently using default picture below:</p>
                <img class="float-right hallImage" src="/default.jpeg"/>
            </#if>
        </#if>

        <input class="form-control-file" type="file" name="image"/>

        <button type="submit" class="btn btn-primary">Save</button>

    </form>
</div>