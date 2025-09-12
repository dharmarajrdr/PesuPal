const NotifyCount = ({ count }) => {

    return count > 0 ? <b className='notifyCount'>{count}</b> : <></>
}

export default NotifyCount;