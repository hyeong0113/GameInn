$(function() {
    $("#linkError").hide();

    //  Bind the event handler to the "submit" JavaScript event
    $('form').submit(function() {

        // Get the Login Name value and trim it
        var url = new URL($.trim($("#inputlink").val()));
        var platform = $.trim($("#platform").val())

        // Check url
        switch (platform) {
            case "YOUTUBE":
                var host = url.hostname;

                if (host != "youtu.be" && host != "www.youtube.com") {
                    $("#linkError").show();
                    return false;
                }

                break;

            case "TWITTER":
                var host = url.hostname;
                var path = url.pathname.split("/");

                if (host != "twitter.com" || path[2] != "status") {
                    $("#linkError").show();
                    return false;
                }

                break;

            case "REDDIT":
                var host = url.hostname;
                var path = url.pathname.split("/");

                if (host != "www.reddit.com" || path[3] != "comments") {
                    $("#linkError").show();
                    return false;
                }

                break;

            case "TWITCH":
                var host = url.hostname;
                var path = url.pathname.split("/");

                if (host != "www.twitch.tv" || (path.length == 3 && path[1] != "videos") || (path.length == 4 && path[2] != "clip")) {
                    $("#linkError").show();
                    return false;
                }

                break;
        }
    });
});