function set_action_url(clip) {
    if (clip.getId() === null) {
        return "@{/clips/{sub}/addEdit/add(sub=${user.getSubId()})}";
    } else {
        return "@{/clips/{sub}/addEdit/edit/{id}(sub=${clip.getUser().getSubId()}, id=${clip.getId()})}";
    }
}