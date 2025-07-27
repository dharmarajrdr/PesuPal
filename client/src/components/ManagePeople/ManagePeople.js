import { useState } from 'react';
import AddUserLayout from './AddUserLayout';
import './ManagePeople.css';
import ManagePeopleHeader from './ManagePeopleHeader';

const initialPeople = [
    {
        userId: "AMwYOJWFAkoQ",
        displayName: "Dharmaraj R",
        displayPicture: "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
        designation: "CEO",
        department: "Executive Department",
        status: "Away",
        email: "dharmaraj.171215@gmail.com",
        phone: "1234567890"
    },
    {
        userId: "uexJUG4ral4j",
        displayName: "Mohankumar R",
        displayPicture: "https://pbs.twimg.com/media/E03qKiHUYAE9tNW.jpg:large",
        designation: "CEO",
        department: "Executive Department",
        status: "Away",
        email: "mohan.rmk@gmail.com",
        phone: "8072113856",
        chatId: "MHI3f5RkAQg6"
    }
];

const ManagePeople = () => {

    const [showAddUserLayout, setShowAddUserLayout] = useState(false);

    return (
        <div className="manage-people-container h100">
            <ManagePeopleHeader setShowAddUserLayout={setShowAddUserLayout} />
            {showAddUserLayout && <AddUserLayout setShowAddUserLayout={setShowAddUserLayout} />}
        </div>
    );
};

export default ManagePeople;
