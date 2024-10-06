import React from 'react';
import PeopleData from './PeopleData';
import PeopleCard from './PeopleCard';

const PeopleCards = () => {
    return (
        <div className='FRSS'>
            {PeopleData.map((person, index) =>
                <PeopleCard key={index} person={person} />
            )}
        </div>
    )
}

export default PeopleCards