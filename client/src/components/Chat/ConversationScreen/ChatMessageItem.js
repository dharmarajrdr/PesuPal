import MessageDeleted from './MessageDeleted';
import Message from './Message';
import MessageActions from './MessageActions';
import MessageMeta from './MessageMeta';
import { useSelector } from 'react-redux';

const extensionTagMapper = {
    "img": ["jpeg", "jpg", "png", "gif", "avif"],
    "video": ["mp4", "webm", "ogg"],
    "audio": ["mp3", "wav", "ogg"],
    "doc": ["pdf", "doc", "docx", "txt"],
    "zip": ["zip", "rar", "tar", "gz"]
}

const ImageDisplayer = ({ media }) => {

    const { mediaUrl } = media || {};

    return mediaUrl ? <div className="message-media">
        <img src={mediaUrl} alt="Media" className="media-image cursP" />
    </div> : null;
}

const MediaDisplayer = ({ media }) => {

    if (!media) return null;

    const { id, extension, mediaUrl } = media || {};

    for (const [tag, extensions] of Object.entries(extensionTagMapper)) {
        if (extensions.includes(extension)) {

            switch (tag) {
                case 'img':
                    return <ImageDisplayer media={media} />;
                case 'video':
                    return <div className="message-media">
                        <video src={mediaUrl} controls className="media-video cursP" />
                    </div>;
                case 'audio':
                    return <div className="message-media">
                        <audio src={mediaUrl} controls className="media-audio cursP" />
                    </div>;
                case 'doc':
                    return <div className="message-media">
                        <a href={mediaUrl} target="_blank" rel="noopener noreferrer" className="media-doc cursP">Document</a>
                    </div>;
                case 'zip':
                    return <div className="message-media">
                        <a href={mediaUrl} target="_blank" rel="noopener noreferrer" className="media-zip cursP">Download Zip</a>
                    </div>;
            }
        }
    }

    return <div className='p20 w100 alignCenter'>
        <span className='colorAAA fs12 w100'>
            <i className="fa fa-triangle-exclamation mR5 colorAAA" /> Unsupported media type</span>
    </div>
}

const ChatMessageItem = ({ msg }) => {

    const { sender, deleted, createdAt, readReceipt, message, media } = msg;

    const myProfile = useSelector(state => state.myProfile);

    const isCurrentUser = sender.id == myProfile?.id;

    return myProfile ? (
        <div className='row w100 FRCS'>
            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                {deleted ? <MessageDeleted /> : <>
                    <MediaDisplayer media={media} />
                    <div className="message-content">
                        <Message html={message} />
                        <MessageActions />
                    </div>
                    <MessageMeta createdAt={createdAt} readReceipt={readReceipt} isCurrentUser={isCurrentUser} />
                </>}
            </div>
        </div>
    ) : null;
};

export default ChatMessageItem;