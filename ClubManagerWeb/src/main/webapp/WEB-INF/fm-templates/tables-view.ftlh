<div class="row justify-content-center">
    <div id="create-new-hall">
        <label class="label-white" for="create-table">Create new table</label>
        <button id="create-table" class="btn btn-dark" type="button"
                onclick="window.location.href='/table'"><i class="fas fa-plus"></i>
        </button>
    </div>
</div>
<#if reservations??>
<#list reservations as r>
    <ul>
        <li class="list-group-item d-flex justify-content-between align-items-center table-in-hall">
                <p class="hallName">${r.table.name}</p>
                <p class="hallName">${r.table.type}</p>
            <div>
                <a href="reservations?tid=${r.table.id}&type=&period=&startDate=&startTime=&endDate=&endTime="><span
                        class="badge badge-brown badge-pill" id="countdown${r?index}"></span></a>
                <input id="end${r?index}" value="${r.endMillis}" hidden>
                <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-blue"
                            onclick="window.location.href='table?tid=${r.table.id}'">Edit</button>
                </div>
            </div>
        </li>
    </ul>
</#list>
<#else>
<p class="label-white">No tables</p>
</#if>
<script>
    runCountdown();
</script>