const MembersCount = ({ count }) => {

    return count > 0 ? <div>
        <span className="MembersCount mT5">
            {count} Members
        </span>
    </div> : null
}

const Leaf = ({ department }) => {

    const { name, children } = department || {};

    return name ? (
        <a href="javascript:void(0);" className="leaf-node">
            <div className="FCCC">
                <h3>{name}</h3>
                <MembersCount count={children?.length} />
            </div>
        </a>
    ) : null;
}

export default Leaf