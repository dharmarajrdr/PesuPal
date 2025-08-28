import './TagsListLayout.css';
import Loader from '../Loader';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';

const Tag = ({ tag }) => {

    const { title, count } = tag;
    const tagName = title?.replace('#', '');

    return title && count && (
        <Link to={`/feeds/tags/${tagName}`} className='FRCC tag-with-count'>
            <span className='title'>{title}</span>
            <span className='count'>{count > 999 ? '999+' : count}</span>
        </Link>
    )
}

const TagsListLayout = () => {

    const dispatch = useDispatch();
    const [tags, setTags] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        apiRequest(`/api/v1/tags?size=100`, 'GET').then(({ data }) => {
            setTags(data);
            setLoading(false);
        }).catch(({ message }) => {
            setLoading(false);
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    return (
        <div id='tags-list-layout' className='w100'>
            {loading ? <Loader /> : tags.map((tag, index) => <Tag key={index} tag={tag} />)}
        </div>
    )
}

export default TagsListLayout