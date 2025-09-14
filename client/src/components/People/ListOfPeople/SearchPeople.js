import './SearchPeople.css';
import { useDispatch, useSelector } from 'react-redux';
import { setSearchUser } from '../../../store/reducers/PeopleSlice';

const SearchPeople = () => {

    const dispatch = useDispatch();
    const { people, searchUser } = useSelector(state => state.people);

    return (
        <div id='search_people' className='w100 FRSS'>
            <div id='search_people_input_container' className='FRES'>
                <input type='input' placeholder='Search people, department,..' id='search_people_input' autoComplete='off' spellCheck='false' value={searchUser} onChange={(e) => dispatch(setSearchUser(e.target.value))} />
                {searchUser.trim().length > 0 && <label className='FRCC color555' id='search_count'>
                    <b className='pR5 color555'>{people.length}</b> users found
                </label>}
            </div>
        </div>
    )
}

export default SearchPeople