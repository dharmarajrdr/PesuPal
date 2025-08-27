import './TrendingTags.css'
import Loader from '../../Loader';
import { Link } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';

const TrendingTags = () => {

    const dispatch = useDispatch();
    const [tags, setTags] = useState([]);
    const [loader, setLoader] = useState(true);

    useEffect(() => {
        setLoader(true);
        apiRequest(`/api/v1/tags/trending?limit=10`, 'GET').then(({ data }) => {
            setTags(data || []);
            setLoader(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoader(false);
        });
    }, []);

    return (
        <div id='TrendingTags' className='FCSS'>
            <p id='title' className='w100 selectNone'>
                <i className='fa-regular fa-hashtag w15 mR5' style={{ color: 'orange' }} ></i>Trending tags
            </p>
            <div className='FRCC w100' id='TrendingTagsList'>
                {loader ? <div className='FRCC w100'>
                    <Loader />
                </div> : tags.map((tag, index) => {
                    const tagName = tag.startsWith('#') ? tag.slice(1) : tag;
                    return (
                        <Link to={`/feeds/tags/${tagName}`} key={index} className='TrendingTag'>{tag}</Link>
                    )
                })}
            </div>
        </div>
    )
}

export default TrendingTags