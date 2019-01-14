<h2 class="label-white">Users list</h2>
<br>

<div class="list-group">
<#list users as u>
    <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
        <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">${u.userName}</h5>
        </div>
        <p class="mb-1">${u.userEmail}</p>
    </a>
</#list>
</div>
<br>
<a href="javascript:history.back();">Back to Admin panel</a>