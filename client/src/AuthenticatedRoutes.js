import LeftNavigation from './components/LeftNavigation/LeftNavigation'
import SubscriptionPlan from './components/Settings/subscription/SubscriptionPlan'
import SubscriptionNotExpiredRoutes from './SubscriptionNotExpiredRoutes'

const AuthenticatedRoutes = ({ isSubscriptionExpired, inLobby }) => (<>
    {!inLobby && <LeftNavigation />}
    {isSubscriptionExpired ? <SubscriptionPlan /> : <SubscriptionNotExpiredRoutes />}
</>)

export default AuthenticatedRoutes