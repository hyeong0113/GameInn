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
        var gamesDropdown = $("#games");
        $.each(response, function(index, game) {
            $("<option>").val(game).text(game).appendTo(gamesDropdown);
        });
    }).fail(function() {
        alert("Get games failed");
    });
}