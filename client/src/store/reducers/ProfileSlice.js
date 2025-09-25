import { createSlice } from "@reduxjs/toolkit";

const profileSlice = createSlice({
    'name': 'profile',
    'initialState': null,
    'reducers': {
        'showProfile': (state, action) => {
            return action.payload;
        },
        'hideProfile': () => {
            return null;
        }
    }
});

export const { showProfile, hideProfile } = profileSlice.actions;
export default profileSlice.reducer;