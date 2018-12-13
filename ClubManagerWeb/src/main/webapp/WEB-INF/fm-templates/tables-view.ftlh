<!doctype html>
<html lang="pl">

<head>
    <meta charset="UTF-8"/>
    <title>Choose Hall</title>
    <#--BOOTSTRAP-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
</head>

<body>

<div class="row">
    <#list tablesEndTimeIneMillis as tetim>
        <div class="col table-in-hall">
        <p>id: ${tetim.table.id} name: ${tetim.table.name} duration: </p>
            <input id="end" value="${tetim.endTimeInMillis}">
        <p id="demo"></p>
        </div>

    </#list>
</div>

</div>

<#--BOOTSTRAP-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</body>
</html>


<script>
   var count = document.getElementsByTagName('table');

    // Set the date we're counting down to
   var str = document.getElementById("end").value;
    var countDownDate = new Date(Number(str.trim().replace(',', '')));

            // new Date("Jan 5, 2019 15:37:25").getTime();

    // Update the count down every 1 second
    var x = setInterval(function() {
        console.log(countDownDate, str);
        // Get todays date and time
        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Display the result in the element with id="demo"
        document.getElementById("demo").innerHTML = days + "d " + hours + "h "
                + minutes + "m " + seconds + "s ";

        // If the count down is finished, write some text
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("demo").innerHTML = "EXPIRED";
        }
    }, 1000);
</script>
