function countdown(id) {
    let str = document.getElementById("end" + id).value;
    let strRpl = str
        .replace(new RegExp(String.fromCharCode(160), "g"), "")
        .replace(new RegExp(String.fromCharCode(44), "g"), "");
    let countDownDate = new Date(parseInt(strRpl, 10));
    let x = setInterval(function () {
        let now = new Date().getTime();
        let distance = countDownDate - now;
        document.getElementById("countdown" + id).innerHTML = timeLeft(distance);
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("countdown" + id).innerHTML = "FREE";
        }
    }, 1000);
}

function runCountdown() {
    let count = document.getElementsByClassName('table-in-hall').length;
    for (i = 0; i < count; i++) {
        countdown(i);
    }
}

$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

function timeLeft(millis) {

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

function getCurrentDate() {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    return year + '-' + month + '-' + day;
}

function millisToDateTime(millisStr) {
    console.log(millisStr);
    let millis = millisStr
        .replace(new RegExp(String.fromCharCode(160), "g"), "")
        .replace(new RegExp(String.fromCharCode(44), "g"), "");
    let parsedMillis = new Date(parseInt(millis, 10));
    let minutes = new Date(parsedMillis).getMinutes();
    let hours = new Date(parsedMillis).getHours();
    let day = new Date(parsedMillis).getDate();
    let month = new Date(parsedMillis).getMonth() + 1;
    let year = new Date(parsedMillis).getFullYear();
    return `${hours}:${minutes} ${day}.${month}.${year}`
}

function replaceMillis(classToReplace, countElements) {
    let count = document.getElementsByClassName(countElements).length;
    for (let i = 0; i < count; i++) {
        let startMillis = document.getElementById('replace-start-millis' + i).value;
        let endMillis = document.getElementById('replace-end-millis' + i).value;
        console.log(classToReplace + i);
        document.getElementById('replace-start-millis' + i).value = millisToDateTime(startMillis);
        document.getElementById('replace-end-millis' + i).value = millisToDateTime(endMillis);
    }
}