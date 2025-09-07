import { createSlice } from "@reduxjs/toolkit";

const OrgRolePermissionsSlice = createSlice({
    name: 'orgRolePermissions',
    initialState: {
        roles: [],
        permissions: [],
        hasPrivilegeToCreateOrgRole: false
    },
    reducers: {
        setPermissions: (state, action) => {
            state.permissions = action.payload;
        },
        setOrgRoles: (state, action) => {
            state.roles = action.payload;
        },
        updateOrgRole: (state, action) => {
            const { roleId } = action.payload;
            state.roles = state.roles.map(role => role.roleId === roleId ? action.payload : role).sort((a, b) => a.name.localeCompare(b.name));
        },
        deleteOrgRole: (state, action) => {
            const roleId = action.payload;
            state.roles = state.roles.filter(role => role.roleId !== roleId);
        },
        addNewRole: (state, action) => {
            // sort roles by name after adding new role
            state.roles.push(action.payload);
            state.roles.sort((a, b) => a.name.localeCompare(b.name));
        },
        setHasPrivilegeToCreateOrgRole: (state, action) => {
            state.hasPrivilegeToCreateOrgRole = action.payload;
        }
    }
})

export const { setPermissions, setOrgRoles, addNewRole, updateOrgRole, deleteOrgRole, setHasPrivilegeToCreateOrgRole } = OrgRolePermissionsSlice.actions;
export default OrgRolePermissionsSlice.reducer;