import { createSlice } from "@reduxjs/toolkit";

const DepartmentSlice = createSlice({
    name: 'department',
    initialState: {
        currentDepartment: {
            id: null,
            name: null,
            head: null,
            members: []
        },
        departments: []
    },
    reducers: {
        setCurrentDepartmentId: (state, action) => {
            state.currentDepartment.id = action.payload;
        },
        setCurrentDepartmentName: (state, action) => {
            state.currentDepartment.name = action.payload;
        },
        setCurrentDepartmentHead: (state, action) => {
            state.currentDepartment.head = action.payload;
        },
        setCurrentDepartmentMembers: (state, action) => {
            state.currentDepartment.members = action.payload;
        },
        setDepartments: (state, action) => {
            state.departments = action.payload;
        },
        updateMemberStatus: (state, action) => {
            const { userId, status } = action.payload;
            const memberIndex = state.currentDepartment.members.findIndex(member => member.userId === userId);
            if (memberIndex !== -1) {
                state.currentDepartment.members[memberIndex].status = status;
            }
        }
    }
});

export const { setCurrentDepartmentId, setCurrentDepartmentName, setCurrentDepartmentHead, setDepartments, setCurrentDepartmentMembers, updateMemberStatus } = DepartmentSlice.actions;
export default DepartmentSlice.reducer;
