import React, { useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ChatGifComponent from './ChatGifComponent'
import './auth.css'
import { hasCookie } from './utils'

const Signup = () => {

    const navigate = useNavigate();

    useEffect(() => {
        if (hasCookie()) {
            navigate('/home');
        }
    }, []);

    return (
        <div className='auth_component w100 FRCC'>
            <ChatGifComponent />
            <div className='auth_component_form_container FRCC'>
                <form className='auth_component_form FCCC'>
                    <h1>Create Account</h1>
                    <div className='FCSC auth_component_form_field w100'>
                        <label className='field_name selectNone'>User Name</label>
                        <div className='auth_component_form_input w100'>
                            <i class="fa-regular fa-user input_icon"></i>
                            <input type="email" placeholder='Type your Name' className='w100' />
                        </div>
                    </div>
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
                    </div>
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