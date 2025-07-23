import { createSlice } from "@reduxjs/toolkit";

const PopupSlice = createSlice({
    'name': 'popup',
    'initialState': null,
    'reducers': {
        showPopup: (state, action) => {
            return action.payload;
        },
        hidePopup: () => {
            return null;
        }
    }
})

export const { showPopup, hidePopup } = PopupSlice.actions;
export default PopupSlice.reducer;