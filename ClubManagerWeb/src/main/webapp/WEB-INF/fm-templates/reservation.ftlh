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


<div class="row justify-content-center">
    <form class="reservation-edit-content" method="post" action="/reservation?action=<#if reservation??>update<#else>new</#if>">
        <#if reservation??>
            <input type="hidden" name="rid" value="${reservation.id}">
        </#if>

        <#list tables>
            <div class="form-group">

                <label class="label-white" form="chooseTable">Choose table</label>
                <select name="tid" class="form-control" id="chooseTable">
                    <#items as t>
                        <option value="${t.id}"
                                <#if reservation?? && t.id == reservation.table.id ><#elseif tid?? && tid == t.id>selected="selected"</#if>>
                            ${t.name}
                        </option>
                    </#items>
                </select>

            </div>
        </#list>

        <#-- todo: Date and time span should be filled automatically -->
        <div class="form-group">
            <label class="label-white" for="startDate">Start date</label>
            <input id="startDate" name="startDate" type="date" class="form-control" required/>
        </div>
        <div class="form-group">
            <label class="label-white" for="startTime">Start time</label>
            <input id="startTime" name="startTime" type="time" class="form-control" required/>
        </div>
        <div class="form-group">
            <label class="label-white" for="timeSpan">Time span in minutes</label>
            <#--todo: 'value' field below should also be filled automatically-->
            <input id="timeSpan" name="timeSpan" type="number" class="form-control" value="60" required/>
        </div>

        <div class="form-group">
            <label class="label-white" for="customerInfo">Customer</label>
            <input id="customerInfo" name="customer" type="text" class="form-control"
                    <#if reservation??>
                        value="${reservation.customer}"
                    <#else>
                        placeholder="Your name and phone number"
                    </#if>
                   required/>
        </div>
    <div class="row button-row btn-group" role="group">
        <button type="submit" class="btn btn-blue">Save</button>
        <#if reservation??>

        <form method="post" action="/reservation?action=delete">
            <input type="hidden" name="rid" value="${reservation.id}">
            <button type="submit" class="btn btn-danger">Delete</button>
        </form>
        </#if>
    </div>
    </form>
</div>
<script>
    document.getElementById('startTime').value = getCurrentTime();
    document.getElementById('startDate').value = getCurrentDate();
</script>