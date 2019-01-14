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

<#if warnings??>
    <#list warnings>
        <div class="alert alert-warning">
            <ul>
                <#items as warning>
                    <li>${warning}</li>
                </#items>
            </ul>
        </div>
    </#list>
</#if>
<div class="row justify-content-center">
    <div id="create-new-hall">
        <label class="label-white" for="create-hall">Add new reservation</label>
        <button id="create-hall" class="btn btn-dark" type="button"
                onclick="window.location.href='reservation'"><i class="fas fa-plus"></i>
        </button>
    </div>
</div>


<#-- todo: when choosing specific table you should not be able to choose type -->
<#-- todo: you should only be able to use start/end times when using "custom" Period -->

    <div class="">
        <form class="row justify-content-center" method="get" action="/reservations">
            <#list tables>
            <#-- todo: when no option selected should not put any parameter in request, now puts empty one -->
                <div class="form-group">
                    <label class="label-white" form="chooseTable">Select table</label>
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
                    <label class="label-white" form="chooseType">Select table type</label>
                    <select name="type" class="form-control" id="chooseType">
                        <option value="" selected>---</option>
                        <#items as ty>
                            <option value="${ty}">
                                ${ty}
                            </option>
                        </#items>
                    </select>
                </div>
            </#list>
            <#list periods>
            <#-- todo: when selecting custom, start and end time should appear -->
            <#-- todo: when no option selected should not put any parameter in request, now puts empty one -->
                <div class="form-group">
                    <label class="label-white" form="choosePeriod">Select time period</label>
                    <select name="period" class="form-control" id="choosePeriod">
                        <option value="" selected>---</option>
                        <#items as p>
                            <option value="${p}">
                                ${p.text}
                            </option>
                        </#items>
                    </select>
                </div>
            </#list>
    </div>
    <div class="row justify-content-center">
    <#--todo: this should only be visible when CUSTOM period option is chosen-->
        <div class="form-group">
            <label class="label-white" for="startDate">Start date</label>
            <input id="startDate" name="startDate" type="date" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="label-white" for="endDate">End date</label>
            <input id="endDate" name="endDate" type="date" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="label-white" for="startTime">Start time</label>
            <input id="startTime" name="startTime" type="time" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="label-white" for="endTime">End time</label>
            <input id="endTime" name="endTime" type="time" class="form-control"/>
        </div>
    </div>
    <div class="row justify-content-center">
        <button type="submit" class="btn btn-blue">
            Filter
        </button>
        </form>
    </div>


<#if reservations?size == 0>
            <div class="row justify-content-center alert alert-danger">
                <p>No reservations found :(</p>
            </div>
</#if>
<#list reservations>
<div class="row justify-content-center btn-margin-top">
            <table class="table table-dark">

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
                    <td><a href="/reservation?rid=${r.id}">EDIT</a></td>
                </tr>
                </#items>
                </tbody>
            </table>
</div>
</#list>
    </div>