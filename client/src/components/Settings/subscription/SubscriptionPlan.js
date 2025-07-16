import Plan from './Plan';
import './SubscriptionPlan.css';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../http_request';
import Loader from '../../Loader';
import ErrorMessage from '../../ErrorMessage';

const SubscriptionPlan = () => {

    const [plans, setPlans] = useState([]);
    const [loader, setLoader] = useState(true);
    const [error, setError] = useState(null);

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