import { useState } from 'react'
import SearchPeople from './SearchPeople'
import PeopleCards from './PeopleCards'

const AllUsers = () => {

    const [people, setPeople] = useState([]);
    const [searchUser, setSearchUser] = useState('');

    return (
        <>
            <SearchPeople searchUser={searchUser} setSearchUser={setSearchUser} people={people} />
            <PeopleCards searchUser={searchUser} people={people} setPeople={setPeople} />
        </>
    )
}

export default AllUsers