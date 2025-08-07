// src/redux/actions/loaderActions.js
export const SHOW_LOADER = 'SHOW_LOADER';
export const HIDE_LOADER = 'HIDE_LOADER';

export const showLoader = () => ({
    type: SHOW_LOADER,
});

export const hideLoader = () => ({
    type: HIDE_LOADER,
});