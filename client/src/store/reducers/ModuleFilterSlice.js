import { createSlice } from "@reduxjs/toolkit";

const ModuleFilterSilce = createSlice({
    'name': 'moduleFilter',
    'initialState': {
        'filterBoxShowing': sessionStorage.getItem('filterBoxShowing') === 'true'
    },
    'reducers': {
        'toggleFilterBox': (state, action) => {
            state.filterBoxShowing = !state.filterBoxShowing;
            sessionStorage.setItem('filterBoxShowing', state.filterBoxShowing);
        },
        'showFilterBox': (state, action) => {
            state.filterBoxShowing = true;
            sessionStorage.setItem('filterBoxShowing', true);
        },
        'closeFilterBox': (state, action) => {
            state.filterBoxShowing = false;
            sessionStorage.setItem('filterBoxShowing', false);
        }
    }
});

export const { toggleFilterBox, showFilterBox, closeFilterBox } = ModuleFilterSilce.actions;
export default ModuleFilterSilce.reducer;