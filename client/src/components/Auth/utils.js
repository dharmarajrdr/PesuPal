const hasCookie = () => {
    return document.cookie.split(';').some((item) => item.trim().startsWith('token='));
}

export { hasCookie };