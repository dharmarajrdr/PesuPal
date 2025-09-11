import Leaf from "./Leaf"
import Children from "./Children"

const ParentChilden = ({ department }) => {

    return <>
        <Leaf department={department} />
        <Children children={department.children} />
    </>
}

export default ParentChilden