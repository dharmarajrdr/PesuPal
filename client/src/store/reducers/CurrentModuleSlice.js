import { createSlice } from "@reduxjs/toolkit";

const CurrentModuleSlice = createSlice({
    name: "currentModule",
    initialState: {
        view: null,
        moduleId: null,
        data: {}
    },
    reducers: {
        setCurrentModuleId: (state, action) => {
            return { ...state, 'moduleId': action.payload }
        },
        setCurrentModuleView: (state, action) => {
            return { ...state, 'view': action.payload }
        },
        setCurrentModuleData: (state, action) => {
            return { ...state, 'data': action.payload }
        },
        incrementModuleMemberCount: (state) => {
            return { ...state, 'data': { ...state.data, 'memberCount': (state.data.memberCount || 0) + 1 } }
        },
        resetCurrentModuleView: () => {
            return {
                view: null,
                moduleId: null,
                data: {}
            };
        }
    }
})

export const { setCurrentModuleView, setCurrentModuleId, setCurrentModuleData, incrementModuleMemberCount, resetCurrentModuleView } = CurrentModuleSlice.actions;

export default CurrentModuleSlice.reducer;