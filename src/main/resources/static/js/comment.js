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

    switch (month) {
        case '1':
            month = 'Jan'
            break;
        case '2':
            month = 'Feb'
            break;
        case '3':
            month = 'Mar'
            break;
        case '4':
            month = 'Apr'
            break;
        case '5':
            month = 'May'
            break;
        case '6':
            month = 'Jun'
        case '7':
            month = 'Jul'
            break;
        case '8':
            month = 'Aug'
            break;
        case '9':
            month = 'Sep'
            break;
        case '10':
            month = 'Oct'
            break;
        case '11':
            month = 'Nov'
            break;
        case '12':
            month = 'Dec'
            break;
    }

    return month + " " + day + ", " + year;
}