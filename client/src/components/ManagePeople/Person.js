import './Person.css';
import { useState } from "react";

const FirstChar = ({ displayName }) => {

    return <p className='first-char-of-name'>{displayName.charAt(0).toUpperCase()}</p>
}

const Person = ({ person }) => {

    const { displayName, email, displayPicture } = person;
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture !== null && displayPicture !== undefined);

    return <div className='FRCS person-column'>
        {showDisplayPicture ? (
            <img src={displayPicture} onError={() => setShowDisplayPicture(false)} />
        ) : <FirstChar displayName={displayName} />}
        <div className='FCSS name-and-email'>
            <p className='person-name'>{displayName}</p>
            <p className='person-email'>{email}</p>
        </div>
    </div>
}

export default Person;