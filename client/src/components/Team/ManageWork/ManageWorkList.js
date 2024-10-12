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
            "type": "string"
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
            "type": "string"
        }
    ],
    "data": [
        {
            "id": 1,
            "subject": "[Issue] Component not rendering. Some long text here",
            "status": "In Progress",
            "priority": "High",
            "owner": {
                "name": "John Doe",
                "image": '/images/Users/user_7.jpg'
            },
            "created_by": {
                "name": "Kevin Jackson",
                "image": "/images/Users/user_1.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": "Issue",
            "route": "/team/manage_work/item/1"
        },
        {
            "id": 2,
            "subject": "Implement add photo feature",
            "status": "In Progress",
            "priority": "Medium",
            "owner": {
                "name": "Kevin Smith",
                "image": '/images/Users/user_7.jpg'
            },
            "created_by": {
                "name": "Kevin Jackson",
                "image": "/images/Users/user_1.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": "Task",
            "route": "/team/manage_work/item/2"
        },
        {
            "id": 3,
            "subject": "Analyse the data and provide the report",
            "status": "Todo",
            "priority": "Medium",
            "owner": {
                "name": "Kevin Smith",
                "image": '/images/Users/user_7.jpg'
            },
            "created_by": {
                "name": "Kevin Jackson",
                "image": "/images/Users/user_1.jpg"
            },
            "created_at": "01-09-2023 03:45PM",
            "tag": "Task",
            "route": "/team/manage_work/item/2"
        }
    ]
}