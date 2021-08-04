function getId(url) {
    const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
    const match = url.match(regExp);

    return (match && match[2].length === 11) ?
        match[2] :
        null;
}

function display_diff_time(posted_time) {
    var seconds = Math.floor((new Date() - new Date(posted_time)) / 1000);

    var interval = seconds / 31536000;

    if (interval > 1) {
        return formatDate(posted_time);
    }
    interval = seconds / 2592000;
    if (interval > 1) {
        return formatDate(posted_time);
    }
    interval = seconds / 86400;
    if (interval > 1) {
        return formatDate(posted_time);
    }
    interval = seconds / 3600;
    if (interval > 1) {
        return Math.floor(interval) + "h";
    }
    interval = seconds / 60;
    if (interval > 1) {
        return Math.floor(interval) + "m";
    }
    return "few seconds ago";
}

function formatDate(time) {
    var d = new Date(time),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('.');
}