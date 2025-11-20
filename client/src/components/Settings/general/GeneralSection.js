import "./GeneralSection.css";

const GeneralSection = ({ title, description, warning, bgColor, items, addItemButton }) => {
    return (
        <div className="general-section">

            <div className="FRCB section-title-wrapper">
                <h3 className="section-title">{title}</h3>
                <button className="add-item-button" onClick={addItemButton.onClick} style={{ backgroundColor: bgColor }}>
                    <i className={`${addItemButton.icon} mR5 colorFFF w15`} aria-hidden="true"></i> {addItemButton.title}
                </button>
            </div>

            <p className="section-desc">{description}</p>

            {warning && (
                <div className="section-warning">
                    <i className='fa fa-exclamation-triangle w15' aria-hidden="true"></i>
                    <span>{warning}</span>
                </div>
            )}

            <div className="account-items">
                {items.map((item, index) => (
                    <div key={index} className="account-item">
                        <div className="icon-wrapper" style={{ color: '#fff', backgroundColor: bgColor }}>
                            {item.icon}
                        </div>
                        <div className="item-info">
                            <div className="item-label">{item.label}</div>
                            <div className="item-time">{item.timeAgo}</div>
                        </div>
                        {item.extra && <div className="item-extra">{item.extra}</div>}
                    </div>
                ))}
            </div>

        </div>
    );
};

export default GeneralSection;
