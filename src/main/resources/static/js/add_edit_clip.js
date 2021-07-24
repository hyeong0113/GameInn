function set_action_url(clip) {
    console.log("check::    " + clip.getId());
    if (clip.getId() === null) {
        return "@{/clips/{sub}/addEdit/add(sub=${user.getSubId()})}";
    } else {
        return "@{/clips/{sub}/addEdit/edit/{id}(sub=${clip.getUser().getSubId()}, id=${clip.getId()})}";
    }
}