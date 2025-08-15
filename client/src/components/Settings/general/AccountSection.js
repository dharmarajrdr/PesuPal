import React from "react";
import "./AccountSection.css";

const AccountSection = ({ title, description, warning, bgColor, items, addItemButton }) => {
    return (
        <div className="account-section">

            <div className="FRCB section-title-wrapper">
                <h3 className="section-title">{title}</h3>
                <button className="add-item-button" onClick={addItemButton.onClick} style={{ backgroundColor: bgColor }}>
                    <i className={`${addItemButton.icon} mR5 colorFFF`} aria-hidden="true"></i> {addItemButton.title}
                </button>
            </div>

            <p className="section-desc">{description}</p>

            {warning && (
                <div className="section-warning">
                    <i className='fa fa-exclamation-triangle' aria-hidden="true"></i>
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

export default AccountSection;
