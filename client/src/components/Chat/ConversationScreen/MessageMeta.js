import ReadReceipt from "./ReadReceipt"

const formatTime = (iso) => new Date(iso).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

const MessageMeta = ({ createdAt, readReceipt, isCurrentUser }) => {
    return (
        <div className="message-meta FRCE">
            <span className="time">{formatTime(createdAt)}</span>
            {isCurrentUser && <ReadReceipt readReceipt={readReceipt} />}
        </div>
    )
}

export default MessageMeta