$(function() {
    $("textarea").height($("textarea")[0].scrollHeight);
});

function load_admin_tag(role) {
    if (role == "admin") {
        $("#admin-tag").innerHTML = "ADMIN";
    }
}