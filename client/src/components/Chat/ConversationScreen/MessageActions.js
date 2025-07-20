const reactionsList = ['LIKE', 'LOVE', 'FUNNY', 'ANGRY', 'DISLIKE'];

const MessageActions = () => {
    return (
        <div className="actions">
            <i className='fa fa-trash delete-icon' title="Delete" />
            <div className="reactions">
                {reactionsList.map((reaction) => (
                    <i key={reaction} className={`fa fa-smile reaction-icon`} title={reaction} />
                ))}
            </div>
        </div>
    )
}

export default MessageActions