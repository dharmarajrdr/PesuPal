import { createSlice } from "@reduxjs/toolkit";

const ModuleFilterSilce = createSlice({
    'name': 'moduleFilter',
    'initialState': {
        'filterBoxShowing': false
    },
    'reducers': {
        'toggleFilterBox': (state, action) => {
            state.filterBoxShowing = !state.filterBoxShowing;
        },
        'closeFilterBox': (state, action) => {
            state.filterBoxShowing = false;
        }
    }
});

export const { toggleFilterBox, closeFilterBox } = ModuleFilterSilce.actions;
export default ModuleFilterSilce.reducer;