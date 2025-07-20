const ReadReceipt = ({ readReceipt }) => {
    const icons = {
        PENDING: 'fa fa-clock',
        SENT: 'fa fa-check',
        DELIVERED: 'fa fa-check-double',
        READ: 'fa fa-check-double read',
    };

    return <i className={`${icons[readReceipt]} delivery-status mL5`} title={readReceipt.toLowerCase()} />;
};

export default ReadReceipt;