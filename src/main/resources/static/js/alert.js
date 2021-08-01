function display_alert(show_alert) {
    if (show_alert) {
        $('#unauthorized-delete').show();
        window.setTimeout(function() {
            $("#unauthorized-delete").alert('close');
        }, 3000);
    } else {
        $('#unauthorized-delete').hide();
    }
}