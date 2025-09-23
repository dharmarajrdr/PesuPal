import Leaf from './Leaf'
import ParentChilden from './ParentChilden'

const Children = ({ children }) => {

    return (
        <ul>
            {children?.map((department) => {
                return <li>
                    <ParentChilden department={department} />
                </li>
            })}
        </ul>
    )
}

export default Children