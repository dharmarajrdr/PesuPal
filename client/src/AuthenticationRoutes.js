import { Route, Routes } from 'react-router-dom'
import Signup from './components/Auth/Signup'
import Signin from './components/Auth/Signin'

const AuthenticationRoutes = () => (
    <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
    </Routes>
)

export default AuthenticationRoutes