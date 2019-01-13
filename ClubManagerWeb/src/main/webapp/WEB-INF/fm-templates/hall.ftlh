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

