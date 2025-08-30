import { createSlice } from '@reduxjs/toolkit';

const VerticalLoaderSlice = createSlice({
    'name': 'VerticalLoader',
    'initialState': {
        'isLoading': false,
    },
    'reducers': {
        'showLoader': (state) => {
            state.isLoading = true;
        },
        'hideLoader': (state) => {
            state.isLoading = false;
        }
    }
})

export const { showLoader, hideLoader } = VerticalLoaderSlice.actions;
export default VerticalLoaderSlice.reducer;