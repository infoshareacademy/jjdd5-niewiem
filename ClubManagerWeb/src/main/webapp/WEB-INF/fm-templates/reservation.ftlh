<#if reservation??>
    <div class="row">
    <form method="post" action="/reservation?action=delete">
    <input type="hidden" name="rid" value="${reservation.id}">
    <button type="submit" class="btn btn-danger">Delete reservation!</button>
    </form>
    </div>
</#if>
<div class="row">
    <form method="post" action="/reservation?action=<#if reservation??>update<#else>new</#if>">
        <#if reservation??>
            <input type="hidden" name="rid" value="${reservation.id}">
        </#if>

        <#list tables>
            <div class="form-group">

            <label form="chooseTable">Choose table</label>
            <select name="tid" class="form-control" id="chooseTable">
            <#items as t>
                <option value="${t.id}" <#if reservation?? && t.id == reservation.table.id >selected="selected"</#if>>
                ${t.name}
                </option>
            </#items>
            </select>

            </div>
        </#list>

        <#-- todo: Date and time span should be filled automatically -->
            <div class="form-group">
                <label for="startDate">Start date</label>
                <input id="startDate" name="startDate" type="date" class="form-control" required/>
            </div>
            <div class="form-group">
                <label for="startTime">Start time</label>
                <input id="startTime" name="startTime" type="time" class="form-control" required/>
            </div>
            <div class="form-group">
                <label for="timeSpan">Time span in minutes</label>
                <input id="timeSpan" name="timeSpan" type="number" class="form-control" value="60" required/>
            </div>

        <div class="form-group">
            <label for="customerInfo">Customer</label>
            <input id="customerInfo" name="customer" type="text" class="form-control"
                    <#if reservation??>
                        value="${reservation.customer}"
                    <#else>
                        placeholder="Your name and phone number"
                    </#if>
                   required/>
        </div>

        <button type="submit" class="btn btn-primary">Save!</button>
    </form>
</div>
<script>
    document.getElementById('startTime').value = getCurrentTime();
    document.getElementById('startDate').value = getCurrentDate();
</script>