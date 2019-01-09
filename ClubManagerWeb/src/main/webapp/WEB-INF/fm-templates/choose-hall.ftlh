<div class="row justify-content-md-center col-6 col-m">
<#if halls?has_content>
<#list halls>
        <div class="list-group">
        <#items as h>
            <a href="choose-hall?hallId=${h.id}">
                <div class="cardContainer list-group-item list-group-item-action clearfix">
                    <p class="hallName">${h.name}</p>
                    <#if h.imageURL??>
                        <img class="float-right hallImage" src="${h.imageURL}"/>
                    <#else>
                        <img class="float-right hallImage" src="/default.jpeg"/>
                    </#if>
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
        <button class="btn btn-outline-dark" type="button"
                onclick="window.location.href='create-hall'">Create hall
        </button>
    </div>
</div>
<div style="position: fixed; bottom: 0; right: 0">
    <a href="dev-panel">π</a>
</div>
