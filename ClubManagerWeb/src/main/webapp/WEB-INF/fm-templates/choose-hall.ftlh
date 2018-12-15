<div class="container">
    <#list halls>
        <div class="row">
        <#items as h>
            <a href="choose-hall?hallId=${h.id}">
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

    <div style="position: fixed; bottom: 0; right: 0">
        <a href="dev-panel">π</a>
    </div>

</div>
