import React from 'react';
import PeopleData from './PeopleData';
import PeopleCard from './PeopleCard';
import './PeopleCards.css';

const PeopleCards = () => {
    return (
        <div className='FCSS custom-scrollbar' id='PeopleCards'>
            <div id='list_of_people'>
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
        </div>
    )
}

export default PeopleCards