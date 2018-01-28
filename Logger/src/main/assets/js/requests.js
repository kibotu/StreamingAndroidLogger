'use strict';
var requestUtil = function () {

    function getData(path, callbackSuccess, callbackFail) {
        $.ajax({
            dataType: "json",
            url: path,
            success: function (result) {
                    callbackSuccess(result);
            },
            error: function (e) {
                callbackFail();
            }
        });
    }

    return {
        getData: getData
    }
}();
