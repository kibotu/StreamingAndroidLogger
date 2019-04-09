'use strict';
$(document).ready(function () {
    setInterval(refresh, interval)
});

var interval = 1000;

function refresh() {

    requestUtil.getData("messages.json", function (data) {
        refreshView(data);
    }, function () {
        console.log("[getData] onError ");
    });
}

function refreshView(messages) {

    for (var message in messages) {
         $(".container").prepend(messages[message].time + ": " + messages[message].message +" <br/>");
    }
}