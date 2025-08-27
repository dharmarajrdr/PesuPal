import { createSlice } from "@reduxjs/toolkit";

const MyProfileSlice = createSlice({
    'name': 'myProfile',
    'initialState': null,
    'reducers': {
        'setMyProfile': (state, action) => {
            return action.payload;
        },
        'clearMyProfile': (state) => {
            return null;
        }
    }
})

export const { setMyProfile, clearMyProfile } = MyProfileSlice.actions;

export default MyProfileSlice.reducer;