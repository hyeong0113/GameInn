$(function() {
    $("textarea").height($("textarea")[0].scrollHeight);

    hideEditButtons();

    $("#editProfile").click(function() {
        showEditButtons();
    });

    $("#cancel").click(function() {
        hideEditButtons();
    });
});

function showEditButtons() {
    $("#editProfile").hide();
    $("#saveProfile").show();
    $("#cancel").show();

    $(".social").hide();
    $(".socialInput").show();
}

function hideEditButtons() {
    $("#editProfile").show();
    $("#saveProfile").hide();
    $("#cancel").hide();

    $(".social").show();
    $(".socialInput").hide();
}

function load_admin_tag(role) {
    if (role == "admin") {
        $("#admin-tag").innerHTML = "ADMIN";
    }
}