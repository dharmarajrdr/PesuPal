export const hasCookie = () => {
    return document.cookie.split(';').some((item) => item.trim().startsWith('token='));
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
    return <i class={`fa-solid ${icon} user_status`} style={{ ...style, color, backgroundColor: '#fff', 'borderRadius': '50%', padding: '5px' }}></i>;
}
