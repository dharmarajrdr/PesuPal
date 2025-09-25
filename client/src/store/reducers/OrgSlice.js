import { createSlice } from "@reduxjs/toolkit";

const OrgSlice = createSlice({
    name: 'org',
    initialState: {
        currentOrgId: null,
        showingOrgList: false
    },
    reducers: {
        setCurrentOrgId: (state, action) => {
            state.currentOrgId = action.payload;
        },
        showOrgList: (state) => {
            state.showingOrgList = true;
        },
        hideOrgList: (state) => {
            state.showingOrgList = false;
        }
    }
});

export const { setCurrentOrgId, showOrgList, hideOrgList } = OrgSlice.actions;
export default OrgSlice.reducer;