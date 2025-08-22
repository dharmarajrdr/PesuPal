import { createSlice } from "@reduxjs/toolkit";

const CurrentOrgSlice = createSlice({
    name: 'currentOrg',
    initialState: {
        publicId: null
    },
    reducers: {
        setCurrentOrgPublicId: (state, action) => {
            state.publicId = action.payload;
        }
    }
});

export const { setCurrentOrgPublicId } = CurrentOrgSlice.actions;
export default CurrentOrgSlice.reducer;