import utils from "../../../utils";
import ReadReceipt from "./ReadReceipt"

const MessageMeta = ({ createdAt, readReceipt, isCurrentUser }) => {
    return (
        <div className="message-meta FRCE">
            <span className="time">{utils.convertTime(createdAt, 12)}</span>
            {isCurrentUser && <ReadReceipt readReceipt={readReceipt} />}
        </div>
    )
}

export default MessageMeta