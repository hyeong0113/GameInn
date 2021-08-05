function display_button(id, sub, content_sub, role, edit_id) {
    if (sub !== content_sub && role !== "admin") {
        $(id).hide();
    } else {
        $(id).show();
        if (sub !== content_sub) {
            $(edit_id).hide();
        }
    }
}