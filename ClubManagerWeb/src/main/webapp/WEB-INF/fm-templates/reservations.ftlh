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

<div class="row">
    <a class="btn btn-light btn-lg btn-block" href="/reservation" role="button">Add new reservation</a>
</div>

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
                    <td>${r.table.name}</td>
                    <td>${r.startMillis}</td>
                    <td>${r.endMillis}</td>
                    <td>${r.customer ! ""}</td>
                    <td><a href="/reservation?id=${r.id}">EDIT</a></td>
                </tr>
            </#items>
            </tbody>

        </table>
    </#list>

</div>