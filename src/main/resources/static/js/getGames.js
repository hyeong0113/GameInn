$("#getGames").keyup(function() {
    $("#games").empty();
    findGames();
})

function findGames() {
    var query = $("#getGames").val();
    var url = "/getGames";

    var params = {
        query: query
    };

    $.post(url, params, function(response) {
        // var gamesDropdown = $("#games");
        var gamesList = [];
        $.each(response, function(index, game) {
            // $("<option>").val(game).text(game).appendTo(gamesDropdown);
            gamesList.push(game)
        });
        $("#getGames").autocomplete({
            source: gamesList
        });
    }).fail(function() {
        alert("Get games failed");
    });
}