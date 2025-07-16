// src/redux/reducers/loaderReducer.js
import { SHOW_LOADER, HIDE_LOADER } from '../actions/VerticalLoader';

const initialState = {
    isLoading: false,
};

const VerticalLoaderReducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return {
                ...state,
                isLoading: true,
            };
        case HIDE_LOADER:
            return {
                ...state,
                isLoading: false,
            };
        default:
            return state;
    }
};

export { VerticalLoaderReducer };