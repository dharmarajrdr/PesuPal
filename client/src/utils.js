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
                icon.icon_color = "#42aa1b"
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
    }
}