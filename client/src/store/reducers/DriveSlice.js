import { createSlice } from "@reduxjs/toolkit";

const DriveSlice = createSlice({
    'name': 'drive',
    'initialState': {
        'space': null,
        'folderId': null,
        'items': [],
        'parents': []
    },
    'reducers': {
        'setSpace': (state, action) => {
            state.space = action.payload;
        },
        'setFolderId': (state, action) => {
            state.folderId = action.payload;
        },
        'setItems': (state, action) => {
            state.items = action.payload;
        },
        'addItem': (state, action) => {
            state.items.push(action.payload);
            state.items.sort((a, b) => a.name.localeCompare(b.name));
        },
        'removeItem': (state, action) => {
            state.items = state.items.filter(item => item.id !== action.payload.id);
        },
        'setParents': (state, action) => {
            state.parents = action.payload || [];
        }
    }
});

export const { setSpace, setFolderId, setItems, addItem, removeItem, setParents } = DriveSlice.actions;
export default DriveSlice.reducer;