import { Navigate, Route, Routes } from 'react-router-dom'
import AllUsers from './ListOfPeople/AllUsers'
import UserPostsLayout from './UserPosts/UserPostsLayout'
import PageNotFound from '../Auth/PageNotFound'

const PeopleLayout = () => {
    return (
        <div className='Layout'>
            <Routes>
                <Route path='/' element={<AllUsers />} />
                <Route path='/:id/posts' element={<UserPostsLayout />} />
                <Route path='*' element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default PeopleLayout