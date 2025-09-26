import { createSlice } from "@reduxjs/toolkit";

const CurrentModuleSlice = createSlice({
    name: "currentModule",
    initialState: {
        view: null,
        moduleId: null,
        data: {},
        records: []
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
        setModuleRecords: (state, action) => {
            return { ...state, 'records': action.payload }
        },
        addModuleRecord: (state, action) => {
            return { ...state, 'records': [...state.records, action.payload] }
        },
        removeModuleRecord: (state, action) => {
            return { ...state, 'records': state.records.filter(record => record.id !== action.payload.id) }
        },
        resetCurrentModuleView: () => {
            return {
                view: null,
                moduleId: null,
                data: {},
                records: []
            };
        }
    }
})

export const { setCurrentModuleView, setCurrentModuleId, setCurrentModuleData, incrementModuleMemberCount, decrementModuleMemberCount, updateModuleData, setModuleRecords, addModuleRecord, removeModuleRecord, resetCurrentModuleView } = CurrentModuleSlice.actions;

export default CurrentModuleSlice.reducer;