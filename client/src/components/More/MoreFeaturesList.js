import ListOfAdditionalFeatures from './ListOfAdditionalFeatures.js';
import './MoreFeaturesList.css';

const FeaturePreview = ({ feature }) => {

    const { backgroundColor } = feature;

    return <div key={feature.id} className="feature-item p10 FRSB">
        <div className='FRCC feature-item-icon img_40_40' style={{ backgroundColor }}>
            <i className={feature.icon}></i>
        </div>
        <div className='FCSS feature-item-details'>
            <h4>{feature.displayName}</h4>
            <p>{feature.description}</p>
        </div>
    </div>
}

const MoreFeaturesList = () => {

    return (
        <div id="more-features-list" className='FRSS w100'>
            {ListOfAdditionalFeatures && ListOfAdditionalFeatures.map(feature => <FeaturePreview key={feature.id} feature={feature} />)}
        </div>
    )
}

export default MoreFeaturesList