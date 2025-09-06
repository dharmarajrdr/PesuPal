import { createSlice } from "@reduxjs/toolkit";

const OrgRolePermissionsSlice = createSlice({
    name: 'orgRolePermissions',
    initialState: {
        roles: ["Super Admin", "Member", "Dummy"],
        permissions: []
    },
    reducers: {
        setPermissions: (state, action) => {
            state.permissions = action.payload;
        },
        setRoles: (state, action) => {
            state.roles = action.payload;
        },
        addNewRole: (state, action) => {
            state.roles.push(action.payload);
        }
    }
})

export const { setPermissions, setRoles, addNewRole } = OrgRolePermissionsSlice.actions;
export default OrgRolePermissionsSlice.reducer;