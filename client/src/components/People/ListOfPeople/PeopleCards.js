import './PeopleCards.css';
import Loader from '../../Loader';
import PeopleCard from './PeopleCard';
import ErrorMessage from '../../ErrorMessage';
import { useEffect, useRef, useState } from 'react';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { setPeople } from '../../../store/reducers/PeopleSlice';

const NoPeopleFound = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-users mR5' />
                No members found
            </p>
        </div>
    )
}

const PeopleCards = () => {

    const dispatch = useDispatch();
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const { people, searchUser } = useSelector(state => state.people);

    const firstRender = useRef(true);

    const getUsers = () => {
        apiRequest(`/api/v1/people/search?query=${searchUser.trim()}`, "GET").then(({ data }) => {
            setLoading(false);
            dispatch(setPeople(data));
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
        });
    }

    useEffect(getUsers, []);

    useEffect(() => {

        if (firstRender.current) {
            firstRender.current = false;
            return;
        }

        const timer = setTimeout(() => {
            getUsers();
        }, 500);
        return () => clearTimeout(timer);

    }, [searchUser]);


    return (
        <div className='FCSS custom-scrollbar' id='PeopleCards'>
            <div id='list_of_people'>
                {loading ? <Loader /> :
                    error ? <ErrorMessage message={error} /> :
                        people.length ? people.map((person, index) =>
                            <PeopleCard key={index} person={person} />
                        ) : <NoPeopleFound />
                }
            </div>

        </div>
    )
}

export default PeopleCards