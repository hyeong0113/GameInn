$(function() {
    $("#gameTitle").keyup(function() {
        var gameInputW = $(gameTitle).outerWidth();
        $(".ui-autocomplete").css("max-width", gameInputW);

        findGames();
    })
});

function findGames() {
    var query = $("#gameTitle").val();
    var url = "/getGames";

    var params = {
        query: query
    };

    $.post(url, params, function(response) {
        var gamesList = [];
        $.each(response, function(index, game) {
            gamesList.push(game)
        });
        $("#gameTitle").autocomplete({
            source: gamesList
        });
    }).fail(function() {
        alert("Get games failed");
    });
}