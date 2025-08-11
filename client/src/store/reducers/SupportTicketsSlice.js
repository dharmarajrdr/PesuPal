import { createSlice } from "@reduxjs/toolkit";

const SupportTicketsSlice = createSlice({
    'name': 'supportTickets',
    'initialState': {
        'tickets': [],
        'selectedTicket': null
    },
    'reducers': {
        'setTickets': (state, action) => {
            state.tickets = action.payload;
        },
        'setSelectedTicket': (state, action) => {
            state.selectedTicket = action.payload;
        },
        'addNewTicket': (state, action) => {
            state.tickets = [action.payload, ...state.tickets];
        },
    }
});

export const { setTickets, setSelectedTicket, addNewTicket } = SupportTicketsSlice.actions;

export default SupportTicketsSlice.reducer;