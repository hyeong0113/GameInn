function display_button(id, sub, content_sub, role) {
    if (sub !== content_sub && role !== "admin") {
        $(id).hide();
    } else {
        $(id).show();
    }
}