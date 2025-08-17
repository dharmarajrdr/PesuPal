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

const Item = ({ id, name, security }) => (
    <Link to={`/store/drive/${id}`} className='FRCC directory-list-item'>
        {security === 'SECURED' && <i className='fa fa-lock fs10 color555 w15'></i>}
        <span>{name}</span>
    </Link>
)

const DirectoryPath = () => {
    return (
        <div className='w100 FRCS' id='directory-path'>
            {directories.map(({ id, name, security }) => (
                <Item key={id} id={id} name={name} security={security} />
            ))}
        </div>
    )
}

export default DirectoryPath