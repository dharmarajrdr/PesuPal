import { useEffect, useState } from 'react';
import PeopleCard from './PeopleCard';
import './PeopleCards.css';
import { apiRequest } from '../../../http_request';
import Loader from '../../Loader';
import ErrorMessage from '../../ErrorMessage';
import Profile from '../../OthersProfile/Profile';

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

const PeopleCards = () => {

    const [people, setPeople] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedPerson, setSelectedPerson] = useState(null);

    useEffect(() => {
        apiRequest("/api/v1/people", "GET").then(({ data }) => {
            setLoading(false);
            setPeople(data);
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
        });
    }, []);

    return (
        <div className='FCSS custom-scrollbar' id='PeopleCards'>
            <div id='list_of_people'>
                {loading ? <Loader /> :
                    error ? <ErrorMessage message={error} /> :
                        people.length ? people.map((person, index) =>
                            <PeopleCard key={index} person={person} setShowProfile={() => setSelectedPerson(person)} />
                        ) : <NoPeopleFound />
                }
                {selectedPerson && (
                    <Profile Profile={selectedPerson} setShowProfile={() => setSelectedPerson(null)} />
                )}
            </div>

        </div>
    )
}

export default PeopleCards