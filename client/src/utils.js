export default {
    "serverDomain": 'http://localhost:8080',
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
    "futureTimeCalculator": (date) => {
        const now = new Date();
        const diff = new Date(date) - now;
        const seconds = Math.floor(diff / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);
        const months = Math.floor(days / 30);
        const years = Math.floor(months / 12);
        if (years > 0) return `${years} yr${years > 1 ? 's' : ''} to go`;
        if (months > 0) return `${months} mnt${months > 1 ? 's' : ''} to go`;
        if (days > 0) return `${days} day${days > 1 ? 's' : ''} to go`;
        if (hours > 0) return `${hours} hr${hours > 1 ? 's' : ''} to go`;
        if (minutes > 0) return `${minutes} min${minutes > 1 ? 's' : ''} to go`;
        return `${seconds} sec${seconds > 1 ? 's' : ''} to go`;
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
    },
    "convertDateAndTime": (str) => {
        try {
            const toTwoDigits = function (str) {
                str = str + '';
                return str.length == 2 ? str : '0' + str;
            }
            let d = new Date(str);
            if (d) {
                let hours = d.getHours();
                let am_pm = hours >= 12 ? 'PM' : 'AM';
                hours = hours > 12 ? hours - 12 : hours;
                return toTwoDigits(d.getDate()) + "/" + toTwoDigits(d.getMonth() + 1) + "/" + d.getFullYear() + " " + toTwoDigits(hours) + ":" + toTwoDigits(d.getMinutes()) + " " + am_pm;
            }
        } catch (error) {
            console.error({ error, str });  //eslint-disable-line no-console
        }
    },
    "convertTime": (str, hoursMode) => {
        try {
            const toTwoDigits = function (str) {
                str = str + '';
                return str.length == 2 ? str : '0' + str;
            }
            let d = new Date(str);
            if (d) {
                let hours = d.getHours();
                let am_pm = hours >= 12 ? 'PM' : 'AM';
                if (hoursMode === 12) {
                    hours = hours > 12 ? hours - 12 : hours;
                    return toTwoDigits(hours || 12) + ":" + toTwoDigits(d.getMinutes()) + " " + am_pm;
                } else {
                    return toTwoDigits(d.getHours()) + ":" + toTwoDigits(d.getMinutes());
                }
            }
        } catch (error) {
            console.error({ error, str });  //eslint-disable-line no-console
        }
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
    },
    "getCurrentOrgId": () => {

        return sessionStorage.getItem('org-id');
    },
    "uniqueColorGenerator": (name) => {

        const colors = [
            '#7f8c8d', '#2ecc71', '#3498db', '#9b59b6',
            '#e67e22', '#e74c3c', '#34495e', '#f39c12',
            '#16a085', '#8e44ad', '#2980b9', '#d35400',
            '#c0392b', '#1abc9c', '#27ae60', '#d2aa0cff',
            '#95a5a6', '#bdc3c7', '#5dade2', '#a569bd',
        ];

        let hash = 0;
        for (let i = 0; i < name.length; i++) {
            hash = name.charCodeAt(i) + ((hash << 5) - hash);
        }

        const index = Math.abs(hash) % colors.length;
        return colors[index];
    },
    "formatCurrency": (amount, currency) => {
        if (typeof amount !== 'number') return '';
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: currency || 'USD',
        }).format(amount);
    },
    "getIconByFileExtension": (extension) => {

        const icons = {
            'folder': { icon: 'fa-folder', color: '#a88f00ff' },
            'pdf': { icon: 'fa-file-pdf', color: '#FF0000' },
            'doc': { icon: 'fa-file-word', color: '#2B579A' },
            'docx': { icon: 'fa-file-word', color: '#2B579A' },
            'xls': { icon: 'fa-file-excel', color: '#217346' },
            'xlsx': { icon: 'fa-file-excel', color: '#217346' },
            'ppt': { icon: 'fa-file-powerpoint', color: '#D24726' },
            'pptx': { icon: 'fa-file-powerpoint', color: '#D24726' },
            'txt': { icon: 'fa-file-alt', color: '#808080' },
            'jpg': { icon: 'fa-file-image', color: '#FFB74D' },
            'jpeg': { icon: 'fa-file-image', color: '#FFB74D' },
            'png': { icon: 'fa-file-image', color: '#4FC3F7' },
            'gif': { icon: 'fa-file-image', color: '#9C27B0' },
            'svg': { icon: 'fa-file-image', color: '#FF9800' },
            'zip': { icon: 'fa-file-archive', color: '#FF9800' },
            'mp3': { icon: 'fa-file-audio', color: '#1DB954' },
            'wav': { icon: 'fa-file-audio', color: '#1DB954' },
            'mp4': { icon: 'fa-file-video', color: '#1976D2' },
            'avi': { icon: 'fa-file-video', color: '#1976D2' },
            'mov': { icon: 'fa-file-video', color: '#1976D2' },
            'mkv': { icon: 'fa-file-video', color: '#1976D2' },
            'json': { icon: 'fa-file-code', color: '#F4A261' },
            'html': { icon: 'fa-file-code', color: '#E34F26' },
            'css': { icon: 'fa-file-code', color: '#1572B6' },
            'js': { icon: 'fa-brands fa-js', color: '#c0a915ff' },
            'ts': { icon: 'fa-brands fa-js', color: '#3178C6' },
            'xml': { icon: 'fa-file-code', color: '#FF6600' },
            'csv': { icon: 'fa-file-csv', color: '#4CAF50' },
            'tsv': { icon: 'fa-file-csv', color: '#388E3C' },
            'md': { icon: 'fa-file-alt', color: '#000000' },
            'yaml': { icon: 'fa-file-code', color: '#FBC02D' },
            'yml': { icon: 'fa-file-code', color: '#FBC02D' },
            'java': { icon: 'fa-brands fa-java', color: '#b35d07ff' },
            'py': { icon: 'fa-brands fa-python', color: '#3776AB' },
            'c': { icon: 'fa-brands fa-cuttlefish', color: '#A8B9CC' },
            'cpp': { icon: 'fa-brands fa-cuttlefish', color: '#00599C' },
            'go': { icon: 'fa-brands fa-go', color: '#00ADD8' },
            'rs': { icon: 'fa-brands fa-rust', color: '#000000' },
            'php': { icon: 'fa-brands fa-php', color: '#8892BF' },
            'swift': { icon: 'fa-brands fa-swift', color: '#F05138' }
        }

        return icons[extension.toLowerCase()] || { icon: 'fa-file', color: '#808080' };
    },
    "getFileIcon": (file) => {

        const fileTypeIcons = {
            image: "fa fa-image",
            video: "fa fa-video",
            audio: "fa fa-music",
            pdf: "fa fa-file-pdf",
            doc: "fa fa-file-word",
            docx: "fa fa-file-word",
            xls: "fa fa-file-excel",
            xlsx: "fa fa-file-excel",
            ppt: "fa fa-file-powerpoint",
            pptx: "fa fa-file-powerpoint",
            default: "fa fa-file"
        };

        if (file.type.startsWith("image")) return fileTypeIcons.image;
        if (file.type.startsWith("video")) return fileTypeIcons.video;
        if (file.type.startsWith("audio")) return fileTypeIcons.audio;
        if (file.type.includes("pdf")) return fileTypeIcons.pdf;
        if (
            file.type.includes("word") ||
            file.name.endsWith(".doc") ||
            file.name.endsWith(".docx")
        )
            return fileTypeIcons.doc;
        if (
            file.type.includes("excel") ||
            file.name.endsWith(".xls") ||
            file.name.endsWith(".xlsx")
        )
            return fileTypeIcons.xls;
        if (
            file.type.includes("powerpoint") ||
            file.name.endsWith(".ppt") ||
            file.name.endsWith(".pptx")
        )
            return fileTypeIcons.ppt;
        return fileTypeIcons.default;
    },
    "formatFileSize": (bytes) => {
        if (bytes === 0) return '0 Bytes';
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        const i = Math.floor(Math.log(bytes) / Math.log(1024));
        const size = bytes / Math.pow(1024, i);
        return `${size.toFixed(2)} ${sizes[i]}`;
    }
}