import { createSlice } from "@reduxjs/toolkit";

const ConfirmationPopupSlice = createSlice({
    'name': 'confirmationPopup',
    'initialState': {
        'popupData': {}
    },
    'reducers': {
        'showConfirmationPopup': (state, action) => {
            state.popupData = action.payload;
        },
        'hideConfirmationPopup': (state) => {
            state.popupData = {};
        }
    }
})

export const { showConfirmationPopup, hideConfirmationPopup } = ConfirmationPopupSlice.actions;

export default ConfirmationPopupSlice.reducer;