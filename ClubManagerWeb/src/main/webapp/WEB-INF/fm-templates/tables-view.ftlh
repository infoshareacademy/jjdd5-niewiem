<ul>
<#list reservations as r>
    <li class="list-group-item d-flex justify-content-between align-items-center table-in-hall">
        <p>${r.table.name}</p>
        <div>
            <a href="reservations?tid=${r.table.id}&type=&period=&startDate=&startTime=&endDate=&endTime="><span
                    class="badge badge-primary badge-pill" id="countdown${r?index}"></span></a>
            <input id="end${r?index}" value="${r.endMillis}" hidden>
            <div class="btn-group" role="group" aria-label="Basic example">

                <button type="button" class="btn btn-secondary">Show</button>
                <button type="button" class="btn btn-secondary">Edit</button>
            </div>
        </div>
    </li>
</#list>
</ul>

<div class="row">
    <a class="btn btn-light btn-lg btn-block" href="/table-new" role="button">Add table</a>
</div>