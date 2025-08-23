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
        decrementModuleMemberCount: (state) => {
            return { ...state, 'data': { ...state.data, 'memberCount': (state.data.memberCount || 0) - 1 } }
        },
        updateModuleData: (state, action) => {
            return { ...state, 'data': { ...state.data, ...action.payload } }
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

export const { setCurrentModuleView, setCurrentModuleId, setCurrentModuleData, incrementModuleMemberCount, decrementModuleMemberCount, updateModuleData, resetCurrentModuleView } = CurrentModuleSlice.actions;

export default CurrentModuleSlice.reducer;