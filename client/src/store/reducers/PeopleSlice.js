import { createSlice } from "@reduxjs/toolkit";

const PeopleSlice = createSlice({
    name: 'people',
    initialState: {
        people: [],
        searchUser: ''
    },
    reducers: {
        setPeople: (state, action) => {
            state.people = action.payload;
        },
        setSearchUser: (state, action) => {
            state.searchUser = action.payload;
        },
        updateMemberStatusInPeople: (state, action) => {
            const { userId, status } = action.payload;
            const memberIndex = state.people.findIndex(member => member.id === userId);
            if (memberIndex !== -1) {
                state.people[memberIndex].status = status;
            }
        }
    }
});

export const { setPeople, setSearchUser, updateMemberStatusInPeople } = PeopleSlice.actions;
export default PeopleSlice.reducer;
