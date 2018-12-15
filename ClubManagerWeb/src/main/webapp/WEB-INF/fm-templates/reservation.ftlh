<div>
    <#if reservation??>
        <p>Not yet available</p>
    <#-- SHOULD HAVE TWO BUTTONS UPDATE AND DELETE -->

    <#else>
        <form method="post" action="/reservation?action=new">

        <#list tables>
            <div class="form-group">

            <label form="chooseTable">Choose table</label>
            <select name="tid" class="form-control" id="chooseTable">
            <#items as t>
                <option value="${t.id}">${t.name}</option>
            </#items>
            </select>

            </div>
        </#list>

    <#-- todo: Date input should be done by eg. datepicker in bootsrap. Solution below is just for now -->
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
    <#-- todo: delete the stuff above me when you implement the right stuff -->

        <div class="form-group">
            <label for="customerInfo">Customer</label>
            <input id="customerInfo" name="customer" type="text" class="form-control"
                   placeholder="Your name and phone number" required/>
        </div>

        <button type="submit" class="btn btn-primary">Save!</button>
        </form>
    </#if>
</div>