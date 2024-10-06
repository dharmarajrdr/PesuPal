import React from 'react';
import PeopleData from './PeopleData';
import PeopleCard from './PeopleCard';
import './PeopleCards.css';

const PeopleCards = () => {
    return (
        <div className='FRSS custom-scrollbar' id='list_of_people'>
            {PeopleData.map((person, index) =>
                <PeopleCard key={index} person={person} />
            )}
            {PeopleData.map((person, index) =>
                <PeopleCard key={index} person={person} />
            )}
            {PeopleData.map((person, index) =>
                <PeopleCard key={index} person={person} />
            )}
            {PeopleData.map((person, index) =>
                <PeopleCard key={index} person={person} />
            )}
        </div>
    )
}

export default PeopleCards