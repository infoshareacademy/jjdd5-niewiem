<h2>Users list</h2>
<br>
<#list users as u>
        <ul>
            <li>${u.userName} - ${u.userEmail}</li>
        </ul>
</#list>
<a href="javascript:history.back();">Back to Admin panel</a>