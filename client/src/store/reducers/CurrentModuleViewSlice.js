import { createSlice } from "@reduxjs/toolkit";

const CurrentModuleViewSlice = createSlice({
    name: "currentModuleView",
    initialState: null,
    reducers: {
        setCurrentModuleView: (state, action) => {
            return action.payload;
        },
        resetCurrentModuleView: () => {
            return null;
        }
    }
})

export const { setCurrentModuleView, resetCurrentModuleView } = CurrentModuleViewSlice.actions;

export default CurrentModuleViewSlice.reducer;