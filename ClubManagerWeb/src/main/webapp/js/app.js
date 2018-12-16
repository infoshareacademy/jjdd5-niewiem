let count = document.getElementsByClassName('table-in-hall').length;

function countdown(id) {
    let str = document.getElementById("end" + id).value;
    let strRpl = str.replace(new RegExp(String.fromCharCode(160), "g"), "");
    let countDownDate = new Date(parseInt(strRpl, 10));
    let x = setInterval(function () {
        let now = new Date().getTime();
        let distance = countDownDate - now;
        let days = Math.floor(distance / (1000 * 60 * 60 * 24));
        let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((distance % (1000 * 60)) / 1000);
        document.getElementById("countdown" + id).innerHTML = days + "d " + hours + "h "
            + minutes + "m " + seconds + "s ";
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("countdown" + id).innerHTML = "FREE";
        }
    }, 1000);
}

for (i = 0; i < count; i++) {
    countdown(i);
}
