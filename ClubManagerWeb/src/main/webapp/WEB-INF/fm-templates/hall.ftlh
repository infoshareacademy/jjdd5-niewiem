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

<div class="row justify-content-center">
    <form class="form-width"  method="post" action="/hall?action=<#if hall??>update<#else>new</#if>"
          enctype="multipart/form-data">
        <#if hall??>
            <input type="hidden" name="hid" value="${hall.id}">
        </#if>
        <div class="form-group">
            <label class="label-white" for="hallName">Hall Name</label>
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
                <img class="float-right hallImage" src="${hall.imageURL}"/>
            <#else>
                <p class="label-white">Currently using default picture below:</p>
                <img class="float-right hallImage" src="/default.jpeg"/>
            </#if>
        </#if>
        <div class="form-group">
            <input class="label-white form-control-file" type="file" name="image"/>
        </div>
        <div class="row justify-content-center form-group">
            <button type="submit" class="btn btn-blue">Save</button>
        </div>
            <#if hall??>
        <div class="row btn-margin-top justify-content-center">
            <form method="post" action="/hall?action=delete">
                <input type="hidden" name="hid" value="${hall.id}">
                <button type="submit" class="btn btn-danger">Delete hall</button>
            </form>
        </div>
            </#if>
    </form>
</div>