import ParentChilden from './ParentChilden'
import './Tree.css'

const Tree = ({ department }) => {

    return (
        <div class="body genealogy-body genealogy-scroll">
            <div class="genealogy-tree">
                <ul>
                    <li>
                        <ParentChilden department={department} />
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default Tree