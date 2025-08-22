import { Link } from 'react-router-dom';
import ListOfAdditionalFeatures from './ListOfAdditionalFeatures.js';
import './MoreFeaturesList.css';

const FeaturePreview = ({ feature }) => {

    const { backgroundColor, id, displayName, description, icon, route } = feature || {};

    return <Link key={id} className="feature-item p10 FRSB" to={`/more${route}`}>
        <div className='FRCC feature-item-icon img_40_40' style={{ backgroundColor }}>
            <i className={icon}></i>
        </div>
        <div className='FCSS feature-item-details'>
            <h4>{displayName}</h4>
            <p>{description}</p>
        </div>
    </Link>
}

const MoreFeaturesList = () => {

    return (
        <div id="more-features-list" className='FRSS w100'>
            {ListOfAdditionalFeatures && ListOfAdditionalFeatures.map(feature => <FeaturePreview key={feature.id} feature={feature} />)}
        </div>
    )
}

export default MoreFeaturesList