<div class="row">
    <#list reservations as r>
        <div class="col table-in-hall">
            <p>id: ${r.table.id} name: ${r.table.name} duration: </p>
            <input id="end${r?index}" value="${r.endMillis}" hidden>
            <p id="countdown${r?index}"></p>
        </div>
    </#list>
</div>

<div class="row">
    <a class="btn btn-light btn-lg btn-block" href="/table-new" role="button">Add table</a>
</div>