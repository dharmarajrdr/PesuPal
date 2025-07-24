const reactionsList = [
    {
        "name": 'LIKE',
        "icon": 'fa-thumbs-up',
        "color": '#00ac00ff'
    },
    {
        "name": 'DISLIKE',
        "icon": 'fa-thumbs-down',
        "color": '#ff6c6cff'
    },
    {
        "name": 'LOVE',
        "icon": 'fa-heart',
        "color": '#ff65ffff'
    },
    {
        "name": 'FUNNY',
        "icon": 'fa-laugh',
        "color": '#b2a000ff'
    },
    {
        "name": 'ANGRY',
        "icon": 'fa-angry',
        "color": '#ff4000ff'
    }
];

const MessageActions = ({ isCurrentUser }) => {
    return (
        <div className={`message-actions FRCC ${isCurrentUser ? 'sent' : 'received'}`}>
            {isCurrentUser && <i className='fa fa-trash delete-icon' style={{ color: '#ff6c6cff' }} title="Delete" />}
            <div className="reactions">
                {reactionsList.map(({ name, icon, color }) => (
                    <i key={name} className={`fa ${icon} reaction-icon`} style={{ color }} title={name} />
                ))}
            </div>
        </div>
    )
}

export default MessageActions