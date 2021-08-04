function display_alert(show_alert) {
    if (show_alert) {
        $('#alert-display').show();
        window.setTimeout(function() {
            $("#alert-display").alert('close');
        }, 3000);
    } else {
        $('#alert-display').hide();
    }
}