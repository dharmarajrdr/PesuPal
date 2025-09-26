import ImageUploader from '../../ImageUploader';
import './AccountSection.css';

const AccountSection = () => {
    return (
        <div className="FCSS" id='account-section'>
            <div className='FRCS'>
                <ImageUploader style={{ width: "150px", height: "150px", margin: "0 auto" }} />
                <div className='FCCS'>
                    <b>Dharmaraj R</b>
                </div>
            </div>
        </div>
    )
}

export default AccountSection