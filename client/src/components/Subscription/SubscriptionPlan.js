import PlanDetails from './PlanDetails'
import Plan from './Plan';
import './SubscriptionPlan.css';

const SubscriptionPlan = () => {

    const plans = PlanDetails;

    return (
        <div className='FRSS h100 subscription-plans'>
            {plans.map((plan) => <Plan plan={plan} key={plan.id} />)}
        </div>
    )
}

export default SubscriptionPlan