export default {
    "header": [
        {
            "title": "Subject",
            "sort": true,
            "width": "450px",
            "type": "string"
        },
        {
            "title": "Status",
            "width": "200px",
            "type": "string"
        },
        {
            "title": "Owner",
            "width": "250px",
            "type": "object"
        },
        {
            "title": "Priority",
            "width": "150px",
            "type": "object"
        },
        {
            "title": "Created_By",
            "width": "250px",
            "type": "object"
        },
        {
            "title": "Created_At",
            "width": "250px",
            "type": "string"
        },
        {
            "title": "Tag",
            "width": "150px",
            "type": "object"
        }
    ],
    "data": [
        {
            "id": 1,
            "subject": "[Bug] Component not rendering. Some long text here",
            "status": "On Hold",
            "priority": {
                "name": "High"
            },
            "owner": {
                "name": "John Doe",
                "image": '/images/Users/user_3.jpg'
            },
            "created_by": {
                "name": "Sara Smith",
                "image": "/images/Users/user_8.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": {
                "name": "Bug"
            },
            "route": "/team/manage_work/item/1"
        },
        {
            "id": 2,
            "subject": "Implement add photo feature",
            "status": "In Progress",
            "priority": {
                "name": "Medium"
            },
            "owner": {
                "name": "Kevin Smith",
                "image": '/images/Users/user_2.jpg'
            },
            "created_by": {
                "name": "Robert Williams",
                "image": "/images/Users/user_3.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": {
                "name": "Feature"
            },
            "route": "/team/manage_work/item/2"
        },
        {
            "id": 3,
            "subject": "Analyse the data and provide the report",
            "status": "Yet to Start",
            "priority": {
                "name": "Medium"
            },
            "owner": {
                "name": "Kevin Smith",
                "image": '/images/Users/user_2.jpg'
            },
            "created_by": {
                "name": "Donna Evans",
                "image": "/images/Users/user_6.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": {
                "name": "Task"
            },
            "route": "/team/manage_work/item/2"
        },
        {
            "id": 4,
            "subject": "Generate the invoice for the client",
            "status": "Released",
            "priority": {
                "name": "Low"
            },
            "owner": {
                "name": "Martin Lopez",
                "image": '/images/Users/user_1.jpg'
            },
            "created_by": {
                "name": "Kevin Jackson",
                "image": "/images/Users/user_1.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": {
                "name": "Task"
            },
            "route": "/team/manage_work/item/2"
        }
    ]
}