const Message = ({ html }) => (
  <div className="html-content-renderer" dangerouslySetInnerHTML={{ __html: html }} />
);

export default Message;