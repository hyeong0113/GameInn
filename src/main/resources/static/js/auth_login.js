function load_login_page() {
    setTimeout(function() {
        window.location.href = "/oauth2/authorization/auth0";
    });
    document.getElementById("not_authenticated").innerHTML = "You need to login first!";
}