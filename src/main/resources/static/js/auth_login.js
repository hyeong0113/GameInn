function load_login_page() {
    setTimeout(function() {
        window.location.href = "/oauth2/authorization/auth0";
    });
    document.getElementById("not-authenticated").innerHTML = "You need to login first!";
}

function load_main_page(sub) {
    setTimeout(function() {
        window.location.href = "/main/" + sub;
    }, 3000);
}

function load_admin_message(role) {
    if (role == "admin") {
        document.getElementById("admin-text").innerHTML = "You are logined as Administrator";
    }
}

function load_admin_tag(role) {
    if (role == "admin") {
        document.getElementById("admin-tag").innerHTML = "ADMIN";
    }
}