$(function() {
    $("textarea").each(function(textarea) {
        $(this).height($(this)[0].scrollHeight);
    });
});