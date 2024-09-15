export const setActiveNavigation = ({
    route
}) => {
    return {
        type: 'UPDATE_NAVIGATION',
        payload: { route }
    }
}