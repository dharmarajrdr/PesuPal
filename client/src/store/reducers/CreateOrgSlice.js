import { createSlice } from "@reduxjs/toolkit";

const steps = [
    {
        'displayName': '',
        'uniqueName': '',
        'displayPicture': null
    },
    {
        'displayName': '',
        'userName': '',
        'displayPicture': null
    }
];

const CreateOrgSlice = createSlice({
    'name': 'createOrg',
    'initialState': {
        'steps': steps,
        'currentStep': 0
    },
    'reducers': {
        updateStep: (state, action) => {
            const { step, data } = action.payload;
            state.steps[step] = { ...state.steps[step], ...data };
        },
        setCurrentStep: (state, action) => {
            state.currentStep = action.payload;
        },
        incrementStep: (state) => {
            if (state.currentStep <= state.steps.length - 1) {
                state.currentStep += 1;
            }
        },
        decrementStep: (state) => {
            if (state.currentStep > 0) {
                state.currentStep -= 1;
            }
        },
        resetAllSteps: (state) => {
            state.steps = steps;
            state.currentStep = 0;
        }
    }
});

export const { updateStep, setCurrentStep, incrementStep, decrementStep, resetAllSteps } = CreateOrgSlice.actions;
export default CreateOrgSlice.reducer;