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
    <form class="form-width" method="post" action="/table?action=<#if table??>update-name<#else>new</#if>">
        <#if table??>
            <input type="hidden" name="tid" value="${table.id}">
        </#if>
        <div class="form-group">
            <label class="label-white" for="tableName">Table Name</label>
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
                <label class="label-white" for="tableType">Table type</label>
                <input id="tableType" type="text" class="form-control" value="${table.type}" disabled/>
            <#else>
                <#list types as t>
                    <div class="form-check">
                        <input name="type" value="${t}" class="form-check-input" type="radio" id="${t}" required
                                <#if t=="POOL">
                                    checked="checked"
                                </#if>
                        />
                        <label class="label-white form-check-label" for="${t}">
                            ${t}
                        </label>
                    </div>
                </#list>
            </#if>
        </div>
        <div class="row justify-content-center">
        <#if table??>
            <button type="submit" class="btn-margin-top btn btn-blue">Update</button>
        <#else>
            <button type="submit" class="btn-margin-top btn btn-blue">Save</button>
        </#if>
        </div>
    </form>
</div>
<#if table??>
       <div class="row justify-content-center">
           <form method="post" action="/table?action=delete">
               <input type="hidden" name="tid" value="${table.id}">
               <button type="submit" class="btn-margin-top btn btn-danger">Delete</button>
           </form>
       </div>
</#if>


<#if table??>
    <#if isActive>
    <div class="row justify-content-center">
        <form method="post" action="/table?action=stopGame">
            <input type="hidden" name="tid" value="${table.id}">
            <button type="submit" class="btn-margin-top btn btn-secondary">Stop current game</button>
        </form>
    </div>
    <#else>
        <div class="row justify-content-center">
            <form class="form-width" method="post" action="/table?action=startGame">
                <input type="hidden" name="tid" value="${table.id}">
                <div class="form-group">
                    <label class="label-white btn-margin-top" for="timeSpan">Time span in minutes</label>
                    <input id="timeSpan" name="timeSpan" type="number" class="form-control" value="60" required/>
                </div>
                <div class="row justify-content-center">
                    <button type="submit" class="btn btn-secondary">Start new game</button>
                </div>
            </form>
        </div>
    </#if>
</#if>
<#if table??>
    <div class="row justify-content-center">
        <button class="btn btn-light btn-margin-top" role="button"
                onclick="window.location.href='/reservation?tid=${table.id}'">
            Add new reservation
        </button>
    </div>
    <div class="row justify-content-center">
        <button class="btn btn-light btn-margin-top" role="button"
                onclick="window.location.href='/reservations?tid=${table.id}'">
            See all reservations
        </button>
    </div>
</#if>