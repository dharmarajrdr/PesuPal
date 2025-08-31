const BackNextCancelButtons = ({ backButtonClicked, handleSubmit, cancelButtonClicked, submitButtonLabel }) => {

    return <div className='FRCB w100 mT10'>
        <div className='FRCS'>
            <button className="back-btn mR5" onClick={backButtonClicked}>Back</button>
        </div>
        <div className='FRCE'>
            <button className="submit-btn mR5" onClick={handleSubmit}>{submitButtonLabel || 'Next'}</button>
            <button className="cancel-btn mL5" onClick={cancelButtonClicked}>Cancel</button>
        </div>
    </div>
}

export default BackNextCancelButtons