<div class="row">
    <#list reservations>
        <table class="table table-bordered table-hover table-striped">

        <thead>
        <tr>
            <th>Table</th>
            <th>Start</th>
            <th>End</th>
            <th>Customer</th>
            <th>Edit</th>
        </tr>
        </thead>

        <tbody>
        <#items as r>
            <tr>
            <td>${r.table.name}</th>
            <td>${r.startTime}</td>
            <td>${r.endTime}</td>
            <td>${r.customer ! ""}</td>
            <td><a href="/reservation?id=${r.id}">EDIT</a></td>
            </tr>
        </#items>
        </tbody>

        </table>
    </#list>

</div>