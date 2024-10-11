import React from 'react'
import SearchPeople from './ListOfPeople/SearchPeople'
import PeopleCards from './ListOfPeople/PeopleCards'

const PeopleLayout = () => {
    return (
        <div className='Layout'>
            <SearchPeople />
            <PeopleCards />
        </div>
    )
}

export default PeopleLayout