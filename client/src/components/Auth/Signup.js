import { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ChatGifComponent from './ChatGifComponent'
import './auth.css'
import { hasCookie } from './utils'
import { apiRequest } from '../../http_request'
import { UsePopupFromSession } from '../../UsePopupFromSession'
import Popup from '../Popup'
import { clearMyProfile } from '../../store/reducers/MyProfileSlice'
import { useDispatch } from 'react-redux'
import { showPopup } from '../../store/reducers/PopupSlice'

const Signup = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [email, setEmail] = useState("dharmaraj.171215@gmail.com");
    const [password, setPassword] = useState("123456789");
    const [phone, setPhone] = useState("1234567890");
    const [error, setError] = useState("");
    const [passwordVisible, setPasswordVisible] = useState(false);

    const signupFormHandler = (e) => {
        e.preventDefault();
        apiRequest("/api/v1/user", 'POST', { email, password, phone }).then(({ data }) => {
            setEmail("");
            setPassword("");
            setPhone("");
            dispatch(showPopup({ message: "Account created successfully!", type: "success" }));
        }).catch(({ message }) => {
            setError(message || "An error occurred during login");
        });
    }


    const togglePasswordViewHandler = () => {
        passwordVisible ? setPasswordVisible(false) : setPasswordVisible(true);
    }

    useEffect(() => {
        if (hasCookie()) {
            navigate('/feeds');
        } else {
            dispatch(clearMyProfile());
        }
    }, []);

    return (
        <div className='auth_component w100 FRCC'>
            <ChatGifComponent />
            <div className='auth_component_form_container FRCC'>
                <form className='auth_component_form FCCC' onSubmit={signupFormHandler}>
                    <h1>Create Account</h1>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Email</label>
                        <div className='auth_component_form_input w100'>
                            <i className="fa-regular fa-user input_icon"></i>
                            <input type="email" placeholder='Type Email Address' className='w100' value={email} onChange={(e) => setEmail(e.currentTarget.value)} />
                        </div>
                    </div>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Phone Number</label>
                        <div className='auth_component_form_input w100'>
                            <i className="fa fa-phone input_icon"></i>
                            <input type="number" placeholder='Type your phone number' className='w100' maxLength={10} value={phone} onChange={(e) => setPhone(e.currentTarget.value)} />
                        </div>
                    </div>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Password</label>
                        <div className='auth_component_form_input w100'>
                            <i className="fa-solid fa-key input_icon"></i>
                            <input type={passwordVisible ? 'text' : `password`} placeholder='Type Password' className='w100' value={password} onChange={(e) => setPassword(e.currentTarget.value)} />
                            <i className={`fa-regular ${passwordVisible ? 'fa-eye-slash' : 'fa-eye'} showHidePassword cursP`} onClick={togglePasswordViewHandler}></i>
                        </div>
                    </div>
                    <p id='error-message'>{error}</p>
                    <div className='auth_component_form_field w100 mT10'>
                        <button className='submit_button w100 mT10 cursP' type='submit'>Sign Up</button>
                    </div>

                    <div className='FRCC auth_component_form_field w100 mT10 navigator_parent'>
                        <p className='navigator color777'>Already having account?
                            <b className='cursP'>
                                <Link to='/signin'>Sign In</Link>
                            </b>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Signup