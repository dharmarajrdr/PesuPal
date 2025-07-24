import utils from "../../../utils";
import ReadReceipt from "./ReadReceipt"

const reactionsList = {
    "LIKE": {
        "icon": "fa-thumbs-up",
        "color": "#00ac00ff"
    },
    "DISLIKE": {
        "icon": "fa-thumbs-down",
        "color": "#ff6c6cff"
    },
    "LOVE": {
        "icon": "fa-heart",
        "color": "#ff65ffff"
    },
    "FUNNY": {
        "icon": "fa-laugh",
        "color": "#b2a000ff"
    },
    "ANGRY": {
        "icon": "fa-angry",
        "color": "#ff4000ff"
    }
};

const Reactions = ({ reactions }) => {
    return (
        <div className="message-reacted FRCC">
            {Object.entries(reactions).map(([name, count]) => {
                const reaction = reactionsList[name];
                return (
                    <p key={name} className="reaction">
                        <i className={`fa ${reaction.icon}`} style={{ color: reaction.color }} title={name} />
                        {count > 0 && <span className="count">{count}</span>}
                    </p>
                );
            })}
        </div>
    );
}

const MessageMeta = ({ createdAt, readReceipt, isCurrentUser, reactions }) => {
    return (
        <div className="message-meta FRCB">
            <Reactions reactions={reactions} />
            <div className="FRCE">
                <span className="time">{utils.convertTime(createdAt, 12)}</span>
                {isCurrentUser && <ReadReceipt readReceipt={readReceipt} />}
            </div>
        </div>
    )
}

export default MessageMeta