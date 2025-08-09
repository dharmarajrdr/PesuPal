export default [
    {
        "fieldId": 40,
        "fieldName": "Subject",
        "fieldType": "STRING",
        "required": true,
        "searchable": false,
        "filterable": true,
        "sortable": true,
        "editable": true,
        "showInList": true,
        "showInDetail": true,
        "data": "/webapps/zohocrm/javascript/crm.js"
    },
    {
        "fieldId": 41,
        "fieldName": "Created By",
        "fieldType": "USER",
        "required": true,
        "searchable": false,
        "filterable": true,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": {
            "id": "AMwYOJWFAkoQ",
            "displayName": "Dharmaraj R",
            "displayPicture": "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
            "archived": false
        }
    },
    {
        "fieldId": 42,
        "fieldName": "Created At",
        "fieldType": "DATE_TIME",
        "required": true,
        "searchable": false,
        "filterable": true,
        "sortable": true,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": "2025-08-03T16:06:20.801513"
    },
    {
        "fieldId": 43,
        "fieldName": "Updated By",
        "fieldType": "USER",
        "required": false,
        "searchable": false,
        "filterable": true,
        "sortable": true,
        "editable": false,
        "showInList": false,
        "showInDetail": true
    },
    {
        "fieldId": 44,
        "fieldName": "Updated At",
        "fieldType": "DATE_TIME",
        "required": false,
        "searchable": false,
        "filterable": true,
        "sortable": true,
        "editable": false,
        "showInList": false,
        "showInDetail": true
    },
    {
        "fieldId": 45,
        "fieldName": "Notes",
        "fieldType": "TEXT",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": false,
        "showInDetail": true
    },
    {
        "fieldId": 46,
        "fieldName": "File Owner",
        "fieldType": "USER",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": {
            "id": "AMwYOJWFAkoQ",
            "displayName": "Dharmaraj R",
            "displayPicture": "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
            "archived": false
        }
    },
    {
        "fieldId": 47,
        "fieldName": "Service Name",
        "fieldType": "SELECT",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": false,
        "showInDetail": true,
        "data": [
            {
                "id": 22,
                "value": "zohocrm",
                "selected": true,
                "default": false
            },
            {
                "id": 23,
                "value": "ignite",
                "selected": false,
                "default": false
            },
            {
                "id": 24,
                "value": "abmclient",
                "selected": false,
                "default": false
            },
            {
                "id": 25,
                "value": "zohocommandcenter",
                "selected": false,
                "default": false
            }
        ]
    },
    {
        "fieldId": 51,
        "fieldName": "Status",
        "fieldType": "TRANSITION",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": {
            "name": "Backlog",
            "score": 0
        }
    },
    {
        "fieldId": 54,
        "fieldName": "Subscription (Per Month)",
        "fieldType": "CURRENCY",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": {
            "currency": [
                {
                    "name": "United States Dollar",
                    "code": "USD",
                    "symbol": "$",
                    "exchangeRate": 1.0,
                    "selected": false
                },
                {
                    "name": "Indian Rupee",
                    "code": "INR",
                    "symbol": "₹",
                    "exchangeRate": 80.0,
                    "selected": true
                },
                {
                    "name": "Euro",
                    "code": "EUR",
                    "symbol": "€",
                    "exchangeRate": 0.85,
                    "selected": false
                },
                {
                    "name": "British Pound",
                    "code": "GBP",
                    "symbol": "£",
                    "exchangeRate": 0.75,
                    "selected": false
                },
                {
                    "name": "Japanese Yen",
                    "code": "JPY",
                    "symbol": "¥",
                    "exchangeRate": 110.0,
                    "selected": false
                },
                {
                    "name": "Chinese Yuan",
                    "code": "CNY",
                    "symbol": "¥",
                    "exchangeRate": 6.5,
                    "selected": false
                },
                {
                    "name": "Kenyan Shilling",
                    "code": "KES",
                    "symbol": "KSh",
                    "exchangeRate": 110.0,
                    "selected": false
                },
                {
                    "name": "Tanzanian Shilling",
                    "code": "TZS",
                    "symbol": "TSh",
                    "exchangeRate": 2300.0,
                    "selected": false
                },
                {
                    "name": "Ugandan Shilling",
                    "code": "UGX",
                    "symbol": "USh",
                    "exchangeRate": 3600.0,
                    "selected": false
                },
                {
                    "name": "Rwandan Franc",
                    "code": "RWF",
                    "symbol": "RF",
                    "exchangeRate": 1000.0,
                    "selected": false
                },
                {
                    "name": "South African Rand",
                    "code": "ZAR",
                    "symbol": "R",
                    "exchangeRate": 15.0,
                    "selected": false
                },
                {
                    "name": "Nigerian Naira",
                    "code": "NGN",
                    "symbol": "₦",
                    "exchangeRate": 410.0,
                    "selected": false
                },
                {
                    "name": "Ghanaian Cedi",
                    "code": "GHS",
                    "symbol": "GH₵",
                    "exchangeRate": 6.0,
                    "selected": false
                },
                {
                    "name": "Central African CFA Franc",
                    "code": "XAF",
                    "symbol": "FCFA",
                    "exchangeRate": 600.0,
                    "selected": false
                },
                {
                    "name": "West African CFA Franc",
                    "code": "XOF",
                    "symbol": "CFA",
                    "exchangeRate": 600.0,
                    "selected": false
                },
                {
                    "name": "CFP Franc",
                    "code": "XPF",
                    "symbol": "CFPF",
                    "exchangeRate": 105.0,
                    "selected": false
                },
                {
                    "name": "United Arab Emirates Dirham",
                    "code": "AED",
                    "symbol": "د.إ",
                    "exchangeRate": 3.67,
                    "selected": false
                },
                {
                    "name": "Canadian Dollar",
                    "code": "CAD",
                    "symbol": "$",
                    "exchangeRate": 1.25,
                    "selected": false
                },
                {
                    "name": "Australian Dollar",
                    "code": "AUD",
                    "symbol": "$",
                    "exchangeRate": 1.35,
                    "selected": false
                },
                {
                    "name": "New Zealand Dollar",
                    "code": "NZD",
                    "symbol": "$",
                    "exchangeRate": 1.4,
                    "selected": false
                },
                {
                    "name": "Swiss Franc",
                    "code": "CHF",
                    "symbol": "CHF",
                    "exchangeRate": 0.92,
                    "selected": false
                },
                {
                    "name": "Swedish Krona",
                    "code": "SEK",
                    "symbol": "kr",
                    "exchangeRate": 8.5,
                    "selected": false
                },
                {
                    "name": "Norwegian Krone",
                    "code": "NOK",
                    "symbol": "kr",
                    "exchangeRate": 8.7,
                    "selected": false
                },
                {
                    "name": "Danish Krone",
                    "code": "DKK",
                    "symbol": "kr",
                    "exchangeRate": 6.5,
                    "selected": false
                },
                {
                    "name": "Polish Zloty",
                    "code": "PLN",
                    "symbol": "zł",
                    "exchangeRate": 4.0,
                    "selected": false
                },
                {
                    "name": "Hungarian Forint",
                    "code": "HUF",
                    "symbol": "Ft",
                    "exchangeRate": 300.0,
                    "selected": false
                },
                {
                    "name": "Czech Koruna",
                    "code": "CZK",
                    "symbol": "Kč",
                    "exchangeRate": 22.0,
                    "selected": false
                },
                {
                    "name": "Israeli New Shekel",
                    "code": "ILS",
                    "symbol": "₪",
                    "exchangeRate": 3.3,
                    "selected": false
                },
                {
                    "name": "Turkish Lira",
                    "code": "TRY",
                    "symbol": "₺",
                    "exchangeRate": 8.5,
                    "selected": false
                },
                {
                    "name": "Brazilian Real",
                    "code": "BRL",
                    "symbol": "R$",
                    "exchangeRate": 5.2,
                    "selected": false
                },
                {
                    "name": "Argentine Peso",
                    "code": "ARS",
                    "symbol": "$",
                    "exchangeRate": 95.0,
                    "selected": false
                },
                {
                    "name": "Chilean Peso",
                    "code": "CLP",
                    "symbol": "$",
                    "exchangeRate": 720.0,
                    "selected": false
                },
                {
                    "name": "Colombian Peso",
                    "code": "COP",
                    "symbol": "$",
                    "exchangeRate": 3800.0,
                    "selected": false
                },
                {
                    "name": "Peruvian Sol",
                    "code": "PEN",
                    "symbol": "S/.",
                    "exchangeRate": 3.8,
                    "selected": false
                }
            ],
            "amount": 14.4
        }
    },
    {
        "fieldId": 31,
        "fieldName": "Notes",
        "fieldType": "TEXT",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true
    },
    {
        "fieldId": 32,
        "fieldName": "Service Name",
        "fieldType": "SELECT",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": [
            {
                "id": 10,
                "value": "zohocrm",
                "selected": true,
                "default": false
            },
            {
                "id": 11,
                "value": "abmclient",
                "selected": false,
                "default": false
            },
            {
                "id": 12,
                "value": "ignite",
                "selected": false,
                "default": false
            }
        ]
    },
    {
        "fieldId": 33,
        "fieldName": "File Name",
        "fieldType": "STRING",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": "/webapps/zohocrm/javascript/crm.js"
    },
    {
        "fieldId": 34,
        "fieldName": "Bug Owner",
        "fieldType": "USER",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": {
            "id": "uexJUG4ral4j",
            "displayName": "Mohankumar R",
            "displayPicture": "https://pbs.twimg.com/media/E03qKiHUYAE9tNW.jpg:large",
            "archived": false
        }
    },
    {
        "fieldId": 35,
        "fieldName": "Status",
        "fieldType": "SELECT",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": [
            {
                "id": 13,
                "value": "Open",
                "selected": true,
                "default": true
            },
            {
                "id": 14,
                "value": "In Progress",
                "selected": false,
                "default": false
            },
            {
                "id": 15,
                "value": "Fixed",
                "selected": false,
                "default": false
            }
        ]
    },
    {
        "fieldId": 36,
        "fieldName": "Due Date",
        "fieldType": "DATE_TIME",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": "2025-08-06T02:04:08.575855"
    },
    {
        "fieldId": 37,
        "fieldName": "Screenshot",
        "fieldType": "LINK",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": {
            "title": "Reference image",
            "url": "https://sample-image.com"
        }
    },
    {
        "fieldId": 38,
        "fieldName": "Bug Source",
        "fieldType": "SELECT",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": [
            {
                "id": 16,
                "value": "Shredder",
                "selected": true,
                "default": false
            },
            {
                "id": 17,
                "value": "Flash",
                "selected": false,
                "default": false
            },
            {
                "id": 18,
                "value": "Murphy",
                "selected": false,
                "default": false
            },
            {
                "id": 19,
                "value": "Aalam",
                "selected": false,
                "default": false
            }
        ]
    },
    {
        "fieldId": 39,
        "fieldName": "Layout",
        "fieldType": "SELECT",
        "required": true,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true,
        "data": [
            {
                "id": 20,
                "value": "Feature & Enhancement",
                "selected": false,
                "default": false
            },
            {
                "id": 21,
                "value": "Release",
                "selected": true,
                "default": false
            }
        ]
    },
    {
        "fieldId": 55,
        "fieldName": "Attachement",
        "fieldType": "FILE",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true
    },
    {
        "fieldId": 56,
        "fieldName": "Datacenter Location",
        "fieldType": "GEO_LOCATION",
        "required": false,
        "searchable": false,
        "filterable": false,
        "sortable": false,
        "editable": false,
        "showInList": true,
        "showInDetail": true
    }
]