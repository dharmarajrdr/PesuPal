import { createSlice } from "@reduxjs/toolkit";

const CurrentModuleSlice = createSlice({
    name: "currentModule",
    initialState: {
        view: null,
        moduleId: null
    },
    reducers: {
        setCurrentModuleId: (state, action) => {
            return { ...state, 'moduleId': action.payload }
        },
        setCurrentModuleView: (state, action) => {
            return { ...state, 'view': action.payload }
        },
        resetCurrentModuleView: () => {
            return {
                view: null,
                moduleId: null
            };
        }
    }
})

export const { setCurrentModuleView, setCurrentModuleId, resetCurrentModuleView } = CurrentModuleSlice.actions;

export default CurrentModuleSlice.reducer;