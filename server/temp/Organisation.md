### Organisation Table

| id | display_name   | unique_id | created_at          | created_by | display_picture              | status   |
|----|----------------|-----------|---------------------|------------|------------------------------|----------|
| 1  | Zoho Corp      | zohocorp  | 2023-01-01 12:12:12 | 101        | https://example.com/pic1.jpg | active   |
| 2  | Microsoft Inc. | msft      | 2023-01-02 07:13:13 | 102        | https://example.com/pic2.jpg | inactive |
| 3  | Paypal         | paypal    | 2023-01-03 02:14:14 | 103        | https://example.com/pic3.jpg | inactive |

### Org Configuration Table

| id | org_id | role  | invite_member | add_member | remove_member | update_member | update_org | delete_org | leave_org | create_group | show_employee_id |
|----|--------|-------|---------------|------------|---------------|---------------|------------|------------|-----------|--------------|------------------|
| 1  | 1      | admin | true          | true       | true          | true          | true       | true       | true      | true         | true             |
| 2  | 2      | user  | false         | true       | false         | false         | false      | false      | false     | false        | false            |
| 3  | 3      | admin | true          | true       | true          | true          | true       | false      | true      | true         | false            |

### Org Subscription History Table

| id | org_id | plan_id | start_date | end_date   | created_at |
|----|--------|---------|------------|------------|------------|
| 1  | 1      | 4       | 2023-01-01 | 2024-01-01 | 2023-01-01 |
| 2  | 2      | 5       | 2023-03-01 | 2023-03-30 | 2023-03-01 |
| 3  | 3      | 2       | 2023-03-01 | 2024-03-01 | 2023-03-01 |
| 4  | 1      | 3       | 2023-02-01 | 2024-02-01 | 2023-02-01 |

