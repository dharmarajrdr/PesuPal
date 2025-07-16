export default {
    "getIconBasedOnCategory": function (category) {
        const icon = {};
        switch (category) {
            case "Document": {
                icon.icon = "fas fa-file-alt"
                icon.icon_color = "#8081ff"
                icon.bg_color = "rgb(32, 33, 165)";
                break;
            }
            case "Video": {
                icon.icon = "fas fa-video"
                icon.icon_color = "#ccbb12"
                icon.bg_color = "rgb(131, 115, 31)";
                break;
            }
            case "Audio": {
                icon.icon = "fas fa-music"
                icon.icon_color = "#fa23ac"
                icon.bg_color = "rgb(128, 21, 89)";
                break;
            }
            case "Image": {
                icon.icon = "fas fa-file-image"
                icon.icon_color = "#42aa1b"
                icon.bg_color = "rgb(32, 92, 9)";
                break;
            }
            case "Folder": {
                icon.icon = "fas fa-folder"
                icon.icon_color = "#ff7a12"
                icon.bg_color = "rgb(32, 92, 9)";
                break;
            }
            case "Recent": {
                icon.icon = "fas fa-history"
                icon.icon_color = "#007bff"
                icon.bg_color = "rgb(32, 33, 165)";
                break;
            }
            case "Trash": {
                icon.icon = "fas fa-trash"
                icon.icon_color = "#ff1524"
                icon.bg_color = "rgb(32, 33, 165)";
                break;
            }
            case "Shared": {
                icon.icon = "fas fa-share-alt"
                icon.icon_color = "#935102"
                icon.bg_color = "rgb(32, 33, 165)";
                break;
            }
            case "Starred": {
                icon.icon = "fas fa-star"
                icon.icon_color = "#fa23ac"
                icon.bg_color = "rgb(32, 33, 165)";
                break;
            }
            case "Home": {
                icon.icon = "fas fa-home"
                icon.icon_color = "#007bff"
                icon.bg_color = "rgb(32, 33, 165)";
                break;
            }
        }
        return icon;
    },
    "getIconForSorting": function (sort) {
        switch (sort) {
            case "ASC": {
                return "fa-solid fa-sort-up";
            }
            case "DESC": {
                return "fa-solid fa-sort-down";
            }
        }
    },
    "getIconForTagWithColor": function (tag) {
        switch (tag) {
            case "Feature": {
                return {
                    "icon": "fas fa-star",
                    "icon_color": "#3a94ac"
                }
            }
            case "Task": {
                return {
                    "icon": "fas fa-tasks",
                    "icon_color": "#4caf50"
                }
            }
            case "Bug": {
                return {
                    "icon": "fas fa-bug",
                    "icon_color": "#f44336"
                }
            }
        }
    },
    "getPriortyColorAndIcon": (priority) => {
        switch (priority) {
            case 'High':
                return { icon_color: 'red', icon: 'fa-solid fa-bolt' }
            case 'Medium':
                return { icon_color: 'orange', icon: 'fa-solid fa-circle-exclamation' }
            case 'Low':
                return { icon_color: 'green', icon: 'fa-regular fa-face-smile' }
            default:
                return { icon_color: 'black', icon: 'fa-solid fa-question' }
        }
    },
    "agoTimeCalculator": (date) => {
        const now = new Date();
        const diff = now - new Date(date);
        const seconds = Math.floor(diff / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);
        const months = Math.floor(days / 30);
        const years = Math.floor(months / 12);
        if (years > 0) return `${years} yr${years > 1 ? 's' : ''} ago`;
        if (months > 0) return `${months} mnt${months > 1 ? 's' : ''} ago`;
        if (days > 0) return `${days} day${days > 1 ? 's' : ''} ago`;
        if (hours > 0) return `${hours} hr${hours > 1 ? 's' : ''} ago`;
        if (minutes > 0) return `${minutes} min${minutes > 1 ? 's' : ''} ago`;
        return `${seconds} sec${seconds > 1 ? 's' : ''} ago`;
    },
    "parseCookie": () => {
        const cookies = document.cookie
            .split(';')
            .map(cookie => cookie.trim().split('='))
            .reduce((acc, [key, value]) => {
                acc[key] = decodeURIComponent(value);
                return acc;
            }, {});

        return {
            get: (name) => cookies[name] || null
        };
    }
}