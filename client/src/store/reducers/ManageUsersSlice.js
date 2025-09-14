import { createSlice } from "@reduxjs/toolkit";

const ManageUsersSlice = createSlice({
    name: 'manageUsers',
    initialState: {
        title: 'all-members',
        members: []
    },
    reducers: {
        setManageUserTitle: (state, action) => {
            state.title = action.payload;
        },
        setUsers: (state, action) => {
            state.members = action.payload;
        },
        deleteUserById: (state, action) => {
            state.members = state.members.filter(user => user.id !== action.payload);
        },
        deleteUserByInvitationId: (state, action) => {
            state.members = state.members.filter(user => user.invitationId !== action.payload);
        },
        prependUser: (state, action) => {
            state.members.unshift(action.payload);
        }
    }
});

export const { setManageUserTitle, setUsers, deleteUserById, deleteUserByInvitationId, prependUser } = ManageUsersSlice.actions;
export default ManageUsersSlice.reducer;