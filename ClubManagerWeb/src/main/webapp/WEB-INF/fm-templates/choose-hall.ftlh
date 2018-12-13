<div class="container">
    <#list halls>
        <div class="row">
        <#items as h>
            <a href="tables-view?hallId=${h.id}">
                <div class="col">
                    <#if h.imageURL??>
                        <img src="${h.imageURL}"/>
                    <#else>
                        <img src="/default.jpeg"/>
                    </#if>
                    <p>${h.name}</p>
                </div>
            </a>
        </#items>
        </div>
    </#list>

    <div class="row">
        <div id="create-new-hall">
            <a href="create-hall">Create new hall</a>
        </div>
    </div>

    <#if savedSuccess??>
        <div class="row">
            <div class="alert alert-success">
                <p>Saved ${savedHall} in database</p>
            </div>
        </div>
    </#if>

    <div style="position: fixed; bottom: 0; right: 0">
        <a href="dev-panel">π</a>
    </div>

</div>
