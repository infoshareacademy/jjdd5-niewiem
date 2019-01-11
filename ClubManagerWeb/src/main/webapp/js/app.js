let count = document.getElementsByClassName('table-in-hall').length;

function countdown(id) {
    let str = document.getElementById("end" + id).value;
    let strRpl = str
        .replace(new RegExp(String.fromCharCode(160), "g"), "")
        .replace(new RegExp(String.fromCharCode(44), "g"), "");
    let countDownDate = new Date(parseInt(strRpl, 10));
    let x = setInterval(function () {
        let now = new Date().getTime();
        let distance = countDownDate - now;
        document.getElementById("countdown" + id).innerHTML = millisToDateTime(distance);
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("countdown" + id).innerHTML = "FREE";
        }
    }, 1000);
}

for (i = 0; i < count; i++) {
    countdown(i);
}
$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});
(function () {
    function id(v) {
        return document.getElementById(v);
    }

    function loadbar() {
        var ovrl = id("overlay"),
            img = document.images,
            c = 0,
            tot = img.length;
        if (tot == 0) return doneLoading();

        function imgLoaded() {
            c += 1;
            if (c === tot) return doneLoading();
        }

        function doneLoading() {
            ovrl.style.opacity = 0;
            setTimeout(function () {
                ovrl.style.display = "none";
            }, 0);
        }

        for (var i = 0; i < tot; i++) {
            var tImg = new Image();
            tImg.onload = imgLoaded;
            tImg.onerror = imgLoaded;
            tImg.src = img[i].src;
        }
    }

    document.addEventListener('DOMContentLoaded', loadbar, false);
}());

function millisToDateTime(millis) {

    let days = Math.floor(millis / (1000 * 60 * 60 * 24));
    let hours = Math.floor((millis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((millis % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((millis % (1000 * 60)) / 1000);
    return days + "d "
        + hours + "h "
        + minutes + "m "
        + seconds + "s ";
}

function getCurrentTime() {
    let date = new Date();
    let hours = date.getHours();
    let minutes = date.getMinutes();
    if (hours < 10) {
        hours = '0' + hours;
    }
    if (minutes < 10) {
        minutes = '0' + minutes;
    }
    return hours + ':' + minutes;
}

document.getElementById('startTime').value = getCurrentTime();

function getCurrentDate() {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = date.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    return year + '-' + month + '-' + day;
}

document.getElementById('startDate').value = getCurrentDate();