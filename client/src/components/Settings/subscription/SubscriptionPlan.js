import Plan from './Plan';
import './SubscriptionPlan.css';
import Loader from '../../Loader';
import { useEffect, useState } from 'react';
import ErrorMessage from '../../ErrorMessage';
import { apiRequest } from '../../../http_request';

const SubscriptionPlan = () => {

    const [plans, setPlans] = useState([]);
    const [error, setError] = useState(null);
    const [loader, setLoader] = useState(true);

    useEffect(() => {
        apiRequest('/api/v1/subscription-plan', 'GET').then(({ data }) => {
            setLoader(false);
            setPlans(data);
        }).catch(({ message }) => {
            setLoader(false);
            setError(message);
        });
    }, []);

    return loader ? <Loader /> :
        error ? <ErrorMessage message={error} /> : (
            <div className='FRSS h100 w100 subscription-plans'>
                {plans.map((plan) => <Plan plan={plan} key={plan.id} />)}
            </div>
        )
}

export default SubscriptionPlan