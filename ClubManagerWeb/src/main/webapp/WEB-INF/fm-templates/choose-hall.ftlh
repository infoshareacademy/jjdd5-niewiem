<div class="row justify-content-md-center">
<#if halls?has_content>
<#list halls>
        <div class="list-group">
        <#items as h>
            <a href="choose-hall?hallId=${h.id}">
                <div class="cardContainer list-group-item list-group-item-action clearfix">
                    <div class="col-sm">
                    <p class="hallName">${h.name}</p>
                    </div>
                    <div class="col-sm"></div>
                    <div class="col-sm">
                    <#if h.imageURL??>
                        <img class="float-right hallImage" src="${h.imageURL}"/>
                    <#else>
                        <img class="float-right hallImage" src="/default.jpeg"/>
                    </#if>
                    </div>
                </div>
            </a>
        </#items>
        </div>
</#list>
<#else>
<p>No halls. Create new hall.</p>
</#if>

</div>
<div class="row justify-content-md-center">
    <div id="create-new-hall">
        <button class="btn btn-dark" type="button"
                onclick="window.location.href='create-hall'">Create hall
        </button>
    </div>
</div>
<div style="position: fixed; bottom: 0; right: 0">
    <a href="dev-panel">π</a>
</div>
