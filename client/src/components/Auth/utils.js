export const hasCookie = () => {
    return sessionStorage.getItem('token') !== null || utils.parseCookie().get('token') !== null;
}, StatusIndicator = ({ status, style }) => {
    let color = '#aaa', icon = 'fa-circle';
    switch (status) {
        case 'available':
            color = 'green';
            icon = 'fa-circle';
            break;
        case 'offline':
            color = '#aaa';
            icon = 'fa-circle';
            break;
        case 'call':
            color = 'red';
            icon = 'fa-phone';
            break;
    }
    return <i className={`fa-solid ${icon} user_status`} style={{ ...style, color, backgroundColor: '#fff', 'borderRadius': '50%', padding: '5px' }}></i>;
}
