import MessageDeleted from './MessageDeleted';
import Message from './Message';
import MessageActions from './MessageActions';
import MessageMeta from './MessageMeta';
import { useSelector } from 'react-redux';
import UserAvatar from '../../User/UserAvatar';

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

const SenderName = ({ displayName, sent_or_received, is_super_admin }) => {
    return <p className={`sender-name ${sent_or_received}`}>
        {displayName}
        {is_super_admin && <i title='Super Admin' className='fa fa-user-shield mL5 fs12 super-admin-icon' />}
    </p>
}

const ChatMessageItem = ({ msg, isSameSender }) => {

    const { sender, deleted, createdAt, readReceipt, message, media, chatMode, reactions } = msg;
    const { id: senderId, displayName, displayPicture, is_super_admin } = sender || {};

    const showUserMeta = chatMode == 'GROUP_MESSAGE';

    const myProfile = useSelector(state => state.myProfile);

    const isCurrentUser = senderId == myProfile?.id;

    const sent_or_received = isCurrentUser ? 'sent' : 'received';

    return myProfile ? (
        <div className={`row w100 FRSS ${sent_or_received}`}>
            {showUserMeta && (
                isSameSender ? <div className="user-avatar-placeholder img_40_40 mL5" /> :
                    <UserAvatar displayName={displayName} displayPicture={displayPicture} />
            )}
            <div className={`message-wrapper FCSE ${sent_or_received}`}>
                {showUserMeta && !isSameSender && <SenderName displayName={displayName} sent_or_received={sent_or_received} is_super_admin={is_super_admin} />}
                <div className={`message ${sent_or_received}`}>
                    {deleted ? <MessageDeleted /> : <>
                        <MessageActions isCurrentUser={isCurrentUser} />
                        <MediaDisplayer media={media} />
                        <div className="message-content">
                            <Message html={message} />
                        </div>
                        <MessageMeta createdAt={createdAt} readReceipt={readReceipt} isCurrentUser={isCurrentUser} reactions={reactions} />
                    </>}
                </div>
            </div>
        </div>
    ) : null;
};

export default ChatMessageItem;