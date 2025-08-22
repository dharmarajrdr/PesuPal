import './EmptyFolder.css';

const EmptyFolder = () => {
    return (
        <div className='' id='empty_folder'>
            <img src='/images/no_data_found.avif' className='' />
            <p>No files or folders found</p>
        </div>
    )
}

export default EmptyFolder