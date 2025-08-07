const MessageDeleted = () => (
    <p className='message-content message-deleted FRCS selectNone'>
        <i className='fa fa-trash fs12 mR5 colorAAA' title="Deleted" />
        <span className="deleted-message colorAAA italic fs12">This message was deleted</span>
    </p>
);

export default MessageDeleted;