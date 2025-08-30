import './FeedsMainPanel.css'
import AllPosts from './AllPosts'

const FeedsMainPanel = ({ searchText }) => {
    return (
        <div id='FeedsMainPanel'>
            <AllPosts searchText={searchText} />
        </div>
    )
}

export default FeedsMainPanel