import { useEffect, useRef, useState } from 'react';
import PeopleCard from './PeopleCard';
import './PeopleCards.css';
import { apiRequest } from '../../../http_request';
import Loader from '../../Loader';
import ErrorMessage from '../../ErrorMessage';

const NoPeopleFound = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-users mR5' />
                No members found
            </p>
            <p className='w100 alignCenter'>Recruit some people to your organization</p>
        </div>
    )
}

const PeopleCards = ({ searchUser, people, setPeople }) => {

    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const firstRender = useRef(true);

    const getUsers = () => {
        apiRequest(`/api/v1/people/search?query=${searchUser.trim()}`, "GET").then(({ data }) => {
            setLoading(false);
            setPeople(data);
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