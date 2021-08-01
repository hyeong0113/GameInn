function load_login_page() {
    setTimeout(function() {
        window.location.href = "/oauth2/authorization/auth0";
    });
    document.getElementById("not-authenticated").innerHTML = "You need to login first!";
}

function load_main_page(sub) {
    if (sub) {
        var path = "/clips/" + sub
        setTimeout(function() {
            window.location.href = path;
        }, 3000);
    } else {
        setTimeout(function() {
            window.location.href = "/clips";
        }, 3000);
    }
}

function load_admin_message(role) {
    if (role == "admin") {
        $("#admin-text").innerHTML = "You are logined as Administrator";
    }
}

function load_admin_tag(role) {
    if (role == "admin") {
        $("#admin-tag").innerHTML = "ADMIN";
    }
}