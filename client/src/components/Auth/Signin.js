import { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ChatGifComponent from './ChatGifComponent'
import './auth.css'
import { hasCookie } from './utils'
import { apiRequest } from '../../http_request'
import { clearMyProfile } from '../../store/reducers/MyProfileSlice'
import { useDispatch } from 'react-redux'

const Signin = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [email, setEmail] = useState("dharmaraj.171215@gmail.com");
    const [password, setPassword] = useState("123456789");
    const [error, setError] = useState("");
    const [passwordVisible, setPasswordVisible] = useState(false);

    const signinFormHandler = (e) => {
        e.preventDefault();
        apiRequest("/auth/login", 'POST', {
            email,
            password
        }).then(({ data }) => {
            const { token } = data || {};
            if (token) {
                sessionStorage.setItem('token', token);
                document.cookie = `token=${token}; path=/;`;
                sessionStorage.setItem('org-id', '1');
                navigate('/feeds');
            } else {
                console.error("Login failed: No token received");
            }
        }).catch((error) => {
            setError(error.message || "An error occurred during login");
        });
    }

    const togglePasswordViewHandler = () => {
        passwordVisible ? setPasswordVisible(false) : setPasswordVisible(true);
    }

    useEffect(() => {
        if (hasCookie()) {
            navigate('/');
        } else {
            dispatch(clearMyProfile());
        }
    }, []);

    return (
        <div className='auth_component w100 FRCC'>
            <ChatGifComponent />
            <div className='auth_component_form_container FRCC'>
                <form className='auth_component_form FCCC' onSubmit={signinFormHandler}>
                    <h1>Welcome</h1>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Email</label>
                        <div className='auth_component_form_input w100'>
                            <i className="fa-regular fa-user input_icon"></i>
                            <input type="email" placeholder='Type Email Address' className='w100' value={email} onChange={(e) => setEmail(e.currentTarget.value)} />
                        </div>
                    </div>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Password</label>
                        <div className='auth_component_form_input w100'>
                            <i className="fa-solid fa-key input_icon"></i>
                            <input type={passwordVisible ? 'text' : `password`} placeholder='Type Password' className='w100' value={password} onChange={(e) => setPassword(e.currentTarget.value)} />
                            <i className={`fa-regular ${passwordVisible ? 'fa-eye-slash' : 'fa-eye'} showHidePassword cursP`} onClick={togglePasswordViewHandler}></i>
                        </div>
                        <div className='FRCB w100 mT10 remember_me_forgot_password'>
                            <div className='FRCC'>
                                <input type='checkbox' id='remember_me' />
                                <label for="remember_me" className='pL10 color777 cursP'>Remember Me</label>
                            </div>
                            <span className='color777 cursP'>Forgot Password?</span>
                        </div>
                    </div>
                    <p id='error-message'>{error}</p>
                    <div className='auth_component_form_field w100 mT10'>
                        <button className='submit_button w100 mT10 cursP' type='submit'>Log In</button>
                    </div>

                    <div className='FRCC auth_component_form_field w100 mT10 navigator_parent'>
                        <p className='navigator color777'>Don't have account?
                            <b className='cursP'>
                                <Link to='/signup'>Sign Up</Link>
                            </b>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Signin