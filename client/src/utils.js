export default {
    "getIconBasedOnCategory": function (category) {
        const icon = {};
        switch (category) {
            case "Document": {
                icon.icon = "fas fa-file-alt"
                icon.icon_color = "#ff7bff"
                break;
            }
            case "Video": {
                icon.icon = "fas fa-video"
                icon.icon_color = "#ff7bff"
                break;
            }
            case "Audio": {
                icon.icon = "fas fa-music"
                icon.icon_color = "#ff7bff"
                break;
            }
            case "Image": {
                icon.icon = "fas fa-file-image"
                icon.icon_color = "#ff7bff"
                break;
            }
            case "Folder": {
                icon.icon = "fas fa-folder"
                icon.icon_color = "#8081ff"
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
    }
}