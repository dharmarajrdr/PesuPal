import AllUsers from './ListOfPeople/AllUsers'
import PageNotFound from '../Auth/PageNotFound'
import { Route, Routes } from 'react-router-dom'
import UserPostsLayout from './UserPosts/UserPostsLayout'

const PeopleLayout = () => {
    return (
        <div className='Layout'>
            <Routes>
                <Route path='/' element={<AllUsers />} />
                <Route path='/:userId/posts' element={<UserPostsLayout />} />
                <Route path='*' element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default PeopleLayout