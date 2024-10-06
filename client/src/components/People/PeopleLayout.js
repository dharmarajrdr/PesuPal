import React from 'react'
import ListOfPeople from './ListOfPeople/ListOfPeople'
import SearchPeople from './ListOfPeople/SearchPeople'

const PeopleLayout = () => {
  return (
    <div className='h100 Layout'>
        <SearchPeople />
        <ListOfPeople />
    </div>
  )
}

export default PeopleLayout