import './ErrorMessage.css';

const ErrorMessage = ({ message = "Something went wrong!" }) => {
    return (
        <div className="error-message w100 h100P FRCC">
            <i className="fa fa-exclamation-triangle mR5" aria-hidden="true" />
            <span>{message}</span>
        </div>
    )
}

export default ErrorMessage