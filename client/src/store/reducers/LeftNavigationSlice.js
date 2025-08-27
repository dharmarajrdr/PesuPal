import { createSlice } from "@reduxjs/toolkit";

const LeftNavigationSlice = createSlice({
    'name': 'showLeftNavigation',
    'initialState': {
        'showLeftNavigation': true
    },
    'reducers': {
        showLeftNavigation: (state, action) => {
            state.showLeftNavigation = action.payload;
        }
    }
})

export const { showLeftNavigation } = LeftNavigationSlice.actions;
export default LeftNavigationSlice.reducer;