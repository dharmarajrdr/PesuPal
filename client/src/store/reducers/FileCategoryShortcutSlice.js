import { createSlice } from "@reduxjs/toolkit";

const FileCategoryShortcutSlice = createSlice({
    'name': 'fileCategoryShortcut',
    'initialState': [
        {
            "id": 1,
            "title": "Image",
            "icon": "fas fa-images",
            "icon_color": "#42aa1b",
            "size": null,
            "count": null
        },
        {
            "id": 2,
            "title": "Video",
            "icon": "fas fa-video",
            "icon_color": "#ff7bff",
            "size": null,
            "count": null
        },
        {
            "id": 3,
            "title": "Audio",
            "icon": "fas fa-music",
            "icon_color": "#fa23ac",
            "size": null,
            "count": null
        },
        {
            "id": 4,
            "title": "Document",
            "icon": "fas fa-file-alt",
            "icon_color": "#8081ff",
            "size": null,
            "count": null
        }
    ],
    reducers: {
        setSizeAndCount: (state, action) => {
            state.forEach(item => {
                if (action.payload[item.title]) {
                    item.size = action.payload[item.title].size;
                    item.count = action.payload[item.title].count;
                } else {
                    item.size = 0;
                    item.count = 0;
                }
            });
            return state;
        }
    }
});

export const { setSizeAndCount } = FileCategoryShortcutSlice.actions;
export default FileCategoryShortcutSlice.reducer;