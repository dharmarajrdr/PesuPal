import { useSelector } from 'react-redux';
import './DirectoryPath.css';
import { Link } from 'react-router-dom'

const directories = [
    {
        "type": "FOLDER",
        "id": 1,
        "name": "Recruitment",
        "security": "NONE"
    },
    {
        "type": "FOLDER",
        "id": 2,
        "name": "2025",
        "security": "NONE"
    },
    {
        "type": "FOLDER",
        "id": 3,
        "name": "PSG College",
        "security": "SECURED"
    }
]

const Item = ({ id, name, security, space }) => {

    const route = `/store/${space.toLowerCase()}${id ? `/folder/${id}` : ''}`;

    return <Link to={route} className='FRCC directory-list-item'>
        {security === 'SECURED' && <i className='fa fa-lock fs10 color555 w15'></i>}
        <span>{name}</span>
    </Link>
};

const DirectoryPath = () => {

    const { parents: directories } = useSelector((state) => state.drive) || [];

    return (
        <div className='FRCS w100' id='directory-path'>
            <i className='fa fa-folder w20 mL10 fs20 color555'></i>
            <div className='FRCS' >
                {directories.map(({ id, name, security, space }) => (
                    <Item key={id} id={id} name={name} security={security} space={space} />
                ))}
            </div>
        </div>
    )
}

export default DirectoryPath