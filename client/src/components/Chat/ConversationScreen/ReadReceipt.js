const ReadReceipt = ({ readReceipt = "SENT" }) => {
    const icons = {
        PENDING: 'fa fa-clock',
        SENT: 'fa fa-check',
        DELIVERED: 'fa fa-check-double',
        READ: 'fa fa-eye',
    };

    return <i className={`${icons[readReceipt]} delivery-status mL5`} title={readReceipt} />;
};

export default ReadReceipt;