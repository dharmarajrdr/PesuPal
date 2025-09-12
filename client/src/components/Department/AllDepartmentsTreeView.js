import Tree from "../BinaryTree/Tree"

const department = {
    'name': 'Zoho Corp',
    'children': [

        {
            'name': 'ZohoMail',
            'children': [
                {
                    'name': 'Inbox',
                    'children': [
                        { 'name': 'Primary' },
                        {
                            'name': 'Social',
                            'children': [
                                { 'name': 'Accounts' },
                                {
                                    'name': 'Billing',
                                    'children': [
                                        { 'name': 'Accounts' },
                                        {
                                            'name': 'Billing',
                                            'children': [
                                                {
                                                    'name': 'Accounts',
                                                    'children': [
                                                        {
                                                            'name': 'Accounts', 'children': [
                                                                {
                                                                    'name': 'Accounts',
                                                                    'children': [
                                                                        {
                                                                            'name': 'Inbox',
                                                                            'children': [
                                                                                { 'name': 'Primary' },
                                                                                {
                                                                                    'name': 'Social',
                                                                                    'children': [
                                                                                        { 'name': 'Accounts' },
                                                                                        {
                                                                                            'name': 'Billing',
                                                                                            'children': [
                                                                                                { 'name': 'Accounts' },
                                                                                                {
                                                                                                    'name': 'Billing',
                                                                                                    'children': [
                                                                                                        {
                                                                                                            'name': 'Accounts',
                                                                                                            'children': [
                                                                                                                {
                                                                                                                    'name': 'Accounts', 'children': [
                                                                                                                        { 'name': 'Accounts' },
                                                                                                                        { 'name': 'Billing' },
                                                                                                                        { 'name': 'Investments' }
                                                                                                                    ]
                                                                                                                },
                                                                                                                { 'name': 'Billing' },
                                                                                                                { 'name': 'Investments' }
                                                                                                            ]
                                                                                                        },
                                                                                                        { 'name': 'Billing' },
                                                                                                        { 'name': 'Investments' }
                                                                                                    ]
                                                                                                },
                                                                                                { 'name': 'Investments' }
                                                                                            ]
                                                                                        },
                                                                                        { 'name': 'Investments' }
                                                                                    ]
                                                                                },
                                                                                { 'name': 'Promotions' }
                                                                            ]
                                                                        },
                                                                        { 'name': 'Sent' },
                                                                        { 'name': 'Drafts' }
                                                                    ]
                                                                },
                                                                { 'name': 'Billing' },
                                                                { 'name': 'Investments' }
                                                            ]
                                                        },
                                                        { 'name': 'Billing' },
                                                        { 'name': 'Investments' }
                                                    ]
                                                },
                                                { 'name': 'Billing' },
                                                { 'name': 'Investments' }
                                            ]
                                        },
                                        { 'name': 'Investments' }
                                    ]
                                },
                                { 'name': 'Investments' }
                            ]
                        },
                        { 'name': 'Promotions' }
                    ]
                },
                { 'name': 'Sent' },
                { 'name': 'Drafts' }
            ]
        },
        {
            'name': 'ZohoCRM',
            'children': [
                { 'name': 'Sales' },
                { 'name': 'Support' },
                { 'name': 'Marketing' }
            ]
        },
        {
            'name': 'ZohoFinance',
            'children': [
                { 'name': 'Accounts' },
                { 'name': 'Billing' },
                { 'name': 'Investments' }
            ]
        },
        {
            'name': 'ZohoCliq',
            'children': [
                { 'name': 'Product' },
                { 'name': 'Design' },
                { 'name': 'Development' }
            ]
        }
    ]
}

const AllDepartmentsTreeView = () => {

    return (
        <div className="noScrollbar">
            <Tree department={department} />
        </div>
    )
}

export default AllDepartmentsTreeView