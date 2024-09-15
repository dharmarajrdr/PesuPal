import initialState from '../../components/LeftNavigation/ListOfNavigations';

const NavigationReducers = (state = initialState, { payload, type }) => {

    switch (type) {
        case 'UPDATE_NAVIGATION': {
            let { route } = payload;
            route = route || '/' + document.location.pathname.split('/')[1];

            let newState = initialState;
            newState = {
                ...initialState,
                top: newState.top.map((item) => {
                    if (item.route === route) {
                        return { ...item, isActive: true };
                    } else {
                        return { ...item, isActive: false };
                    }
                }),
                bottom: newState.bottom.map((item) => {
                    if (item.route === route) {
                        return { ...item, isActive: true };
                    } else {
                        return { ...item, isActive: false };
                    }
                })
            };

            return newState;
        }
        default:
            return state;
    }
}

export { NavigationReducers };