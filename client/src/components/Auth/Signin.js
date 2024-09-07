import React, { useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ChatGifComponent from './ChatGifComponent'
import './auth.css'
import { hasCookie } from './utils'

const Signin = () => {

    const navigate = useNavigate();

    useEffect(() => {
        if (hasCookie()) {
            navigate('/feeds');
        }
    }, []);

    return (
        <div className='auth_component w100 FRCC'>
            <ChatGifComponent />
            <div className='auth_component_form_container FRCC'>
                <form className='auth_component_form FCCC'>
                    <h1>Welcome</h1>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Email</label>
                        <div className='auth_component_form_input w100'>
                            <i class="fa-regular fa-user input_icon"></i>
                            <input type="email" placeholder='Type Email Address' className='w100' />
                        </div>
                    </div>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>Password</label>
                        <div className='auth_component_form_input w100'>
                            <i class="fa-solid fa-key input_icon"></i>
                            <input type="email" placeholder='Type Password' className='w100' />
                            <i class="fa-regular fa-eye showHidePassword cursP"></i>
                        </div>
                        <div className='FRCB w100 mT10 remember_me_forgot_password'>
                            <div className='FRCC'>
                                <input type='checkbox' id='remember_me' />
                                <label for="remember_me" className='pL10 color777 cursP'>Remember Me</label>
                            </div>
                            <span className='color777 cursP'>Forgot Password?</span>
                        </div>
                    </div>
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