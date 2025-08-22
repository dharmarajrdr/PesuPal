import GeneralSection from './GeneralSection';

const GeneralLayout = () => {

    return (
        <div style={{ padding: "20px" }}>
            <GeneralSection
                title="My Email Addresses"
                bgColor="#00aaff"
                description="View and manage the email addresses associated with your account. They can be used to sign in and to reset password if you ever forget it."
                items={[
                    {
                        icon: <i className='fa fa-envelope' aria-hidden="true"></i>,
                        label: "dharmaraj.171215@gmail.com",
                        timeAgo: "3 years ago",
                        extra: <i className='fa fa-check-circle' aria-hidden="true" style={{ color: "#28a745" }}></i>
                    },
                    {
                        icon: <i className='fa fa-envelope' aria-hidden="true"></i>,
                        label: "dharmavkl2001@gmail.com",
                        timeAgo: "2 years ago",
                        extra: <i className='fa fa-check-circle' aria-hidden="true" style={{ color: "#28a745" }}></i>
                    },
                    {
                        icon: <i className='fa fa-envelope' aria-hidden="true"></i>,
                        label: "rdrdharma003@gmail.com",
                        timeAgo: "1 year ago"
                    }
                ]}
                addItemButton={{
                    title: "Add Email",
                    icon: 'fa fa-plus',
                    onClick: () => alert("Add Email Clicked")
                }}
            />

            <GeneralSection
                title="My Mobile Numbers"
                bgColor="#ff5a3c"
                description="View and manage all of the mobile numbers associated with your account."
                warning="You can no longer use your recovery mobile numbers, as your admin has restricted users from resetting passwords themselves."
                items={[
                    {
                        icon: <i className='fa fa-phone' aria-hidden="true"></i>,
                        label: "+91 63833 92245",
                        timeAgo: "3 years ago"
                    },
                    {
                        icon: <i className='fa fa-phone' aria-hidden="true"></i>,
                        label: "+91 70942 24529",
                        timeAgo: "2 years ago"
                    }
                ]}
                addItemButton={{
                    title: "Add Mobile",
                    icon: 'fa fa-plus',
                    onClick: () => alert("Add Mobile Clicked")
                }}
            />
        </div>
    );
}

export default GeneralLayout