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
    <div id="create-new-hall">
        <label class="label-white" for="create-hall">Create new hall</label>
        <button id="create-hall" class="btn btn-dark" type="button"
                onclick="window.location.href='hall'"><i class="fas fa-plus"></i>
        </button>
    </div>
</div>
<div class="row justify-content-center">
    <#if halls?has_content>
        <#list halls>
            <div class="form form-width list-group">
                <#items as h>
                    <a href="choose-hall?hid=${h.id}">
                        <div class="btn-margin-top cardContainer list-group-item list-group-item-action clearfix">
                            <div class="col-sm">
                                <p class="hallName">${h.name}</p>
                            </div>
                            <div class="col-sm"></div>
                            <div class="col-sm padding-zero">
                                <#if h.imageURL??>
                                    <img class="float-right hallImage" src="${h.imageURL}"/>
                                <#else>
                                    <img class="float-right hallImage" src="/default.jpeg"/>
                                </#if>
                            </div>
                        </div>
                    </a>
                    <a class="btn btn-blue btn-lg btn-block" href="hall?hid=${h.id}" role="button">
                        EDIT
                    </a>
                </#items>
            </div>
        </#list>
    <#else>
    <div>
        <p id="no-halls-text">No halls.</p>
    </div>
    </#if>
</div>
<div style="position: fixed; bottom: 0; right: 0">
    <a href="dev-panel">π</a>
</div>
