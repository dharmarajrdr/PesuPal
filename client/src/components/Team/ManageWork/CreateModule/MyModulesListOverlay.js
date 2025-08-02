import { useEffect, useState } from 'react';
import './MyModulesList.css';
import utils from '../../../../utils';
import { apiRequest } from '../../../../http_request';
import Loader from '../../../Loader';

const MyModulePreview = ({ module }) => {

    const { id, name, createdAt, active, accessibility } = module || {};

    const accessibilityIcon = {
        "ANYONE_IN_ORG": "fa fa-globe",
        "ONLY_ME": "fa fa-lock",
        "SELECTIVE_MEMBERS": "fa fa-user-friends"
    }

    return (
        <div className='my-module-preview FRSB w100'>
            <p className='FRCS'>
                <span title={name}>{name}</span>
                <i className={`${accessibilityIcon[accessibility.name]} fs12 color777 pL5`} title={accessibility.description}></i>
            </p>
            <span className='created-time'>Created {utils.agoTimeCalculator(createdAt)}</span>
        </div>
    )
}

const NoModulesAvailable = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5'></i>
                No modules created yet.
            </p>
        </div>
    )
}

const MyModulesList = () => {

    const [modules, setModules] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        apiRequest('/api/v1/module/created-by-me', 'GET').then(({ data }) => {
            setLoading(false);
            setModules(data);
        }).catch(({ message }) => {
            setLoading(false);
        });
    }, []);

    return (
        <div id='my-modules-list' className='FCSS h100'>
            <h2 id='my-modules-list-title' className='w100'>Modules - Created By Me ({modules.length})</h2>
            <div className='my-modules-list h100 w100'>
                {loading ? (
                    <Loader />
                ) : modules.length ? (
                    modules.map(module => <MyModulePreview key={module.id} module={module} />)
                ) : (
                    <NoModulesAvailable />
                )}
            </div>
        </div>
    );
}

const MyModulesListOverlay = ({ onCloseModal }) => {
    return (
        <div id='my-modules-list-overlay' className='entire-screen-overlay FRSE' onClick={onCloseModal}>
            <MyModulesList />
        </div>
    )
}

export default MyModulesListOverlay