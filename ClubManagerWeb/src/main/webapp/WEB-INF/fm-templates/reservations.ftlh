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
    <div class="col-5">
        <#-- todo: when choosing specific table you should not be able to choose type -->
        <#-- todo: you should only be able to use start/end times when using "custom" period -->
        <form method="get" action="/reservations">
            <button type="submit" class="btn btn-primary">Filter</button>
            <#list tables>
            <#-- todo: when no option selected should not put any parameter in request, now puts empty one -->
                <div class="form-group">
                    <label form="chooseTable">Select table</label>
                    <select name="tid" class="form-control" id="chooseTable">
                        <option value="" selected>---</option>
                        <#items as t>
                            <option value="${t.id}">
                                ${t.name}
                            </option>
                        </#items>
                    </select>
                </div>
            </#list>
            <#list types>
            <#-- todo: when no option selected should not put any parameter in request, now puts empty one -->
                <div class="form-group">
                    <label form="chooseType">Select table type</label>
                    <select name="type" class="form-control" id="chooseTable">
                        <option value="" selected>---</option>
                        <#items as ty>
                            <option value="${ty}">
                                ${ty}
                            </option>
                        </#items>
                    </select>
                </div>
            </#list>
        </form>
    </div>
    <div class="col-7">

        <div class="row">
            <a class="btn btn-light btn-lg btn-block" href="/reservation" role="button">Add new reservation</a>
        </div>

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
</div>