<div class="row">
    <#list tablesEndTimeIneMillis as tetim>
        <div class="col table-in-hall">
        <p>id: ${tetim.table.id} name: ${tetim.table.name} duration: </p>
    <input id="end${tetim?index}" value="${tetim.endTimeInMillis}" hidden>
    <p id="countdown${tetim?index}"></p>
        </div>
    </#list>
</div>
<div class="row">
    <a class="btn btn-light btn-lg btn-block" href="/table-new" role="button">Add table</a>
</div>